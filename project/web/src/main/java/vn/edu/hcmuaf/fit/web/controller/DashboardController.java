package vn.edu.hcmuaf.fit.web.controller;

import vn.edu.hcmuaf.fit.web.dao.DashboardDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private final DashboardDao dashboardDAO = new DashboardDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ DashboardDAO
        Map<String, Object> info = dashboardDAO.getInfo();

        List<Integer> data = dashboardDAO.getOrdersCountByMonth();

        request.setAttribute("data", data);

        // Đưa thông tin vào request
        request.setAttribute("userCount", info.get("so_luong_nguoi_dung"));
        request.setAttribute("productCount", info.get("so_luong_san_pham"));
        request.setAttribute("orderCount", info.get("so_luong_don_hang"));
        request.setAttribute("totalRevenue", info.get("tong_doanh_thu"));

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
    }
}