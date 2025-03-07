<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt lại mật khẩu</title>
    <link rel="stylesheet" href="CSS_JSP/forgetpassword.css">
</head>
<body>
<div id="page" class="page">
    <div id="reset-password" class="reset-password">
        <div class="container">
            <form action="forgetPassword" method="post">
                <h1 class="title">Đặt lại mật khẩu</h1>
                <p class="subtitle">Quên mật khẩu? Nhập email của bạn để đặt lại.</p>
                <div class="form-group">
                    <label for="email" class="label">Email</label>
                    <input type="email" id="email" name="email" class="input" placeholder="Nhập email của bạn" required>
                </div>
                <input type="submit" class="submit" value="Đặt lại mật khẩu">
            </form>
            <div id="notification" class="notification">
                <%
                    String resultMessage = (String) request.getAttribute("resultMessage");
                    if (resultMessage != null) {
                %>
                <div class="alert"><%= resultMessage %></div>
                <% } %>
            </div>
        </div>
    </div>
</div>
</body>
</html>