package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.ProductDao;

import java.io.IOException;

@WebServlet("/deleteServlet")
public class deleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int Bid = Integer.parseInt(request.getParameter("Bid"));
        ProductDao dao = new ProductDao();
        dao.deleteBank(Bid);
        response.sendRedirect("bank");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
