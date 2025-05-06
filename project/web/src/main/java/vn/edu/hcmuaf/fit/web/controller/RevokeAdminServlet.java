package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import redis.clients.jedis.Jedis;
import vn.edu.hcmuaf.fit.web.dao.UserDao;
import vn.edu.hcmuaf.fit.web.model.User;
import vn.edu.hcmuaf.fit.web.redis.RedisManager;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;

@WebServlet(name = "RevokeAdminServlet", value = "/revoke")
public class RevokeAdminServlet extends HttpServlet {

    private final UserDao userDAO = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("uid") == null || session.getAttribute("isSuperAdmin") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Chưa đăng nhập");
            return;
        }

        // Kiểm tra quyền SuperAdmin
        boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");
        if (!isSuperAdmin) {
            request.setAttribute("error", "Chỉ SuperAdmin mới có quyền hạ quyền admin");
            request.getRequestDispatcher("/400.jsp").forward(request, response);
            return;
        }


        // Lấy ID admin bị hạ quyền từ request
        String targetIdParam = request.getParameter("revokeAdminId");
        String actionParam = request.getParameter("action");
        if (targetIdParam == null) {
            request.setAttribute("error", "Thiếu revokeAdminId");
            request.getRequestDispatcher("/400.jsp").forward(request, response);
            return;
        }

        int revokeAdminId = Integer.parseInt(targetIdParam);
        // Cập nhật vào Redis (đánh dấu bị hạ quyền)
        try (Jedis jedis = RedisManager.getJedis()) {
            if("revoke".equals(actionParam)) {
                jedis.sadd("revoked_admins", String.valueOf(revokeAdminId));
                jedis.sadd("revoked_users", String.valueOf(revokeAdminId));
                userDAO.deleteUser(revokeAdminId, "Hạn chế");
            } else if("unrevoke".equals(actionParam)) {
                jedis.srem("revoked_admins", String.valueOf(revokeAdminId));
                jedis.srem("revoked_users", String.valueOf(revokeAdminId));
                userDAO.deleteUser(revokeAdminId, "Hoạt động");
            } else{
                request.setAttribute("error", "Hành động không hợp lệ");
                request.getRequestDispatcher("/400.jsp").forward(request, response);
                return;
            }
        }

        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        String before = "ID: " + revokeAdminId + (actionParam.equals("revoke") ? " còn quyền truy cập" : " thu hồi quyền truy cập");
        String after = "ID: " + revokeAdminId + (actionParam.equals("revoke") ? " thu hồi quyền truy cập" : " khôi phục quyền truy cập");
        String logAction = actionParam.equals("revoke") ? "Hạ quyền " : "Khôi phục quyền ";

        logService.logAction("Warning", logAction, uid, before, after );
        response.sendRedirect("user");
    }
}