package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.web.model.*;
import vn.edu.hcmuaf.fit.web.servieces.AdminService;
import vn.edu.hcmuaf.fit.web.servieces.LogEntryService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/ProductManagement")
public class ProductManagement extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminService adminService = new AdminService();
        HttpSession session = request.getSession(false); // false để không tự tạo mới session
        if (session == null) {
            response.sendRedirect("login");
            return;
        }

        int uid = (int) session.getAttribute("uid");
        boolean isSuperAdmin = (boolean) session.getAttribute("isSuperAdmin");

        // Chỉ cho phép thêm sp loại sp mà admin đc phân
        List<String> allowedType = null;
        if (!isSuperAdmin) {
            allowedType = adminService.getProductTypeByUserId(uid);
            request.setAttribute("allowedType", allowedType);
        }

        List<ProductManage> products = adminService.getProductListByRole(uid, isSuperAdmin);

        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Xem quản lý sản phẩm", uid, "list products", "list products");
        request.setAttribute("products", products);
        request.getRequestDispatcher("/productManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addProduct(request, response);
            response.sendRedirect(request.getContextPath() + "/ProductManagement");
        } else if ("search".equals(action)) {
            searchProduct(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request);
            response.sendRedirect(request.getContextPath() + "/ProductManagement");
        }else if ("update".equals(action)) {
            updateProduct(request);
            response.sendRedirect(request.getContextPath() + "/ProductManagement");
        }else if ("addKey".equals(action)) {
            addKey(request);
            response.sendRedirect(request.getContextPath() + "/ProductManagement");
        }
    }

    private void updateProduct(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("pid"));
        String name = request.getParameter("pName");
        String type = request.getParameter("pType");
        double price = Double.parseDouble(request.getParameter("pPrice"));
        String duration = request.getParameter("pDuration");
        String image = request.getParameter("pImg");
        String description = request.getParameter("pDescription");
        String introduction = request.getParameter("pIntroduction");
        String manufacturer = request.getParameter("pManufacturer");
        String support = request.getParameter("pSupport");

        AdminService adminService = new AdminService();
        adminService.editProduct(id,name,type,price,duration,image,description,introduction,manufacturer,support);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        double price = Double.parseDouble(request.getParameter("price"));
        String duration = request.getParameter("duration");
        String img = request.getParameter("img");
        String des = request.getParameter("des");
        String intro  = request.getParameter("intro");
        String manu = request.getParameter("manu");
        String support = request.getParameter("support");
        String banner = request.getParameter("banner");

        AdminService adminService = new AdminService();
        HttpSession session = request.getSession(true);
        int uid = (int) session.getAttribute("uid");
        int pid = adminService.addProduct(name,type,price,duration,img,des,intro,manu,support,banner);
        LogEntryService logService = new LogEntryService();
        logService.logAction("Info", "Thêm sản phẩm", uid, "Empty", "id product: "+pid);
    }

    private void deleteProduct(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        int pid = Integer.parseInt(request.getParameter("pid"));
        AdminService adminService = new AdminService();
        adminService.deleteProduct(pid);

        int uid = (int) session.getAttribute("uid");
        LogEntryService logService = new LogEntryService();
        logService.logAction("Danger", "Xóa sản phẩm", uid, "id product: "+pid, "Empty");
    }

    private void searchProduct(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        Integer productId = request.getParameter("productID") != null && !request.getParameter("productID").isEmpty()
                ? Integer.parseInt(request.getParameter("productID"))
                : null;
        String productName = request.getParameter("productName");
        String status = request.getParameter("status");

        AdminService adminService = new AdminService();
        List<ProductManage> filteredProducts = adminService.filterProducts(productId, productName, status);

        request.setAttribute("products", filteredProducts);
        request.getRequestDispatcher("/productManagement.jsp").forward(request, response);
    }

    private void addKey(HttpServletRequest request) {
        int pid = Integer.parseInt(request.getParameter("pid"));
        String keys = request.getParameter("keys");

        // Tách các key theo dòng
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
}