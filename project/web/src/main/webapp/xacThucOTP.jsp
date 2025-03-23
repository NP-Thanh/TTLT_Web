<%@ page import="vn.edu.hcmuaf.fit.web.servieces.XacThucOTPService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession sessionUser = request.getSession();
    String email = (String) sessionUser.getAttribute("email");
    String action = (String) sessionUser.getAttribute("action");

    if (email == null || action == null || action.trim().isEmpty()) {
        if ("resetPassword".equals(action)) {
            response.sendRedirect("forgetpassword.jsp"); // Quên mật khẩu -> về trang nhập email
        } else {
            response.sendRedirect("signup.jsp"); // Đăng ký -> về trang đăng ký
        }
        return;
    }

    // Gọi Service để kiểm tra tài khoản có bị khóa không
    XacThucOTPService xacThucOTPService = new XacThucOTPService();
    boolean isLocked = xacThucOTPService.isLocked(email);
    session.setAttribute("isLocked", isLocked);
%>

<html>
<head>
    <title>Xác thực OTP</title>
    <link rel="stylesheet" type="text/css" href="CSS_JSP/xacThucOTP.css">
</head>
<body>
<div id="xacThucOTP" class="xacThucOTP">
    <div class="container">
        <form id="otpForm" action="xacThucOTP" method="post">
            <h1 class="title">Xác Thực OTP</h1>
            <% if (session.getAttribute("notification") != null) { %>
            <h6 style="color: red;"><%= session.getAttribute("notification") %></h6>
            <% session.removeAttribute("notification"); %>
            <% } %>

            <label for="otp" class="label">Nhập OTP</label>
            <input type="text" id="otp" name="otp" class="input" placeholder="Nhập mã otp của bạn" required maxlength="6" pattern="[0-9]{6}" <%= isLocked ? "disabled" : "" %>>
            <input type="submit" class="submit" value="Xác nhận" <%= isLocked ? "disabled" : "" %>>
        </form>
        <div class="otpResend">
            <label class="otpResend-label">Bạn chưa nhận được OTP?</label>
            <% if (!isLocked) { %>
            <a href="" class="otpResend-link" onclick="resendOtp()" style="color: #007bff;">Gửi lại OTP</a>
            <% } else { %>
            <span style="color: red;">Bạn đã nhập sai quá 5 lần, thử lại sau 5 phút.</span>
            <% } %>
        </div>
    </div>
</div>
<script>
    function resendOtp() {
        fetch('resendOtp', { method: 'POST' })
            .then(response => response.text())
            .then(data => {
                alert(data); // Hiển thị thông báo từ server
                location.reload(); // Tải lại trang để cập nhật OTP
            })
            .catch(error => console.error('Lỗi:', error));
    }
</script>
</body>
</html>
