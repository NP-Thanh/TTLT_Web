package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.model.ProductType;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;
import vn.edu.hcmuaf.fit.web.servieces.LoginService;
import vn.edu.hcmuaf.fit.web.servieces.ProductServiece;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private LoginService loginService;

    @Override
    public void init() {
        loginService = new LoginService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserServiece userServiece = new UserServiece();

        if (loginService.validateUser(email, password)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("uid",loginService.getID(email));
            User user= userServiece.getUserById((Integer) session.getAttribute("uid"));
            boolean admin= false;
            if (user.getRole_id()==1){
                admin=true;
            }
            session.setAttribute("admin",admin);
            session.setAttribute("email", email);
            response.sendRedirect("/web/home");
        } else {
            response.getWriter().println("<h2>Đăng Nhập Thất Bại. Vui lòng kiểm tra lại email và mật khẩu.</h2>");
            response.sendRedirect("/web/login");
        }
    }
}