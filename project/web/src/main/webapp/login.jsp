<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng Nhập</title>
    <link rel="stylesheet" type="text/css" href="CSS_JSP/login.css">
</head>
<body>
<div id="login" class="login">
    <div class="container">
        <form id="loginForm" action="login" method="post">
            <h1 class="title">Đăng nhập</h1>
            <label for="email" class="label">Email</label>
            <input type="email" id="email" name="email" class="input" placeholder="Nhập email của bạn" required>
            <label for="password" class="label">Mật khẩu</label>
            <input type="password" id="password" name="password" class="input" placeholder="Nhập mật khẩu của bạn" required>
            <label for="captchaInput" class="label">Nhập Captcha:</label>
            <div class="captcha-container">
                <div class="noise"></div>
                <span id="captchaText" class="captcha"></span>
                <span class="refresh-btn" id="refreshCaptcha">🔄</span>
            </div>
            <input type="text" id="captchaInput" name="captchaInput" class="input" placeholder="Nhập mã captcha" required>

            <a href="forgetpassword.jsp" class="forgot-password" target="_blank">Quên mật khẩu?</a>
            <input type="submit" class="submit" value="Đăng nhập">
            <div class="signup">
                <label class="signup-label">Bạn chưa có tài khoản?</label>
                <a href="signup.jsp" class="signup-link">Đăng ký ngay</a>
            </div>
        </form>

        <!-- Nút Đăng nhập bằng Facebook -->
        <div class="social-login">
            <h2>Hoặc</h2>
            <a href="login?action=facebook" class="facebook-login-btn">Đăng nhập bằng Facebook</a>
        </div>
    </div>
</div>

<div id="notification" class="notification"></div>
<script src="${pageContext.request.contextPath}/Js/login.js"></script>
</body>
</html>