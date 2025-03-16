<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    HttpSession sessionUser = request.getSession();
    User pendingUser = (User) sessionUser.getAttribute("pendingUser");
    if (pendingUser == null){
        System.out.println("Session không có email! Chuyển về signup.jsp");
        response.sendRedirect("signup.jsp");
        return;
    }
    String email = pendingUser.getEmail();
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
            <!-- Ẩn email trong form -->
<%--            <input type="hidden" id="email" name="email" value="<%= email %>">--%>

            <label for="otp" class="label">Nhập OTP</label>
            <input type="text" id="otp" name="otp" class="input" placeholder="Nhập mã otp của bạn" required maxlength="6" pattern="[0-9]{6}">
            <input type="submit" class="submit" value="Xác nhận">
        </form>
    </div>
</div>
</body>
</html>
