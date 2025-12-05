<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Product" %>
<%@ page import="java.text.DecimalFormat" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sản phẩm vừa xem</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="CSS_JSP/sanphamvuaxem.css">
</head>
<body>
<div id="page" class="page">
    <iframe class="iframe-header" src="header.jsp" frameborder="0" scrolling="no"></iframe>
    <div class="container">
        <div class="sidebar">
            <ul>
                <li><a href="taikhoan.jsp"><i class="fas fa-user"></i>Tài khoản</a></li>
                <li><a href="view" style="color: #007bff;"><i class="fas fa-clock"></i>Sản phẩm vừa
                    xem</a></li>
                <li><a href="lichsugiaodich"><i class="fas fa-history"></i>Lịch sử giao dịch</a></li>
            </ul>
        </div>
        <div class="main-content">
            <h2>Sản Phẩm Vừa Xem</h2>
            <p>Danh sách các sản phẩm bạn vừa xem gần đây.</p>
            <div class="product-list">
                <%
                    List<Product> products = (List<Product>) request.getAttribute("products");
                    if (products != null && !products.isEmpty()) {
                        for (Product product : products) {

                            // Định dạng số tiền
                            DecimalFormat formatter = new DecimalFormat("#,###");
                            String formattedPrice = formatter.format(product.getPrice());

                %>
                <div class="product-card">
                    <img src="<%= product.getImage() %>" alt="<%= product.getName() %>" style="width: 200px; height: 200px">
                    <h3 class="product-title"><%= product.getName() %>
                    </h3>
                    <p class="product-price"><%= formattedPrice %>đ</p>
                    <a href="ProductDetail?id=<%= product.getId() %>" class="btn">Xem Chi Tiết</a>
                </div>
                <%
                    }
                } else {
                %>
                <p>Không có sản phẩm nào vừa xem.</p>
                <%
                    }
                %>
            </div>
            <div class="navigation">
                <button class="nav-btn" id="prev-btn"><i class="fas fa-chevron-left"></i></button>
                <button class="nav-btn" id="next-btn"><i class="fas fa-chevron-right"></i></button>
            </div>
        </div>
    </div>
    <iframe class="iframe-footer" src="../HTML/footer.html" frameborder="0" scrolling="no"></iframe>
</div>
<script src="../JS/sanphamvuaxem.js"></script>
</body>
</html>