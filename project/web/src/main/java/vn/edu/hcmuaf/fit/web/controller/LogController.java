package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.model.LogEntry;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogEntry", value = "/logs")
public class LogController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LogEntryService service = new LogEntryService();
        List<LogEntry> logs= service.getAllLogs();
        request.setAttribute("logs", logs);
        request.getRequestDispatcher("logentry.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminId = request.getParameter("productName"); // Lấy ID admin
        String level = request.getParameter("level"); // Lấy level

        LogEntryService service = new LogEntryService();
        List<LogEntry> logs = service.filterLogs(adminId, level);

        request.setAttribute("logs", logs);
        request.getRequestDispatcher("logentry.jsp").forward(request, response);
    }
}
