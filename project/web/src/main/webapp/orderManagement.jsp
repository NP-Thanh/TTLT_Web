<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.OrderWithUser" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Order" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Product" %>
<%@ page import="java.text.DecimalFormat" %><%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 18/01/2025
  Time: 8:35 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%
    List<OrderWithUser> list = (List<OrderWithUser>) request.getAttribute("list");
%>
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
            <h2>Quản lý thông tin chuyển khoản</h2>
        </header>
        <section>
            <!-- Nút tìm kiếm -->
            <button class="btn-find" onclick="toggleEditForm()">Tìm kiếm</button>
            <form class="hidden" id="findOrderForm" action="orderManagement" method="post">
                <input type="text" name="orderID" placeholder="Mã đơn hàng">
                <input type="text" name="productName" placeholder="Tên sản phẩm">
                <select name="status">
                    <option value="">Tất cả</option>
                    <option value="NotPaid">Chưa thanh toán</option>
                    <option value="Paid">Đã thanh toán</option>
                    <option value="Pending">Chờ xử lý</option>
                </select>
                <button type="submit">Tìm kiếm</button>
            </form>
            <!-- Nút reset -->
            <a href="orderManagement">
                <button class="btn-reset">Reset</button>
            </a>
            <!-- Bảng thông tin -->
            <table>
                <thead>
                <tr>
                    <th>Mã đơn hàng</th>
                    <th>Khách hàng</th>
                    <th>Email</th>
                    <th>Sản phẩm</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody id="bankTable">
                <!-- Các ngân hàng cố định được thêm vào ở đây -->
                <%
                    for (OrderWithUser orderWithUser : list) {
                        Order order = orderWithUser.getOrder();
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formattedTotal = formatter.format(order.getTotal_amount());
                        User user = orderWithUser.getUser();
                        List<Product> products = orderWithUser.getProduct();
                        StringBuilder product = new StringBuilder();
                        for (Product p : products) {
                            product.append(p.getName()).append("<br>");
                        }
                %>
                <tr>
                    <td><%=order.getId()%>
                    </td>
                    <td><%=user.getName()%>
                    </td>
                    <td><%=user.getEmail()%>
                    </td>
                    <td><%=product%>
                    </td>
                    <td><%=formattedTotal%>đ</td>
                    <%
                        if (order.getStatus().equals("Đã thanh toán")) {
                    %>
                    <td style="color: #2fa50c; font-weight: 600"><%=order.getStatus()%>
                    </td>
                    <td class="icon-trash">
                        <a href="DeleteOrder?oid=<%=order.getId()%>" class="delete" style="color: black"><i
                                class="fa-solid fa-trash"></i></a>
                    </td>
                    <%
                    } else {
                    %>
                    <td style="color: #ea0b0b; font-weight: 600"><%=order.getStatus()%>
                    </td>
                    <td class="icon-trash">
                        <form action="sendKey?oid=<%=order.getId()%>" method="post">
                            <button class="btn-sendkey">Xử lý</button>
                        </form>
                    </td>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>

                </tbody>
            </table>
        </section>
    </div>
</div>
<script>
    function toggleEditForm() {
        const form = document.getElementById("findOrderForm");
        form.classList.toggle("hidden");
    }

</script>
</body>
</html>
