package vn.edu.hcmuaf.fit.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import redis.clients.jedis.Jedis;
import vn.edu.hcmuaf.fit.web.redis.RedisManager;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;
import java.util.Set;

@WebFilter(filterName = "UserPermissionFilter", urlPatterns = {"/*"})
public class UserPermissionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpSession session = httpReq.getSession(false);

        String path = httpReq.getServletPath();

        if (session != null) {
            Integer userId = (Integer) session.getAttribute("uid");
            Boolean isAdmin = (Boolean) session.getAttribute("admin");

            //Nếu là admin bỏ qua filter này qua AdminPermissionFilter
            if (Boolean.TRUE.equals(isAdmin)) {
                chain.doFilter(request, response);
                return;
            }

            if (userId != null && isRevokedUser(userId)) {
                LogEntryService logService = new LogEntryService();
                String before = "User ID: " + userId + " truy cập trang " + path;
                String after = "Chặn truy cập do tài khoản đã bị hạ quyền (revoke)";
                logService.logAction("Warning", "Chặn truy cập người dùng", userId, before, after);

                request.setAttribute("error", "Tài khoản của bạn đã bị hạn chế truy cập vào trang web");
                RequestDispatcher dispatcher  = request.getRequestDispatcher("/400.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isRevokedUser(int userId) {
        Jedis jedis = RedisManager.getJedis();
        return jedis.sismember("revoked_users", String.valueOf(userId));
    }
}
