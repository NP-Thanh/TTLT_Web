package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import redis.clients.jedis.Jedis;
import vn.edu.hcmuaf.fit.web.redis.RedisManager;

import java.io.IOException;

@WebServlet(name = "RevokeAdminServlet", value = "/revoke")
public class RevokeAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy ID admin bị hạ quyền từ request
        String targetIdParam = request.getParameter("revokeAdminId");
        if (targetIdParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing revokeAdminId");
            return;
        }

        int revokeAdminId = Integer.parseInt(targetIdParam);
        // Cập nhật vào Redis (đánh dấu bị hạ quyền)
        try (Jedis jedis = RedisManager.getJedis()) {
            jedis.sadd("revoked_admins", String.valueOf(revokeAdminId));
        }
        response.sendRedirect("user");
    }
}
