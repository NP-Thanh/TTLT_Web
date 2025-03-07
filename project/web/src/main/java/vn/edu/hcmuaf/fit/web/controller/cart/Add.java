package vn.edu.hcmuaf.fit.web.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.cart.Cart;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        String idParam = request.getParameter("id");
        int id = Integer.parseInt(idParam);
        Product pid= productServiece.getProductById(id);
        if (pid == null) {
            response.sendRedirect("ProductDetail?addCart=false");
        }
        HttpSession session = request.getSession(true);
        Cart c= (Cart) session.getAttribute("cart");
        if (c == null) {
            c = new Cart();
        }
        c.add(pid);
        session.setAttribute("cart", c);
        String referer = request.getHeader("referer"); // Lấy URL trước đó
        if (referer != null) {
            response.sendRedirect(referer); // Quay lại trang trước
        } else {
            response.sendRedirect("ProductDetail?addCart=true"); // Phòng trường hợp không có URL trước đó
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
