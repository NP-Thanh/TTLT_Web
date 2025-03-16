package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/addKey")
public class addProductKey extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        String keys = request.getParameter("keys");

        // Tách các key theo dòng
        List<String> keyList = Arrays.asList(keys.split("\\r?\\n"));

        // thêm từng key vào sản phẩm
        AdminService adminService = new AdminService();
        for (String key : keyList) {
            key = key.trim(); // Xóa khoảng trắng thừa
            if (!key.isEmpty()) {
                adminService.addProductKey(pid, key);
            }
        }

        response.sendRedirect("ProductManagement");

    }
}
