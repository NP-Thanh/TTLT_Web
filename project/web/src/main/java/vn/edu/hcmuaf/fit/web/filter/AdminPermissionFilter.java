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

@WebFilter(filterName = "AdminPermissionFilter", urlPatterns = {"/ProductManagement", "/bank", "/discounts", "/user", "/dashboard", "/logs", "/orderManagement", "/KeyManagement"})
public class AdminPermissionFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;
        HttpSession session = httpReq.getSession(false);

        if (session != null) {
            Integer adminId = (Integer) session.getAttribute("uid");
            if (adminId != null && isRevoked(adminId)) {

                //Ghi lại log khi admin bị chặn
                LogEntryService logService = new LogEntryService();
                String before = "Admin ID: " + adminId + " truy cập trang admin";
                String after = "Chặn truy cập do tài khoản đã bị hạ quyền";
                logService.logAction("Warning", "Chặn truy cập admin", adminId, before, after);

                request.setAttribute("error", "Tài khoản của bạn đã bị thu hồi quyền admin");
                RequestDispatcher dispatcher  = request.getRequestDispatcher("/400.jsp");
                dispatcher.forward(request, response);
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
