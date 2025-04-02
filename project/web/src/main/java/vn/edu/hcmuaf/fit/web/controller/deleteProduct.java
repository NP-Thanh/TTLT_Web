package vn.edu.hcmuaf.fit.web.controller;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.ProductDao;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;

@WebServlet("/deleteProduct")
public class deleteProduct extends HttpServlet { 

@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession(true);
    int pid = Integer.parseInt(request.getParameter("pid"));
    AdminService adminService = new AdminService();
    adminService.deleteProduct(pid);
    int uid = (int) session.getAttribute("uid");
    LogEntryService logService = new LogEntryService();
    logService.logAction("Danger", "Xóa sản phẩm", uid, "id product: "+pid, "Empty");
    response.sendRedirect("ProductManagement");
}

    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { }

}
