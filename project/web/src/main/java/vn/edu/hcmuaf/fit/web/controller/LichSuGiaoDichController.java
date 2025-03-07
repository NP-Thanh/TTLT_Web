package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.OrderServiece;
import vn.edu.hcmuaf.fit.web.model.OrderWithUser;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;

import java.io.IOException;
import java.util.List;

@WebServlet("/lichsugiaodich")
public class LichSuGiaoDichController extends HttpServlet {
    private final OrderServiece orderService = new OrderServiece();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiece productServiece = new ProductServiece();
        UserDao userDao = new UserDao();
        HttpSession session = request.getSession(true);
        if (session.getAttribute("uid")!=null){
            User user = userDao.getUserById((Integer) session.getAttribute("uid"));
            request.setAttribute("user", user);
        }
        List<ProductType> data=productServiece.getAllProductTypes();
        request.setAttribute("productTypes", data);

        List<OrderWithUser> orders = orderService.getAllOrderWithUID((Integer) request.getSession(true).getAttribute("uid"));
        request.setAttribute("orders", orders);
        System.out.println(orders.size());
        request.getRequestDispatcher("/lichsugiaodich.jsp").forward(request, response);
    }
}