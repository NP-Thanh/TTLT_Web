<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Discount" %>

<%
    List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
%>
<% if (discounts != null && !discounts.isEmpty()) { %>
<% for (Discount discount : discounts) { %>
<tr>
    <td><%= discount.getId() %></td>
    <td><%= discount.getQuantity() %></td>
    <td><%= discount.getValue() %></td>
    <td><%= discount.getCreateDate() %></td>
    <td><%= discount.getExpDate() %></td>
    <td class="<%= discount.getQuantity() > 0 ? "green" : "red" %> font-600">
        <%= discount.getQuantity() > 0 ? "Còn mã" : "Hết" %>
    </td>
</tr>
<% } %>
<% } else { %>
<tr>
    <td colspan="6">Không có mã giảm giá nào.</td>
</tr>
<% } %>
