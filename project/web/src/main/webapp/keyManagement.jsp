<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Key</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"
            integrity="sha512-6sSYJqDreZRZGkJ3b+YfdhB3MzmuP9R7X1QZ6g5aIXhRvR1Y/N/P47jmnkENm7YL3oqsmI6AK+V6AD99uWDnIw=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/keyManagement.css">
    <script src="https://cdn.ckeditor.com/4.21.0/standard/ckeditor.js"></script>
</head>
<body>
<div class="admin-container">
    <div class="slidebar">
        <jsp:include page="slibar.jsp"/>
    </div>

    <div class="content">
        <header>
            <h2>Quản lý Key</h2>
        </header>
        <section>
            <%--Tìm kiếm--%>
            <button class="btn-find" onclick="toggleFind()">Tìm kiếm</button>
            <form class="hidden" id="findKeyForm" action="${pageContext.request.contextPath}/KeyManagement" method="post">
                <input type="hidden" name="action" value="search">
                <input type="text" name="keyID" placeholder="Mã key">
                <button type="submit">Tìm kiếm</button>
            </form>

            <!-- Nút Thêm sản phẩm -->
            <button class="btn-add" onclick="toggleAddForm()">+ Thêm Key</button>
            <form action="${pageContext.request.contextPath}/KeyManagement" method="post" id="addKeyForm" class="hidden">
                <input type="hidden" name="action" value="add">
                <input name="pid" type="text" id="pid" placeholder="Mã sản phẩm" required>
                <span id="statusMessage" style="font-size: 14px;"></span>

                <input name="keys" type="text" id="addKeyName" placeholder="Tên Key">
                <button type="submit" id="saveBtn" name="action" value="save" disabled> LƯU </button>
            </form>

            <%--                Form chỉnh sửa chi tiết sản phẩm--%>
            <div id="editKeyDetails" class="hidden">
                <form action="${pageContext.request.contextPath}/KeyManagement" method="post">
                    <input type="hidden" name="action" value="update">
                    <table style="margin-bottom: 10px">
                        <tr>
                            <td>Mã Key:</td>
                            <td><input type="text" id="editIDKey" name="kid" required readonly></td>
                        </tr>
                        <tr>
                            <td>Key:</td>
                            <td><input type="text" id="editNameKey" name="kName" required></td>
                        </tr>

                        <tr>
                            <td>Tên sản phẩm:</td>
                            <td><input type="text" id="editKeyName" name="pName" readonly></td>
                        </tr>
                        <tr>
                            <td>Loại:</td>
                            <td><input type="text" id="editKeyType" name="pType" readonly></td>
                        </tr>
                        <tr>
                            <td>Hình ảnh (URL):</td>
                            <td><input type="text" id="editKeyImage" name="pImg" readonly></td>
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

            <!-- Bảng sản phẩm -->
            <table>
                <thead>
                <tr>
                    <th>Mã Key</th>
                    <th>Key</th>
                    <th>Tên sản phẩm</th>
                    <th>Loại</th>
                    <th>Tình trạng</th>
                    <th>Hình ảnh</th>
                    <th>Hành động</th>

                </tr>
                </thead>
                <tbody id="keyTable">
                <!-- Các sản phẩm cố định được thêm vào ở đây -->
                <c:forEach var="key" items="${keys}">
                    <tr data-id="${key.id}"
                        data-key="${key.key}"
                        data-name="${key.productName}"
                        data-type="${key.productType}"
                        data-status="${key.status}"
                        data-image="${key.image}">

                        <td>${key.id}</td>
                        <td>${key.key}</td>
                        <td>${key.productName}</td>
                        <td>${key.productType}</td>
                        <td class="status ${key.status eq 'đã xuất' ? 'in-stock' : 'out-of-stock'}">
                                ${key.status}
                        </td>
                        <td><img src="${key.image}" style="width: 50px; height: 50px;"></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <button class="d-flex align-items-center"
                                        style="width: 60px; height: 30px; padding-left: 8px; font-weight: bold"
                                        onclick="editKey(this)">Update
                                </button>
                                <form action="${pageContext.request.contextPath}/KeyManagement" method="post" style="margin: 0">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="kid" value="${key.id}">
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function () {
        $("#pid").on("input", function () {
            var pid = $(this).val().trim();
            if (pid === "") {
                $("#statusMessage").text("Vui lòng nhập Mã sản phẩm!").css("color", "red");
                $("#saveBtn").prop("disabled", true);
                return;
            }

            $.get("KeyManagement", { pid: pid }, function (response) {
                $("#statusMessage").text(response.message).css("color", response.valid ? "green" : "red");
                $("#saveBtn").prop("disabled", !response.valid);
            });
        });
    });
</script>
<script src="${pageContext.request.contextPath}/Js/keyManagement.js"></script>
</body>

</html>
