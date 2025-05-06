<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.UserAdmin" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Role" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý khách hàng</title>
</head>
<style>
    :root {
        --primary-color: #4a90e2;
        --secondary-color: #f5f5f5;
        --text-color: #333;
        --border-color: #ddd;
        --success-color: #28a745;
        --danger-color: #dc3545;
    }

    body {
        font-family: Arial, sans-serif;
        color: var(--text-color);
        margin: 0;
        padding: 0;
        background-color: var(--secondary-color);
    }

    .admin-container {
        display: flex;
        min-height: 100vh;
    }

    .content {
        flex-grow: 1;
        padding-left: 228px;
    }

    header {
        background-color: var(--primary-color);
        color: white;
        padding: 20px;
        margin-bottom: 20px;
    }

    h1, h2 {
        margin: 0;
    }

    .btn {
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 14px;
        transition: background-color 0.3s;
    }

    .btn-primary {
        background-color: var(--primary-color);
        color: white;
    }

    .btn-secondary {
        background-color: var(--secondary-color);
        color: var(--text-color);
    }

    .btn-success {
        background-color: var(--success-color);
        color: white;
    }

    .btn-danger {
        background-color: var(--danger-color);
        color: white;
    }

    .btn-text {
        background-color: transparent;
        color: var(--primary-color);
    }

    .btn:hover {
        opacity: 0.8;
    }

    .actions, .filters, .user-table {
        background-color: white;
        border-radius: 4px;
        padding: 20px;
        margin-bottom: 20px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .filter-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 15px;
    }

    .filter-group {
        display: flex;
        flex-wrap: wrap;
        gap: 15px;
    }

    .filter-item {
        flex: 1;
        min-width: 200px;
    }

    label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    select, input[type="text"] {
        width: 100%;
        padding: 8px;
        border: 1px solid var(--border-color);
        border-radius: 4px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 12px;
        text-align: left;
        border-bottom: 1px solid var(--border-color);
    }

    th {
        background-color: var(--secondary-color);
        font-weight: bold;
    }

    .hidden {
        display: none;
    }

    .btn-group {
        display: flex;
        gap: 10px;
        margin-top: 10px;
    }

    @media (max-width: 768px) {
        .filter-group {
            flex-direction: column;
        }

        .filter-item {
            width: 100%;
        }
    }

</style>
<body>
<div class="admin-container">
    <div class="slidebar">
        <jsp:include page="slibar.jsp"/>
    </div>

    <div class="content">
        <header>
            <h1>Quản lý khách hàng</h1>
        </header>

        <main>
            <div id="editForm" class="hidden">
                <form action="update-user" method="post">
                    <input type="hidden" id="userId" name="user_id"> <!-- Chứa ID người dùng -->
                    <div>
                        <label for="role">Role:</label>
                        <select id="role" name="role_id" required>
                            <option value="1">Admin</option>
                            <option value="2">User</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success">Cập nhật</button>
                    <button type="button" class="btn btn-secondary" id="cancelEdit">Hủy</button>
                </form>
            </div>

            <script>
                document.addEventListener("DOMContentLoaded", () => {
                    const editForm = document.getElementById("editForm");
                    const userIdInput = document.getElementById("userId");
                    const roleInput = document.getElementById("role");
                    const cancelEdit = document.getElementById("cancelEdit");

                    // Gán sự kiện cho nút Sửa
                    document.querySelectorAll(".btn-edit").forEach((btn) => {
                        btn.addEventListener("click", () => {
                            const userId = btn.getAttribute("data-id");
                            const row = btn.closest("tr");
                            const role = row.children[4].textContent; // Lấy giá trị Role từ bảng

                            // Gán giá trị vào form
                            userIdInput.value = userId;
                            roleInput.value = role.trim() === "Admin" ? "1" : "2";

                            // Hiển thị form sửa
                            editForm.classList.remove("hidden");
                        });
                    });

                    // Ẩn form khi nhấn nút Hủy
                    cancelEdit.addEventListener("click", () => {
                        editForm.classList.add("hidden");
                    });
                });

            </script>

            <section class="user-table">
                <table>
                    <thead>
                    <tr>
                        <th>Mã khách hàng</th>
                        <th>Tên khách hàng</th>
                        <th>Email</th>
                        <th>Số điện thoại</th>
                        <th>Role</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody id="userTable">
                    <%
                        List<UserAdmin> users = (List<UserAdmin>) request.getAttribute("users");
                        for (UserAdmin user : users) {
                            User u = user.getUser();
                            Role r = user.getRole();
                    %>
                    <tr>
                        <td><%=u.getId()%></td>
                        <td><%=u.getName()%></td>
                        <td><%=u.getEmail()%></td>
                        <td><%=u.getPhone()%></td>
                        <td><%=r.getRole_name()%></td>
                        <td class="font-600"
                            style="color: ${user.status == 'Hoạt động' ? 'green' : 'red'}"><%=u.getStatus()%></td>
                        <td>
                            <button class="btn btn-primary btn-edit" data-id="<%=u.getId()%>">Sửa</button>
                            <form action="revoke" method="post" style="display:inline;">
                                <input type="hidden" name="revokeAdminId" value="<%=u.getId()%>">
                                <input type="hidden" name="action" value="unrevoke">
                                <button type="submit" class="btn btn-success">Hoạt động</button>
                            </form>
                            <form action="revoke" method="post" style="display:inline;">
                                <input type="hidden" name="revokeAdminId" value="<%=u.getId()%>">
                                <input type="hidden" name="action" value="revoke">
                                <button type="submit" class="btn btn-success" style="background-color: red">Revoke</button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    %>


                    </tbody>
                </table>
            </section>
        </main>
    </div>
</div>
<%--<script>--%>
<%--  document.addEventListener("DOMContentLoaded", function () {--%>
<%--    const sidebarPlaceholder = document.getElementById("sidebar-placeholder");--%>

<%--    if (sidebarPlaceholder) {--%>
<%--      // Tải sidebar từ file sidebar.html--%>
<%--      fetch('sidebar.html')--%>
<%--              .then(response => response.text())--%>
<%--              .then(data => {--%>
<%--                // Chèn nội dung vào placeholder--%>
<%--                sidebarPlaceholder.innerHTML = data;--%>

<%--                // Lấy tất cả liên kết trong sidebar--%>
<%--                const links = sidebarPlaceholder.querySelectorAll(".sidebar ul li a");--%>

<%--                // Đặt active dựa trên URL hiện tại--%>
<%--                const currentPath = window.location.pathname.split("/").pop(); // Lấy tên file hiện tại--%>
<%--                links.forEach(link => {--%>
<%--                  const linkPath = link.getAttribute("href").split("/").pop(); // Lấy tên file trong href--%>
<%--                  if (linkPath === currentPath) {--%>
<%--                    link.classList.add("active");--%>
<%--                  }--%>
<%--                });--%>

<%--                // Lắng nghe sự kiện click để đổi màu khi bấm--%>
<%--                links.forEach(link => {--%>
<%--                  link.addEventListener("click", function () {--%>
<%--                    // Xóa lớp active khỏi tất cả các liên kết--%>
<%--                    links.forEach(l => l.classList.remove("active"));--%>

<%--                    // Thêm lớp active vào liên kết được bấm--%>
<%--                    this.classList.add("active");--%>
<%--                  });--%>
<%--                });--%>
<%--              })--%>
<%--              .catch(error => console.error('Lỗi khi tải sidebar:', error));--%>
<%--    }--%>
<%--  });--%>

<%--</script>--%>
</body>
</html>

