package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.web.servieces.UserServiece;

import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/ProductManagement", "/bank", "/discounts", "/user", "/dashboard"})
public class LoginFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        boolean admin = (boolean) session.getAttribute("admin");
        String path = req.getServletPath();
        if (path.equals("/ProductManagement") || path.equals("/bank") || path.equals("/dashboard") || path.equals("/user") || path.equals("/discounts")) {
            if (!admin) {
                res.sendRedirect("home");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
