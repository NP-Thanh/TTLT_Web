package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.web.dao.DiscountDao;
import vn.edu.hcmuaf.fit.web.db.JDBIConnector;
import vn.edu.hcmuaf.fit.web.model.Discount;
import vn.edu.hcmuaf.fit.web.servieces.DiscountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/discounts")
public class DiscountController extends HttpServlet {
    private final DiscountService discountService;

    public DiscountController() {
        this.discountService = new DiscountService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Discount> discounts = discountService.getAllDiscounts();
        HttpSession session = request.getSession(true);
        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Xem quản lý vouchers", uid, "list vouchers", "list vouchers");
        request.setAttribute("discounts", discounts);
        request.getRequestDispatcher("/discount.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addDiscount(request, response);
        } else if ("update".equals(action)) {
            updateDiscount(request);
        } else if ("delete".equals(action)) {
            deleteDiscount(request);
        }

        // Sau khi xử lý, lấy lại danh sách và forward về bảng mã giảm giá
        List<Discount> discounts = discountService.getAllDiscounts();
        request.setAttribute("discounts", discounts);

        // Gửi lại fragment HTML bảng discount để dùng cho AJAX
        request.getRequestDispatcher("/discount_table.jsp").forward(request, response);
    }



    private void addDiscount(HttpServletRequest request , HttpServletResponse response) {
        String id = request.getParameter("id");
        String value = request.getParameter("value");
        String quantity = request.getParameter("quantity");
        String createDate = request.getParameter("create_date");
        String expDate = request.getParameter("exp_date");
        String status = "Còn hiệu lực"; // Giá trị mặc định

        // Biến để lưu thông báo lỗi
        String errorMessage = null;

        try {
            // Kiểm tra và chuyển đổi các tham số
            int discountId = Integer.parseInt(id);
            double discountValue = Double.parseDouble(value);
            int discountQuantity = Integer.parseInt(quantity);
            LocalDate createDateParsed = LocalDate.parse(createDate);
            LocalDate expDateParsed = LocalDate.parse(expDate);

            // Tạo đối tượng Discount
            Discount discount = new Discount(discountId, discountValue, discountQuantity, createDateParsed, expDateParsed, status);
            discountService.addDiscount(discount);
        } catch (NumberFormatException e) {
            errorMessage = "Giá trị không hợp lệ. Vui lòng nhập lại mã, giá trị và số lượng.";
            System.err.println("Giá trị không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            errorMessage = "Lỗi không xác định: " + e.getMessage();
            System.err.println("Lỗi không xác định: " + e.getMessage());
        }

        // Nếu có thông báo lỗi, có thể đưa ra cho người dùng
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
            // Chuyển hướng hoặc forward đến trang thông báo lỗi
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // 2. Ghi thông báo lỗi ra Response (Ajax sẽ nhận thông báo này)
            return;
        }
    }

    private void updateDiscount(HttpServletRequest request) {
        String id = request.getParameter("id");
        String quantity = request.getParameter("quantity");

        Discount discount = discountService.getDiscountById(Integer.valueOf(id));
        discount.setQuantity(Integer.valueOf(quantity));
        discountService.updateDiscount(discount);
    }

    private void deleteDiscount(HttpServletRequest request) {
        String id = request.getParameter("id");
        discountService.deleteDiscount(Integer.valueOf(id));
    }
}