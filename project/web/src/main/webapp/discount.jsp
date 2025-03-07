<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Discount" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý mã giảm giá</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="CSS_JSP/discount.css">
</head>
<body>
<div class="admin-container">
    <div class="slidebar">
        <jsp:include page="slibar.jsp"/>
    </div>

    <div class="content">
        <header>
            <h2>Quản lý mã giảm giá</h2>
        </header>
        <section>
            <!-- Nút Thêm mã -->
            <button class="btn-add" onclick="toggleAddForm()">+ Thêm mã giảm giá</button>
            <form id="addDiscountForm" method="post" action="${pageContext.request.contextPath}/discounts" style="display: none;">
                <input type="hidden" name="action" value="add">
                <input type="text" name="id" placeholder="Mã" required>
                <input type="number" name="quantity" placeholder="Số lượng" required>
                <input type="text" name="value" placeholder="Nội dung" required>
                <input type="date" name="create_date" required>
                <input type="date" name="exp_date" required>
                <button type="submit">Lưu</button>
            </form>

            <!-- Nút Sửa thông tin -->
            <button class="btn-edit" onclick="toggleEditForm()">Sửa thông tin</button>
            <form id="editDiscountForm" method="post" action="${pageContext.request.contextPath}/discounts" style="display: none;">
                <input type="hidden" name="action" value="update">
                <input type="text" name="id" placeholder="Nhập mã giảm để sửa" required>
                <input type="number" name="quantity" placeholder="Số lượng" required>
                <button type="submit">Lưu</button>
            </form>

            <!-- Nút Xóa mã -->
            <form id="deleteDiscountForm" method="post" action="${pageContext.request.contextPath}/discounts" style="display: none;">
                <input type="hidden" name="action" value="delete">
                <input type="text" name="id" placeholder="Nhập mã giảm để xóa" required>
                <button type="submit">Xóa</button>
            </form>

            <!-- Bảng thông tin -->
            <table>
                <thead>
                <tr>
                    <th>Mã</th>
                    <th>Số lượng còn lại</th>
                    <th>Nội dung</th>
                    <th>Ngày tạo</th>
                    <th>Ngày hạn</th>
                    <th>Tình trạng</th>
                </tr>
                </thead>
                <tbody>
                <%
                    List<Discount> discounts = (List<Discount>) request.getAttribute("discounts");
                    if (discounts != null) {
                        for (Discount discount : discounts) {
                %>
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
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="6">Không có mã giảm giá nào.</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </section>
    </div>
</div>

<script>
    function toggleAddForm() {
        const form = document.getElementById('addDiscountForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

    function toggleEditForm() {
        const form = document.getElementById('editDiscountForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

    function toggleDeleteForm() {
        const form = document.getElementById('deleteDiscountForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }
</script>
</body>
</html>