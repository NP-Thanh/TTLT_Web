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
            <form class="hidden" id ="addBankForm" action="${pageContext.request.contextPath}/bank" method="post" >
                <input type="hidden" name="action" value="add">
                <input name="bankName" type="text" id="addBankName" placeholder="Tên ngân hàng" required>
                <input name="bankNumber" type="text" id="addBankNum" placeholder="Số tài khoản" required>
                <input name="bankOwner" type="text" id="addBankOwn" placeholder="Chủ tài khoản" required>
                <input name="bankQr" type="text" id="addBankImage" placeholder="Link mã qr (URL)" required>
                <button type="submit">Lưu</button>
            </form>

            <div id="editBankDetails" class="hidden">
                <form action="${pageContext.request.contextPath}/bank" method="post">
                    <input type="hidden" name="action" value="update">
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
                    <div class="d-flex">
                        <button style="width: 100px !important;" type="submit">Lưu</button>
                        <button style="width: 70px !important; background: #f12323" type="button" onclick="cancelled()">
                            Hủy
                        </button>
                    </div>
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
                    <tr data-id="${bank.id}"
                        data-name="${bank.name}"
                        data-num="${bank.number}"
                        data-own="${bank.owner}"
                        data-image="${bank.qr}">

                        <td>${bank.id}</td>
                        <td>${bank.name}</td>
                        <td>${bank.number}</td>
                        <td>${bank.owner}</td>
                        <td><img src="${bank.qr}"
                                 style="width: 50px; height: 50px;">
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <button class="d-flex align-items-center"
                                        style="width: 60px; height: 30px; padding-left: 8px; font-weight: bold"
                                        onclick="editBank(this)">Update
                                </button>

                                <form action="${pageContext.request.contextPath}/bank" method="post" style="margin: 0">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${bank.id}">
                                    <button type="submit"
                                            style="color: #fbfbfb;width: 30px; height: 30px; background: #ff3d3d; border-radius: 4px; padding-left: 8px">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </section>
    </div>
</div>
<%--<script>--%>
<%--    function editBank(button) {--%>
<%--        const row = button.closest('tr'); 0--%>

<%--        document.getElementById('editBankDetails').classList.remove('hidden'); // Hiển thị form sửa--%>

<%--        // Gán dữ liệu vào các input--%>
<%--        document.getElementById('editBID').value = row.getAttribute('data-id');--%>
<%--        document.getElementById('editBankName').value = row.getAttribute('data-name');--%>
<%--        document.getElementById('editBankNum').value = row.getAttribute('data-num')--%>
<%--        document.getElementById('editBankOwn').value = row.getAttribute('data-own');--%>
<%--        document.getElementById('editBankImage').value = row.getAttribute('data-image');--%>
<%--    }--%>
<%--</script>--%>
<script src="${pageContext.request.contextPath}/Js/banking.js"></script>
</body>
</html>
