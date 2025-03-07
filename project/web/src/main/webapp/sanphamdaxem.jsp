<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sản phẩm vừa xem</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="../CSS/sanphamvuaxem.css">
</head>
<body>
<div id="page" class="page">
  <iframe class="iframe-header" src="../HTML/header.html" frameborder="0" scrolling="no"></iframe>
  <div class="container">
    <div class="sidebar">
      <ul>
        <li><a href="../HTML/taikhoan.html"><i class="fas fa-user"></i>Tài khoản</a></li>
        <li><a href="../HTML/sanphamvuaxem.jsp" style="color: #007bff;"><i class="fas fa-clock"></i>Sản phẩm vừa xem</a></li>
        <li><a href="../HTML/lichsugiaodich.html"><i class="fas fa-history"></i>Lịch sử giao dịch</a></li>
      </ul>
    </div>
    <div class="main-content">
      <h2>Sản Phẩm Vừa Xem</h2>
      <p>Danh sách các sản phẩm bạn vừa xem gần đây.</p>
      <div class="product-list">
        <%
          List<Product> products = (List<Product>) request.getAttribute("products");
          for (Product product : products) {
        %>
        <div class="product-card">
          <img src="<%= product.getImageUrl() %>" alt="<%= product.getName() %>">
          <h3 class="product-title"><%= product.getName() %></h3>
          <p class="product-price"><%= product.getPrice() %>đ</p>
          <a href="../HTML/chi_tiet_sp.html" class="btn">Xem Chi Tiết</a>
        </div>
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