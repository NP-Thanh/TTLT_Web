<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  HttpSession sessionUser = request.getSession();
  String email = (String) sessionUser.getAttribute("email");
  if (email == null) {
    response.sendRedirect("forgetpassword.jsp");
    return;
  }
%>

<html>
<head>
  <title>Xác thực OTP</title>
  <link rel="stylesheet" type="text/css" href="CSS_JSP/changePassword.css">
</head>
<body>
<div id="changePassword" class="changePassword">
  <div class="container">
    <form  action="resetPassword" method="post">
      <h1 class="title">Đổi Mật Khẩu</h1>
      <% if (session.getAttribute("error") != null) { %>
      <div id="error-message" style="color: red; font-weight: bold; padding-bottom: 5px">
        <%= session.getAttribute("error") %>
      </div>
      <% session.removeAttribute("error"); %> <%-- Xóa lỗi sau khi hiển thị --%>
      <% } %>
      <label for="new_password" class="label">Mật khẩu mới</label>
      <input type="password" id="new_password" name="new_password" class="input" placeholder="Nhập mật khẩu của bạn" required>

      <label for="confirm_password" class="label">Nhập lại mật khẩu</label>
      <input type="password" id="confirm_password" name="confirm_password" class="input" placeholder="Nhập lại mật khẩu của bạn" required>
      <input type="submit" class="submit" value="Đổi mật khẩu">
    </form>
  </div>
</div>
</body>
</html>
