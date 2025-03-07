package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.model.Bank;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;

import java.io.IOException;
import java.util.List;

@WebServlet("/bank")
public class BankServlet extends HttpServlet {
    AdminService adminService = new AdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            List<Bank> banks = adminService.getAllBanks();
            req.setAttribute("banks", banks);
            req.getRequestDispatcher("/bankingManagement.jsp").forward(req, resp);
    }
}
