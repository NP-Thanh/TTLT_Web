package vn.edu.hcmuaf.fit.web.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.web.model.Product;
import vn.edu.hcmuaf.fit.web.servieces.ListProduct;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/load")
public class LoadProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy tham số "category" từ request
        String category = req.getParameter("category");
        System.out.println(category);

        // Lấy danh sách sản phẩm từ DAO
        ListProduct listProduct = new ListProduct();
        List<Product> products = listProduct.getAllProductWithType(category);

        // Gửi phản hồi HTML
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        for (Product product : products) {
            out.println(
                    "<a href=\"ProductDetail?id="+product.getId()+"\" class=\"product\" data-category=\""+product.getId()+"\" style=\"text-decoration: none; color: inherit;\">\n" +
                            "                  <div class=\"img\">\n" +
                            "                    <!-- Hiển thị hình ảnh, kiểm tra null -->\n" +
                            "                    <img src=\"" +product.getImage() + "\" alt=\"" +product.getName()+"\" class=\"img-p\" style=\"width: 100%; height: 220px\">\n" +
                            "                  </div>\n" +
                            "                  <div class=\"infor-p\" style=\"padding: 10px 16px;\">\n" +
                            "                    <!-- Tên sản phẩm -->\n" +
                            "                    <span class=\"title-p t-none t-black d-block\">"+product.getName()+"</span>\n" +
                            "                    <!-- Thời gian sử dụng -->\n" +
                            "                    <span class=\"t-none t-black d-block\" style=\"padding: 10px 0; font-size: 13px;\">\n" +
                            "                        "+product.getDuration()+"\n" +
                            "                    </span>\n" +
                            "                    <div class=\"price-p d-flex t-between\">\n" +
                            "                      <!-- Giá sản phẩm -->\n" +
                            "                      <span class=\"t-none t-black\" style=\"font-weight: 550;\">"+product.getPrice()+"₫</span>\n" +
                            "                    </div>\n" +
                            "                  </div>\n" +
                            "                </a>"
            );
        }
    }
}



