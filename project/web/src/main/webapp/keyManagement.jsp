<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Key</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/keyManagement.css">
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
            <button class="btn-find" onclick="toggleFind()">Tìm kiếm</button>
            <form class="hidden" id="findKeyForm">
                <input type="text" id="searchKeyID" name="keyID" placeholder="Mã key">
                <button type="button" id="searchBtn">Tìm kiếm</button>
            </form>

            <button class="btn-add" onclick="toggleAddForm()">+ Thêm Key</button>
            <form id="addKeyForm" class="hidden">
                <input type="hidden" name="action" value="add">
                <input name="pid" type="text" id="pid" placeholder="Mã sản phẩm" required>
                <span id="statusMessage" style="font-size: 14px;"></span>
                <input name="keys" type="text" id="addKeyName" placeholder="Tên Key">
                <button type="submit" id="saveBtn" name="action" value="save" disabled>LƯU</button>
            </form>

            <div id="editKeyDetails" class="hidden">
                <!-- Form cập nhật key nếu dùng modal hoặc chi tiết -->
            </div>

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
                <c:forEach var="key" items="${keys}">
                    <tr data-id="${key.id}">
                        <td>${key.id}</td>
                        <td>${key.key}</td>
                        <td>${key.productName}</td>
                        <td>${key.productType}</td>
                        <td class="status ${key.status eq 'đã xuất' ? 'in-stock' : 'out-of-stock'}">${key.status}</td>
                        <td><img src="${key.image}" style="width: 50px; height: 50px;"></td>
                        <td>
                            <div class="d-flex align-items-center">
                                <button class="edit-btn" data-id="${key.id}">Update</button>
                                <button class="delete-btn" data-id="${key.id}">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
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
    function toggleAddForm() {
        $("#addKeyForm").toggleClass("hidden");
    }

    function toggleFind() {
        $("#findKeyForm").toggleClass("hidden");
    }

    $(document).ready(function () {
        function loadKeys() {
            $.get("KeyManagement", function (data) {
                $("#keyTable").html($(data).find("#keyTable").html());
            });
        }

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

        $("#addKeyForm").on("submit", function (e) {
            e.preventDefault();
            $.post("KeyManagement", {
                action: "add",
                pid: $("#pid").val(),
                keys: $("#addKeyName").val()
            }, function () {
                alert("Đã thêm key!");
                loadKeys();
                $("#addKeyForm")[0].reset();
                $("#statusMessage").text("");
            });
        });

        $("#searchBtn").on("click", function () {
            const keyID = $("#searchKeyID").val();
            $.post("KeyManagement", {
                action: "search",
                keyID: keyID
            }, function (data) {
                $("#keyTable").html($(data).find("#keyTable").html());
            });
        });

        $(document).on("click", ".delete-btn", function () {
            if (!confirm("Bạn chắc chắn muốn xóa key này?")) return;
            let kid = $(this).data("id");
            $.post("KeyManagement", {
                action: "delete",
                kid: kid
            }, function () {
                alert("Đã xóa!");
                loadKeys();
            });
        });

        $(document).on("click", ".edit-btn", function () {
            let row = $(this).closest("tr");
            let id = $(this).data("id");
            let key = row.find("td:eq(1)").text();
            let name = row.find("td:eq(2)").text();
            let type = row.find("td:eq(3)").text();
            let img = row.find("td:eq(5)").find("img").attr("src");

            let newKey = prompt("Sửa key:", key);
            if (newKey === null || newKey.trim() === "") return;

            $.post("KeyManagement", {
                action: "update",
                kid: id,
                kName: newKey,
                pName: name,
                pType: type,
                pImg: img
            }, function () {
                alert("Đã cập nhật!");
                loadKeys();
            });
        });
    });
</script>
</body>
</html>
