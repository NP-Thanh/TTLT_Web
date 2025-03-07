package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.dao.ProductDao;

import java.io.IOException;

@WebServlet("/editBank")
public class editBank extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idBankStr = req.getParameter("id");
        String nameBank = req.getParameter("name");
        String numberBank = req.getParameter("number");
        String ownerBank = req.getParameter("owner");
        String qrBank = req.getParameter("qr");

        // Chuyển đổi kiểu dữ liệu
        int idBank = Integer.parseInt(idBankStr);


        // Gọi DAO để cập nhật sản phẩm
        ProductDao dao = new ProductDao();
        dao.editBank(idBank, nameBank, numberBank, ownerBank, qrBank);

        resp.sendRedirect("bank");
    }
}
