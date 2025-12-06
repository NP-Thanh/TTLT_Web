<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%
    User user= (User) request.getAttribute("user");
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tài Khoản</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        body, html {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            color: #404040;
            font-size: 14px;
        }

        .containertk {
            display: flex;
            width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
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

        #main-content {
            flex: 1;
            padding-left: 20px;
        }

        .card {
            background-color: #fff;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            margin-top: 0;
            font-size: 20px;
            color: #333;
        }

        .form-group {
            margin-bottom: 15px;

        }

        .form-label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            font-size: 14px;
        }

        .form-input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }

        .form-button {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .form-button:hover {
            background-color: #0056b3;
        }

        .stats-container {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
            margin-bottom: 20px;
        }

        .stat-item {
            flex: 1;
            text-align: center;
            font-size: 16px;
            color: black;
            font-weight: bold;
        }
        .notification {
            position: fixed;
            bottom: 40px;
            right: 40px;
            background-color: #b62424; /* Green background */
            color: white;
            padding: 20px 20px;
            border-radius: 5px;
            display: none; /* Hidden by default */
            z-index: 1000; /* Ensure it appears above other content */
        }
    </style>
</head>
<body>

<div id="page" class="page">
    <div class="iframe-header">
        <jsp:include page="header.jsp"/>
    </div>
    <div class="containertk">
        <div class="sidebar">
            <ul>
                <li><a href="account" style="color: #007bff;"><i class="fas fa-user"></i>Tài khoản</a></li>
                <li><a href="view"><i class="fas fa-clock"></i>Sản phẩm vừa xem</a></li>
                <li><a href="lichsugiaodich"><i class="fas fa-history"></i>Lịch sử giao dịch</a></li>
                <li><a href="logout" style="color: #ff1e1e; font-weight: 600;"><i></i>Đăng xuất</a></li>
            </ul>
        </div>
        <div id="main-content" class="main-content-section">
            <div id="stats-card" class="stats-card card">
                <div class="stats-container">
                    <div class="stat-item">Khách hàng</div>
                    <div class="stat-item">Ngày tham gia<br>28/10/2024</div>
                </div>
            </div>
            <form action="updateProfile" method="post">
                <div id="personal-info-card" class="personal-info-card card">
                    <h2>Thông tin cá nhân</h2>
                    <% if (session.getAttribute("error") != null) { %>
                    <div id="message" style="color: red; font-weight: bold; padding-bottom: 5px">
                        <%= session.getAttribute("error") %>
                    </div>
                    <% session.removeAttribute("error"); %> <%-- Xóa lỗi sau khi hiển thị --%>
                    <% } %>
                    <div class="form-group">
                        <label for="username" class="form-label">Tên người dùng *</label>
                        <input value="<%=user.getName()%>" type="text" id="username" name="full_name" class="form-input personal-input" placeholder="Nhập tên người dùng" required>
                    </div>
                    <div class="form-group">
                        <label for="phone" class="form-label">Số điện thoại</label>
                        <input value="<%=user.getPhone()%>" type="text" id="phone" name="phone_number" class="form-input personal-input" placeholder="Nhập số điện thoại">
                    </div>
                    <div class="form-group">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" id="email" name="email" class="form-input personal-input" value="<%= request.getSession().getAttribute("email") != null ? request.getSession().getAttribute("email") : "" %>" readonly>
                    </div>
                    <div class="form-group">
                        <div class="form-label"></div>
                        <button type="submit" class="form-button personal-button" id="saveInfoButton">Lưu thông tin</button>
                    </div>
                </div>
            </form>
            <form action="resetPassword" method="post" id="resetPasswordForm">
                <div id="password-change-card" class="password-change-card card">
                    <h2>Đổi mật khẩu</h2>
                    <div class="form-group">
                        <label for="email2" class="form-label">Email</label>
                        <input type="email" id="email2" name="email" class="form-input personal-input" value="<%= request.getSession().getAttribute("email") != null ? request.getSession().getAttribute("email") : "" %>" readonly>
                    </div>
                    <div class="form-group">
                        <label for="old-password" class="form-label">Mật khẩu cũ</label>
                        <input type="password" id="old-password" name="old_password" class="form-input password-input" placeholder="Nhập mật khẩu cũ" required>
                    </div>
                    <div class="form-group">
                        <label for="new-password" class="form-label">Mật khẩu mới</label>
                        <input type="password" id="new-password" name="new_password" class="form-input password-input" placeholder="Nhập mật khẩu mới" required>
                    </div>
                    <div class="form-group">
                        <label for="confirm-password" class="form-label">Nhập lại mật khẩu mới</label>
                        <input type="password" id="confirm-password" name="confirm_password" class="form-input password-input" placeholder="Nhập lại mật khẩu mới" required>
                    </div>
                    <div class="form-group">
                        <div class="form-label"></div>
                        <button type="submit" class="form-button password-button" id="changePasswordButton">Xác nhận thay đổi</button>
                    </div>
<%--                    <div id="message" style="color: red;"></div> <!-- Khối hiển thị thông báo -->--%>
                </div>
            </form>
        </div>
    </div>
    <div id="footer" style="margin-top: 20px;">
        <jsp:include page="footer.jsp"/>
    </div>
</div>
</body>
</html>