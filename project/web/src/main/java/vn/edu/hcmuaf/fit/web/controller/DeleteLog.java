package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;

@WebServlet(name = "DeleteLog", value = "/deleteLog")
public class DeleteLog extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lid = Integer.parseInt(request.getParameter("lid"));
        LogEntryService logEntryService = new LogEntryService();
        logEntryService.deleteLog(lid);
        response.sendRedirect("logs");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
