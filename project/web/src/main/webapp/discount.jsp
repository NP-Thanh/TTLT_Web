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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
            <button class="btn-add" onclick="toggleAddForm()">+ Thêm mã giảm giá</button>
            <p id="errorMessage" style="color: red; display: none; margin-top: 10px;"></p>
            <form id="addDiscountForm" method="post" style="display: none;">
                <input type="hidden" name="action" value="add">
                <input type="text" name="id" placeholder="Mã" required>
                <input type="number" name="quantity" placeholder="Số lượng" required>
                <input type="text" name="value" placeholder="Nội dung" required>
                <input type="date" name="create_date" required>
                <input type="date" name="exp_date" required>
                <button type="button" onclick="addDiscount()">Lưu</button>
            </form>

            <button class="btn-edit" onclick="toggleEditForm()">Sửa thông tin</button>
            <form id="editDiscountForm" method="post" style="display: none;">
                <input type="hidden" name="action" value="update">
                <input type="text" name="id" placeholder="Nhập mã giảm để sửa" required>
                <input type="number" name="quantity" placeholder="Số lượng" required>
                <button type="button" onclick="updateDiscount()">Lưu</button>
            </form>

            <form id="deleteDiscountForm" method="post" style="display: none;">
                <input type="hidden" name="action" value="delete">
                <input type="text" name="id" placeholder="Nhập mã giảm để xóa" required>
                <button type="button" onclick="deleteDiscount()">Xóa</button>
            </form>

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
                <tbody id="discountTableBody">
                <jsp:include page="discount_table.jsp" />
                </tbody>
            </table>
        </section>
    </div>
</div>

<script>
    function toggleAddForm() {
        const form = document.getElementById('addDiscountForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
        $('#errorMessage').hide();
    }

    function toggleEditForm() {
        const form = document.getElementById('editDiscountForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

    function addDiscount() {
        const formData = $('#addDiscountForm').serialize();
        $.post('${pageContext.request.contextPath}/discounts', formData, function(response) {
            // Cập nhật bảng sau khi thêm
            $('#discountTableBody').html(response);
            $('#addDiscountForm')[0].reset();
            toggleAddForm();
            $('#errorMessage').hide();
        }).fail(function(e) {
            const errorText = 'Lỗi khi thêm mã giảm giá.';
            $('#errorMessage').text(errorText).show();
        });
    }

    function updateDiscount() {
        const formData = $('#editDiscountForm').serialize();
        $.post('${pageContext.request.contextPath}/discounts', formData, function(response) {
            // Cập nhật bảng sau khi sửa
            $('#discountTableBody').html(response);
            $('#editDiscountForm')[0].reset();
            toggleEditForm();
        }).fail(function() {
            alert('Lỗi khi sửa mã giảm giá.');
        });
    }

    function deleteDiscount() {
        const formData = $('#deleteDiscountForm').serialize();
        $.post('${pageContext.request.contextPath}/discounts', formData, function(response) {
            // Cập nhật bảng sau khi xóa
            $('#discountTableBody').html(response);
        }).fail(function() {
            alert('Lỗi khi xóa mã giảm giá.');
        });
    }
</script>
</body>
</html>