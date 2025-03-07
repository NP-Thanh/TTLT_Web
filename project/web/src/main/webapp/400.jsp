<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 19/01/2025
  Time: 8:04 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Yêu cầu không hợp lệ</title>
    <style>
        html, body, p {
            width: 100%;
            margin: 0;
            padding: 10px;
            font-size: 14px;
            color: #404040;
            font-family: Arial, sans-serif;
            height: 100%;
        }
    </style>
</head>
<body>
<h1>400 - Yêu cầu không hợp lệ</h1>
<p>Thông báo lỗi: <%= request.getAttribute("error") %></p>
</body>
</html>

