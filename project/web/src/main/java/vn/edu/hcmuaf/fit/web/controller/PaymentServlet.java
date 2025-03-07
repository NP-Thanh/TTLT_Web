package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.mail.IJavaMail;
import vn.edu.hcmuaf.fit.web.mail.MailServiece;
import vn.edu.hcmuaf.fit.web.model.*;
import vn.edu.hcmuaf.fit.web.servieces.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymentServlet", value = "/payment")
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid") != null) {
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }
        List<ProductType> data = productServiece.getAllProductTypes();
        request.setAttribute("productTypes", data);

        OrderServiece orderServiece = new OrderServiece();
        BankServiece bankServiece = new BankServiece();
        DiscountService discountServiece = new DiscountService();

        String order_id = (String) request.getParameter("oid");
        String bank_id = (String) request.getParameter("bid");
        if (order_id != null && bank_id != null) {
            int oid = Integer.parseInt(order_id);
            int bid = Integer.parseInt(bank_id);
            // Lấy thông tin order bao gồm user
            OrderWithUser orderWithUser = orderServiece.getOrderWithUserByOID(oid);
            request.setAttribute("orderWithUser", orderWithUser);
            Order order = orderWithUser.getOrder();
            request.setAttribute("order", order);
            Discount discount = discountServiece.getDiscountById(order.getDiscount_id());
            request.setAttribute("discount", discount);
            User user = orderWithUser.getUser();
            request.setAttribute("user_order", user);

            // Lấy thông tin danh sách product
            List<Product> products = orderWithUser.getProduct();
            request.setAttribute("products", products);
            List<OrderDetail> orderDetails = orderWithUser.getOrderDetail();
            request.setAttribute("orderDetails", orderDetails);

            // Lấy thông tin danh sách banks
            List<Bank> banks = bankServiece.getBanks();
            request.setAttribute("banks", banks);

            // Lấy thông tin bank đã chọn
            Bank bank_selected = bankServiece.getBankById(bid);
            orderServiece.updateBankID(oid, bid);
            request.setAttribute("bank_selected", bank_selected);
        }

        request.getRequestDispatcher("/payment.jsp").forward(request, response);
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

        // Cập nhật trạng thái đơn hàng thành "Đã thanh toán"
        orderServiece.updateOrderStatus(orderId, "Đã thanh toán");

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
                        orderServiece.updateOrderStatus(orderId, "Chờ xử lý");
                        response.sendRedirect("order?id=" + orderId + "&pid=" + orderWithUser.getProduct().get(0).getId());
//                        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không đủ mã key cho sản phẩm.");
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

        // Chuyển hướng đến trang hóa đơn
        response.sendRedirect("order?id=" + orderId + "&pid=" + orderWithUser.getProduct().get(0).getId());
    }
}
