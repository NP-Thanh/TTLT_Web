<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.OrderWithUser" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Product" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử giao dịch</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<%--    <link rel="stylesheet" type="text/css" href="CSS_JSP/lichsugiaodich.css">--%>
    <style>
        body, html {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #404040;
            font-size: 14px;
        }

        .containerlsgd {
            display: flex;
            width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 200px;
        }

        #page {
            display: flex;
            flex-direction: column;
            height: 100%;
        }

        .iframe-header {
            height: 170px;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .iframe-footer {
            width: 100%;
            height: 375px;
            position: relative;
            margin-top: auto;
            background: #076ce3;
        }

        .sidebar {
            width: 200px;
            border-right: 1px solid #ddd;
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .sidebar li {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }

        .sidebar li a {
            text-decoration: none;
            color: #333;
            display: flex;
            align-items: center;
        }

        .sidebar li a:hover {
            background-color: #e9ecef;
        }

        .sidebar li a i {
            margin-right: 8px;
        }

        .main-content {
            flex: 1;
            padding: 20px;
        }

        .main-content h2 {
            margin-bottom: 10px;
        }

        .main-content p {
            color: #666;
            margin-bottom: 20px;
        }

        .filters {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }

        .filters select, .filters input {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .filters button {
            padding: 8px 12px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .filters button:hover {
            background-color: #0056b3;
        }

        .table {
            width: 100%;
            border-collapse: collapse;
        }

        .table th, .table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        .table th {
            background-color: #f4f4f4;
        }

        .table td {
            color: #777;
        }
    </style>
</head>
<body>
<div id="page" class="page">
    <div class="iframe-header">
        <jsp:include page="header.jsp"/>
    </div>
    <div class="containerlsgd">
        <div class="sidebar">
            <ul>
                <li><a href="account"><i class="fas fa-user"></i>Tài khoản</a></li>
                <li><a href="view"><i class="fas fa-clock"></i>Sản phẩm vừa xem</a></li>
                <li><a href="lichsugiaodich" style="color: #007bff;"><i
                        class="fas fa-history"></i>Lịch sử giao dịch</a></li>
                <li><a href="logout" style="color: #ff1e1e; font-weight: 600;"><i></i>Đăng xuất</a></li>
            </ul>
        </div>
        <div class="main-content">
            <h2>Lịch sử giao dịch</h2>
            <p>Hiển thị toàn bộ các giao dịch bạn đã thực hiện</p>
            <table class="table">
                <thead>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Sản phẩm</th>
                    <th>Thời gian</th>
                    <th>Số tiền</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<OrderWithUser> orders = (List<OrderWithUser>) request.getAttribute("orders");
                    if (orders != null && !orders.isEmpty()) {
                        for (OrderWithUser order : orders) {
                %>
                <tr>
                    <td><a href="order?id=<%= order.getOrder().getId() %>&pid=<%= order.getProduct().get(0).getId() %>"><%=order.getOrder().getId()%></a></td>
                    <td>
                        <%
                            StringBuilder products = new StringBuilder();
                            for (Product product : order.getProduct()) {
                                products.append(product.getName()).append(", ");
                            }
                            if (products.length() > 0) {
                                products.setLength(products.length() - 2); // Xóa dấu phẩy cuối
                            }
                        %>
                        <%= products.toString() %>
                    </td>
                    <td><%= order.getOrder().getDate() %></td>
                    <td><%= order.getOrder().getTotal_amount() %></td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr id="noResultRow">
                    <td colspan="4" style="text-align: center;">Không tìm thấy giao dịch</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
    <div id="iframe-footer" style="margin-top: 20px;">
        <jsp:include page="footer.jsp"/>
    </div>
</div>
<script src="../JS/lichsugiaodich.js"></script>
</body>
</html>