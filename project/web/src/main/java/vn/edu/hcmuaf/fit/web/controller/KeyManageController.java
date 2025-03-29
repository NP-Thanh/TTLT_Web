package vn.edu.hcmuaf.fit.web.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.model.KeyManage;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.StorageServiece;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/KeyManagement")
public class KeyManageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AdminService adminService = new AdminService();
        List<KeyManage> keys = adminService.getKeyManageList();
        request.setAttribute("keys", keys);
        request.getRequestDispatcher("/keyManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
