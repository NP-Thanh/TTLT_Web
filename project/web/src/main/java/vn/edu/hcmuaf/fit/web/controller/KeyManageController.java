package vn.edu.hcmuaf.fit.web.controller;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.model.KeyManage;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.StorageServiece;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet("/KeyManagement")
public class KeyManageController extends HttpServlet {
    private final StorageServiece storageServiece = new StorageServiece();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("pid");
        if (productId != null) {
            processRequest(request, response);
            return;
        }

        AdminService adminService = new AdminService();
        List<KeyManage> keys = adminService.getKeyManageList();
        request.setAttribute("keys", keys);
        request.getRequestDispatcher("/keyManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addKey(request, response);
            response.sendRedirect(request.getContextPath() + "/KeyManagement");
        } else if ("search".equals(action)) {
            searchKey(request, response);
        } else if ("delete".equals(action)) {
            deleteKey(request);
            response.sendRedirect(request.getContextPath() + "/KeyManagement");
        }else if ("update".equals(action)) {
            updateKey(request);
            response.sendRedirect(request.getContextPath() + "/KeyManagement");
        }

    }

    private void updateKey(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("kid"));
        String key = request.getParameter("kName");
        String productName = request.getParameter("pName");
        String productType = request.getParameter("pType");
        String image = request.getParameter("pImg");

        AdminService adminService = new AdminService();
        adminService.editKey(id, key, productName, productType, image);
    }

    private void addKey(HttpServletRequest request , HttpServletResponse response) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        String keys = request.getParameter("keys");

        List<String> keyList = Arrays.asList(keys.split("\\r?\\n"));

        // thêm từng key vào sản phẩm
        AdminService adminService = new AdminService();
        for (String key : keyList) {
            key = key.trim(); // Xóa khoảng trắng thừa
            if (!key.isEmpty()) {
                adminService.addProductKey(pid, key);
            }
        }
    }

    private void searchKey(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        Integer keyId = request.getParameter("keyID") != null && !request.getParameter("keyID").isEmpty()
                ? Integer.parseInt(request.getParameter("keyID"))
                : null;

        AdminService adminService = new AdminService();
        List<KeyManage> filteredKeys =  adminService.filterKeyManages(keyId);

        request.setAttribute("keys", filteredKeys);
        request.getRequestDispatcher("/keyManagement.jsp").forward(request, response);
    }

    private void deleteKey(HttpServletRequest request) {
        int kid = Integer.parseInt(request.getParameter("kid"));
        AdminService adminService = new AdminService();
        adminService.deleteKeyManage(kid);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        JsonObject jsonResponse = new JsonObject();

        String pidStr = request.getParameter("pid");

        if (pidStr != null && !pidStr.isEmpty()) {
            try {
                int pid = Integer.parseInt(pidStr);
                boolean exists = storageServiece.existPId(pid);

                if (exists) {
                    jsonResponse.addProperty("valid", true);
                    jsonResponse.addProperty("message", "Mã sản phẩm hợp lệ!");
                } else {
                    jsonResponse.addProperty("valid", false);
                    jsonResponse.addProperty("message", "Mã sản phẩm không tồn tại!");
                }
            } catch (NumberFormatException e) {
                jsonResponse.addProperty("valid", false);
                jsonResponse.addProperty("message", "Mã sản phẩm không hợp lệ!");
            }
        } else {
            jsonResponse.addProperty("valid", false);
            jsonResponse.addProperty("message", "Vui lòng nhập Mã sản phẩm!");
        }

        out.print(jsonResponse);
        out.flush();
    }
}