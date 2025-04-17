package vn.edu.hcmuaf.fit.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;
import vn.edu.hcmuaf.fit.web.redis.RedisManager;


import java.io.IOException;

@WebFilter(filterName = "AdminPermissionFilter", urlPatterns = {"/ProductManagement", "/bank", "/discounts", "/user", "/dashboard", "/logs"})
public class AdminPermissionFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession();

        if (session != null) {
            Integer adminId = (Integer) session.getAttribute("adminId");

            if (adminId != null && isRevoked(adminId)) {
                // Xoá khỏi Redis để tránh kiểm lại nhiều lần (tuỳ bạn)
                RedisManager.getJedis().srem("revoked_admins", String.valueOf(adminId));

                session.invalidate();
                httpRes.sendRedirect("/login");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isRevoked(int adminId) {
        Jedis jedis = RedisManager.getJedis();
        return jedis.sismember("revoked_admins", String.valueOf(adminId));
    }
}
