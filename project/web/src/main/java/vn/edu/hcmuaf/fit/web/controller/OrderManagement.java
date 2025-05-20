package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.model.OrderWithUser;
import vn.edu.hcmuaf.fit.web.model.ProductManage;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderManagement", value = "/orderManagement")
public class OrderManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderServiece orderServiece = new OrderServiece();
        List<OrderWithUser> list= orderServiece.getAllOrders();
        HttpSession session = request.getSession(true);
        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Xem quản lý đơn hàng", uid, "list orders", "list orders");
        request.setAttribute("list", list);
        request.getRequestDispatcher("/orderManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("search".equals(action)) {
            searchOrder(request, response);
        } else if ("delete".equals(action)) {
            deleteOrder(request);
            response.sendRedirect(request.getContextPath() + "/orderManagement");
        }
    }

    private void searchOrder(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderID");
        String productName = request.getParameter("productName");
        String status = request.getParameter("status");

        if (status.equals("Paid")) {
            status = "Đã thanh toán";
        } else if (status.equals("NotPaid")) {
            status = "Chưa thanh toán";
        } else if (status.equals("Pending")) {
            status = "Chờ xử lý";
        }
        OrderServiece orderServiece = new OrderServiece();

        // Kiểm tra nếu orderId không rỗng và có thể chuyển đổi sang Integer
        Integer orderIdInt = null;
        if (orderId != null && !orderId.isEmpty()) {
            try {
                orderIdInt = Integer.parseInt(orderId);  // Chuyển đổi nếu có giá trị hợp lệ
            } catch (NumberFormatException e) {
                // Xử lý khi không thể chuyển đổi, ví dụ: báo lỗi cho người dùng
                System.out.println("Invalid Order ID");
            }
        }

        // Chỉ lọc đơn hàng nếu orderId hợp lệ
        List<OrderWithUser> filteredOrders = orderServiece.filterOrders(orderIdInt, productName, status);

        request.setAttribute("list", filteredOrders);
        request.getRequestDispatcher("/orderManagement.jsp").forward(request, response);
    }
    private void deleteOrder(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        int oid = Integer.parseInt(request.getParameter("oid"));
        OrderServiece orderServiece = new OrderServiece();
        orderServiece.deleteOrder(oid);

        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        logService.logAction("Danger", "Xóa đơn hàng", uid, "id order: "+oid, "Empty");
    }
}
