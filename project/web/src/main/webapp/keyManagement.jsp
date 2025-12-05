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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
            <p id="message" style="color: red; display: none; margin-top: 10px;"></p>

            <form action="${pageContext.request.contextPath}/KeyManagement" method="post" id="addKeyForm" class="hidden">

                <input type="hidden" name="action" value="add">
                <input name="pid" type="text" id="pid" placeholder="Mã sản phẩm" required>
                <span id="statusMessage" style="font-size: 14px;"></span>
                <input name="keys" type="text" id="addKeyName" placeholder="Tên Key" required>
                <button type="submit" id="saveBtn" name="action" value="save" disabled>LƯU</button>
            </form>

            <div id="editKeyDetails" class="hidden">
                <form action="${pageContext.request.contextPath}/KeyManagement" method="post">
                    <input type="hidden" name="action" value="update">
                    <table style="margin-bottom: 10px">
                        <tr style="display: none;"> <td>Mã Key:</td>
                            <td><input type="text" id="editIDKey" name="kid" required readonly></td>
                        </tr>
                        <tr> <td>Key:</td>
                            <td><input type="text" id="editNameKey" name="kName" required></td>
                        </tr>
                        <tr style="display: none;"> <td>Tên sản phẩm:</td>
                            <td><input type="text" id="editKeyName" name="pName" readonly></td>
                        </tr>
                        <tr style="display: none;"> <td>Loại:</td>
                            <td><input type="text" id="editKeyType" name="pType" readonly></td>
                        </tr>
                        <tr style="display: none;"> <td>Hình ảnh (URL):</td>
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
                loadKeys();
                $("#addKeyForm")[0].reset();
                $("#statusMessage").text("");
                const messageElement = $('#message');
                messageElement.text("Thêm key thành công!").css("color", "green").show();
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
            // if (!confirm("Bạn chắc chắn muốn xóa key này?")) return;
            let kid = $(this).data("id");
            $.post("KeyManagement", {
                action: "delete",
                kid: kid
            }, function () {
                loadKeys();
                const messageElement = $('#message');
                messageElement.text("Xóa key thành công!").css("color", "green").show();

            });
        });

        $(document).on("click", ".edit-btn", function () {
            let row = $(this).closest("tr");
            let id = $(this).data("id");
            let key = row.find("td:eq(1)").text();
            let name = row.find("td:eq(2)").text();
            let type = row.find("td:eq(3)").text();
            let img = row.find("td:eq(5)").find("img").attr("src");

            $("#editIDKey").val(id);
            $("#editNameKey").val(key);
            $("#editKeyName").val(name);
            $("#editKeyType").val(type);
            $("#editKeyImage").val(img);

            // 2. Hiển thị form sửa
            $("#editKeyDetails").removeClass("hidden");

            $('html, body').animate({
                scrollTop: $("#editKeyDetails").offset().top
            }, 500);

            $("#editKeyDetails form").on("submit", function(e) {
                e.preventDefault();

                const formData = {
                    action: "update",
                    kid: $("#editIDKey").val(),
                    kName: $("#editNameKey").val(),
                    pName: $("#editKeyName").val(),
                    pType: $("#editKeyType").val(),
                    pImg: $("#editKeyImage").val()
                };

                $.post("KeyManagement", formData, function () {
                    const messageElement = $('#message');
                    messageElement.text("Thêm key thành công!").css("color", "green").show();
                    loadKeys();
                    cancelled();
                });
            });
        });
    });
</script>
<script src="${pageContext.request.contextPath}/Js/keyManagement.js"></script>

</body>
</html>
