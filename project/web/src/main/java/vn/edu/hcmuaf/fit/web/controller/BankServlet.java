package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.model.Bank;
import vn.edu.hcmuaf.fit.web.model.KeyManage;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.BankServiece;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/bank")
public class BankServlet extends HttpServlet {
    AdminService adminService = new AdminService();
    BankServiece bankServiece = new BankServiece();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Bank> banks = adminService.getAllBanks();
        HttpSession session = req.getSession(true);
        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Xem quản lý thông tin chuyển khoản", uid, "list banks", "list banks");
        req.setAttribute("banks", banks);
        req.getRequestDispatcher("/bankingManagement.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addBank(request, response);
        }else if ("delete".equals(action)) {
            deleteBank(request);
        }else if ("update".equals(action)) {
            updateBank(request);
        }
        response.sendRedirect(request.getContextPath() + "/bank");
    }

    private void updateBank(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String owner = request.getParameter("owner");
        String qr = request.getParameter("qr");

        bankServiece.updateBank(id, name, number, owner, qr);
    }

    private void addBank(HttpServletRequest request , HttpServletResponse response) {
        String name = request.getParameter("bankName");
        String number = request.getParameter("bankNumber");
        String owner = request.getParameter("bankOwner");
        String qr = request.getParameter("bankQr");

        bankServiece.addBank(name, number, owner, qr);
    }

    private void deleteBank(HttpServletRequest request) {
        int kid = Integer.parseInt(request.getParameter("id"));
        bankServiece.deleteBank(kid);
    }

}
