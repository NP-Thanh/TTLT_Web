<%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 1/13/2025
  Time: 12:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"
            integrity="sha512-6sSYJqDreZRZGkJ3b+YfdhB3MzmuP9R7X1QZ6g5aIXhRvR1Y/N/P47jmnkENm7YL3oqsmI6AK+V6AD99uWDnIw=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/productmanagement.css">
    <script src="https://cdn.ckeditor.com/4.21.0/standard/ckeditor.js"></script>
</head>
<body>
<div class="admin-container">
    <div class="slidebar">
        <jsp:include page="slibar.jsp"/>
    </div>

    <div class="content">
        <header>
            <h2>Quản lý sản phẩm</h2>
        </header>
        <section>
            <!-- Nút Thêm sản phẩm -->
            <button class="btn-add" onclick="toggleAddForm()">+ Thêm sản phẩm</button>
            <form action="addProduct" method="post" id="addProductForm" class="hidden" >

                <input name="name" type="text" id="addProductName" placeholder="Tên sản phẩm" required>
<%--                <input name="type" type="text" id="addProductType" placeholder="Loại" required>--%>
                <select name="type" id="addProductType" required>
                    <option value="Giải trí">Giải trí</option>
                    <option value="Học tập">Học tập</option>
                    <option value="Làm việc">Làm việc</option>
                    <option value="Tiện ích">Tiện ích</option>
                    <option value="Bảo mật">Bảo mật</option>
                    <option value="Lưu trữ">Lưu trữ</option>
                </select>

                <input name="price" type="number" id="addProductPrice" placeholder="Giá (VNĐ)" required>
                <input name="duration" type="text" id="addProductDuration" placeholder="Thời hạn" required>
                <input name="img" type="text" id="addProductImg" placeholder="Hình ảnh" required>
<%--                <input name="des" type="text" id="addProductDes" placeholder="Mô tả" required>--%>
<%--                <input name="intro" type="text" id="addProductIntro" placeholder="Giới thiệu" required>--%>
<%--                <input name="manu" type="text" id="addProductManu" placeholder="Sản xuất" required>--%>
<%--                <input name="support" type="text" id="addProductSupport" placeholder="Hổ trợ" required>--%>
<%--                <input name="banner" type="text" id="addProductBanner" placeholder="Banner" required>--%>
                <button type="submit">Lưu</button>
            </form>


            <!-- Nút Sửa thông tin -->
            <button class="btn-edit" onclick="toggleEditForm()">Sửa thông tin</button>
            <form id="editProductForm" class="hidden">
                <input type="text" id="editProductId" placeholder="Nhập mã sản phẩm để sửa" required>
                <button type="button" onclick="loadProductInfo()">Tìm kiếm</button>
            </form>
            <div id="editProductDetails" class="hidden">
                <form action="editProduct" method="post">
                    <table style="margin-bottom: 10px">
                        <tr>
                            <td>Mã sản phẩm:</td>
                            <td><input type="text" id="editIDProduct" name="pid" required readonly></td>
                        </tr>
                        <tr>
                            <td>Tên sản phẩm:</td>
                            <td><input type="text" id="editProductName" name="pName" required></td>
                        </tr>
                        <tr>
                            <td>Loại:</td>
                            <td><input type="text" id="editProductType" name="pType" required></td>
                        </tr>

                        <tr>
                            <td>Giá:</td>
                            <td><input type="number" id="editProductPrice" name="pPrice" required></td>
                        </tr>
                        <tr>
                            <td>Thời hạn:</td>
                            <td><input type="text" id="editProductDuration"  name="pDuration" required></td>
                        </tr>
                        <tr>
                            <td>Hình ảnh (URL):</td>
                            <td><input type="text" id="editProductImage" name="pImg" required></td>
                        </tr>
                        <tr>
                            <td>Mô tả:</td>
                            <td><textarea id="editProDes" name="pDescription" required></textarea></td>
                        </tr>
                        <tr>
                            <td>Giới thiệu:</td>
                            <td><input type="text" id="editProIntro" name="pIntroduction" required></td>
                        </tr>
                        <tr>
                            <td>Sản xuất:</td>
                            <td><input type="text" id="editProManu" name="pManufacturer" required></td>
                        </tr>

                        <tr>
                            <td>Hổ trợ:</td>
                            <td><input type="text" id="editProSupport" name="pSupport" required></td>
                        </tr>
<%--                        9 cái--%>
                    </table>
                    <button type="submit">Lưu</button>
                </form>
            </div>

<%--            thêm key--%>
            <button class="btn-edit" onclick="toggleAddKeyForm()">Thêm key cho sản phẩm</button>
            <form id="editKeyForm" class="hidden">
                <input type="text" id="addProductId" placeholder="Nhập mã sản phẩm để thêm key" required>
                <button type="button" onclick="loadProductKeyInfo()">Tìm kiếm</button>
            </form>


            <div id="addKey" class="hidden">
                <form action="addKey" method="post">
                    <table style="margin-bottom: 10px">
                        <tr>
                            <td>Mã sản phẩm:</td>
                            <td><input type="text" id="idProductKey" name="pid" required readonly></td>
                        </tr>
                        <tr>
                            <td>Mã key:</td>
                            <td><input type="text" id="key" name="key" required></td>
                        </tr>
                    </table>
                    <button type="submit">Lưu</button>
                </form>
            </div>



            <!-- Bảng sản phẩm -->
            <table>
                <thead>
                <tr>
                    <th>Mã sản phẩm</th>
                    <th>Loại</th>
                    <th>Tên sản phẩm</th>
                    <th>Giá</th>
                    <th>Thời hạn</th>
                    <th>Số lượng</th>
                    <th>Hình ảnh</th>
                    <th>Tình trạng</th>
                    <th>Hành động</th>

                </tr>
                </thead>
                <tbody  id="productTable">
                <!-- Các sản phẩm cố định được thêm vào ở đây -->
                <c:forEach var="product" items="${products}">
                    <tr data-description="${product.description}"
                        data-introduction="${product.introduction}"
                        data-manufacturer="${product.manufacturer}"
                        data-support="${product.support}">
                        <td>${product.id}</td>
                        <td>${product.type_name}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.duration}</td>
                        <td>${product.quantity}</td>
                        <td><img src="${product.image}" style="width: 50px; height: 50px;"></td>
                        <td class="status in-stock">${product.status}</td>
                        <td class="icon-trash">
                            <a href="deleteProduct?pid=${product.id}" class="delete" style="color: black"><i class="fa-solid fa-trash"></i></a>
                        </td>

                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </div>
</div>
<script src=""></script>
<script src="${pageContext.request.contextPath}/Js/product-management.js"></script>
<script>
    CKEDITOR.replace('editProDes');
</script>



</body>

</html>
