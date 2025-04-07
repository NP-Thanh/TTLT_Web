<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ page import="java.util.List" %>--%>
<%--<%@ page import="vn.edu.hcmuaf.fit.web.model.OrderWithUser" %>--%>
<%--<%@ page import="vn.edu.hcmuaf.fit.web.model.Order" %>--%>
<%--<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>--%>
<%--<%@ page import="vn.edu.hcmuaf.fit.web.model.Product" %>--%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%--<%--%>
<%--  List<OrderWithUser> list = (List<OrderWithUser>) request.getAttribute("list");--%>
<%--%>--%>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Quản lý đơn hàng</title>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <style>
    /* Tổng quan */
    html, body, p {
      width: 100%;
      margin: 0;
      padding: 0;
      font-size: 14px;
      color: #404040;
      font-family: Arial, sans-serif;
      height: 100%;
    }

    .btn-add {
      background: #4cbf6c;
      font-weight: bold;
    }

    .btn-add:hover {
      background: #409f5b;
    }

    .btn-edit:hover {
      background: #0063d1;
    }

    /* Content */
    .content {
      margin-left: 250px;
      padding: 20px;
    }

    header h1 {
      margin-bottom: 20px;
    }

    button {
      padding: 10px 15px;
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      margin-right: 10px;
    }


    button:disabled {
      background-color: #ccc;
      cursor: not-allowed;
    }

    .btn-reset {
      background-color: #f61414;
    }

    .btn-sendkey {
      background-color: #ff2a2a;
    }

    form.hidden,
    #editProductDetails.hidden {
      display: none;
    }

    form input,
    form button {
      display: block;
      margin-top: 10px;
      margin-bottom: 10px;
      padding: 8px;
      width: 100%;
      max-width: 400px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    /* Bảng sản phẩm */
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
      background: #fff;
    }

    table th,
    table td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: left;
    }

    table th {
      background-color: #f2f2f2;
    }

    table td.status {
      font-weight: bold;
    }

    table td.status.in-stock {
      color: green;
    }

    table td.status.out-of-stock {
      color: red;
    }

    .hidden {
      display: none !important;
    }

    #specList div {
      display: flex;
      justify-content: space-between;
      margin-bottom: 5px;
    }

    /* Căn giữa nội dung trong thẻ <td> */
    .icon-trash {
      text-align: center; /* Căn giữa nội dung theo chiều ngang */
      vertical-align: middle; /* Căn giữa nội dung theo chiều dọc */
    }

    /* Căn giữa biểu tượng trash */
    .delete {
      display: inline-block; /* Giữ nguyên kích thước biểu tượng */
      text-align: center;
      vertical-align: middle;
      line-height: 1; /* Giảm khoảng cách giữa các dòng, nếu cần */
    }

  </style>
</head>
<body>
<div class="admin-container">
  <div class="slidebar">
    <jsp:include page="slibar.jsp"/>
  </div>

  <div class="content">
    <header>
      <h2>Quản lý thông tin giỏ hàng</h2>
    </header>
    <section>
      <!-- Nút reset -->
      <a href="CartManagement">
        <button class="btn-reset">Reset</button>
      </a>
      <!-- Bảng thông tin -->
      <table>
        <thead>
        <tr>
          <th>Mã giỏ hàng</th>
          <th>Khách hàng</th>
          <th>Sản phẩm</th>
          <th>Số Lượng</th>
          <th>Giá tiền</th>
          <th>Thời gian</th>
<%--          <th>Hành động</th>--%>
        </tr>
        </thead>
        <tbody id="cartTable">
        <c:forEach var="cart" items="${cart}">
          <tr data-id="${cart.cartId}"
              data-nameUser="${cart.userName}"
              data-nameProduct="${cart.productName}"
              data-quantity="${cart.quantity}"
              data-price="${cart.price}"
              data-time="${cart.created_at}">

            <td>${cart.cartId}</td>
            <td>${cart.userName}</td>
            <td>${cart.productName}</td>
            <td>${cart.quantity}</td>
            <td>${cart.price}</td>
            <td>${cart.created_at}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </section>
  </div>
</div>
</body>
</html>
