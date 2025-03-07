<%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 1/4/2025
  Time: 4:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý thông tin chuyển khoản</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"
            integrity="sha512-6sSYJqDreZRZGkJ3b+YfdhB3MzmuP9R7X1QZ6g5aIXhRvR1Y/N/P47jmnkENm7YL3oqsmI6AK+V6AD99uWDnIw=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/bank.css">
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
            <!-- Nút Thêm ngân hàng -->
            <button class="btn-add" onclick="toggleAddForm()">+ Thêm ngân hàng</button>
            <form action="insertBankServlet" method="post" id="addBankForm" class="hidden">
                <input name="bankId" type="text" id="addBankId" placeholder="Mã ngân hàng" required>
                <input name="bankName" type="text" id="addBankName" placeholder="Tên ngân hàng" required>
                <input name="bankNumber" type="text" id="addBankNum" placeholder="Số tài khoản" required>
                <input name="bankOwner" type="text" id="addBankOwn" placeholder="Chủ tài khoản" required>
                <input name="bankQr" type="text" id="addBankImage" placeholder="Link mã qr (URL)" required>
                <button type="submit">Lưu</button>
            </form>
            <!-- Nút Sửa thông tin -->
            <button class="btn-edit" onclick="toggleEditForm()">Sửa thông tin</button>
            <form id="editBankForm" class="hidden">
                <input type="text" id="editBankId" placeholder="Nhập mã ngân hàng để sửa" required>
                <button type="button" onclick="loadBankInfo()">Tìm kiếm</button>
            </form>
            <div id="editBankDetails" class="hidden">
                <form action="editBank" method="post">
                    <table style="margin-bottom: 10px">
                        <tr>
                            <td>Mã ngân hàng:</td>
                            <td><input name="id" type="text" id="editBID" required></td>
                        </tr>
                        <tr>
                            <td>Tên ngân hàng:</td>
                            <td><input name="name" type="text" id="editBankName" required></td>
                        </tr>
                        <tr>
                            <td>Số tài khoản:</td>
                            <td><input name="number" type="text" id="editBankNum" required></td>
                        </tr>
                        <tr>
                            <td>Chủ tài khoản:</td>
                            <td><input name="owner" type="text" id="editBankOwn" required></td>
                        </tr>
                        <tr>
                            <td>Mã qr (URL):</td>
                            <td><input name="qr" type="text" id="editBankImage" required></td>
                        </tr>
                    </table>
                    <button type="submit">Lưu</button>
                </form>
            </div>
            <!-- Bảng thông tin -->
            <table>
                <thead>
                <tr>
                    <th>Mã ngân hàng</th>
                    <th>Tên ngân hàng</th>
                    <th>Số tài khoản</th>
                    <th>Chủ tài khoản</th>
                    <th>Mã qr</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody id="bankTable">
                <!-- Các ngân hàng cố định được thêm vào ở đây -->
                <c:forEach var="bank" items="${banks}">
                    <tr>
                        <td>${bank.id}</td>
                        <td>${bank.name}</td>
                        <td>${bank.number}</td>
                        <td>${bank.owner}</td>
                        <td><img src="${bank.qr}"
                                 style="width: 50px; height: 50px;">
                        </td>
                        <td class="icon-trash">
                            <a href="deleteServlet?Bid=${bank.id}" class="delete" style="color: black"><i class="fa-solid fa-trash"></i></a>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </section>
    </div>
</div>
<script src="${pageContext.request.contextPath}/Js/banking.js"></script>
</body>
</html>
