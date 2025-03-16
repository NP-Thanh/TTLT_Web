<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link rel="stylesheet" type="text/css" href="CSS_JSP/signup.css">
</head>
<body>
<div id="signup" class="signup">
    <div class="container">
        <form id="signupForm" action="signup" method="post">
            <h1 class="title">Đăng ký</h1>
            <%-- Sửa cách hiển thị thông báo --%>
            <% if (session.getAttribute("notification") != null) { %>
            <h6 style="color: red;"><%= session.getAttribute("notification") %></h6>
            <% session.removeAttribute("notification"); %>
            <% } %>
            <label for="email" class="label">Email</label>
            <input type="email" id="email" name="email" class="input" placeholder="Nhập email của bạn" required>
            <label for="password" class="label">Mật khẩu</label>
            <input type="password" id="password" name="password" class="input" placeholder="Nhập mật khẩu của bạn" required>
            <label for="password2" class="label">Nhập lại mật khẩu</label>
            <input type="password" id="password2" name="password2" class="input" placeholder="Vui lòng nhập lại mật khẩu" required>
            <input type="submit" class="submit" value="Tạo tài khoản">
            <div class="login">
                <label class="login-label">Đã có tài khoản?</label>
                <a href="login.jsp" class="login-link">Đăng nhập</a>
            </div>
        </form>
    </div>
</div>
<%--<div id="notification" class="notification">--%>
<%--    <c:if test="${not empty errorMessage}">--%>
<%--        <div class="error">${errorMessage}</div>--%>
<%--    </c:if>--%>
<%--</div>--%>
<script src="../JS/signup.js"></script>
</body>
</html>