package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.mail.IJavaMail;
import vn.edu.hcmuaf.fit.web.mail.MailServiece;
import vn.edu.hcmuaf.fit.web.model.*;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;
import vn.edu.hcmuaf.fit.web.servieces.StorageServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SendKeyServlet", value = "/sendKey")
public class SendKeyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderServiece orderServiece = new OrderServiece();
        StorageServiece storageService = new StorageServiece(); // Dịch vụ để làm việc với bảng storage
        IJavaMail mailServiece = new MailServiece();

        // Lấy thông tin đơn hàng và người dùng
        int orderId = Integer.parseInt(request.getParameter("oid"));
        OrderWithUser orderWithUser = orderServiece.getOrderWithUserByOID(orderId);
        Order order = orderWithUser.getOrder();
        User user = orderWithUser.getUser();
        List<Product> products = orderWithUser.getProduct();
        List<OrderDetail> orderDetails = orderWithUser.getOrderDetail();

        // Chuẩn bị nội dung email
        StringBuilder emailContent = new StringBuilder();
        emailContent.append("Xin chào ").append(user.getName()).append(",\n\n");
        emailContent.append("Đơn hàng của bạn đã được thanh toán thành công.\n");
        emailContent.append("Mã đơn hàng: ").append(order.getId()).append("\n\n");
        emailContent.append("Chi tiết sản phẩm:\n");

        for (OrderDetail detail : orderDetails) {
            for (Product product : products) {
                if (detail.getProduct_id() == product.getId()) {
                    int productId = product.getId();
                    int quantity = detail.getQuantity();

                    // Lấy danh sách mã key chưa sử dụng
                    List<Storage> availableKeys = storageService.getAvailableKeys(productId, quantity);
                    if (availableKeys.size() < quantity) {
                        // Nếu không đủ key, thông báo lỗi
                        request.setAttribute("error", "Không đủ mã key cho sản phẩm " + product.getName());
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không đủ mã key cho sản phẩm " + product.getName());
                        return;
                    }
                    for (Storage storage : availableKeys) {
                        emailContent.append("- Sản phẩm: ").append(product.getName())
                                .append(", Mã key:").append(storage.getKey()).append("\n");
                        // Cập nhật key vào bảng storage
                        storageService.updateKeyStatus(storage.getId(), orderId, "Đã xuất");
                    }
                }
            }
        }

        emailContent.append("\nCảm ơn bạn đã sử dụng dịch vụ của chúng tôi!");

        // Gửi email
        boolean emailSent = mailServiece.send(user.getEmail(), "Thanh toán đơn hàng thành công", emailContent.toString());
        if (!emailSent) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Gửi email thất bại.");
            return;
        }
        orderServiece.updateOrderStatus(orderId, "Đã thanh toán");

        // Chuyển hướng đến trang hóa đơn
        response.sendRedirect("orderManagement");
    }
}
