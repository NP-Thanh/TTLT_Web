<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"
            integrity="sha512-6sSYJqDreZRZGkJ3b+YfdhB3MzmuP9R7X1QZ6g5aIXhRvR1Y/N/P47jmnkENm7YL3oqsmI6AK+V6AD99uWDnIw=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/productmanagement.css">
    <script src="//cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script>
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
            <%--Tìm kiếm--%>
            <button class="btn-find" onclick="toggleFind()">Tìm kiếm</button>
            <form class="hidden" id="findProductForm" action="${pageContext.request.contextPath}/ProductManagement" method="post">
                <input type="hidden" name="action" value="search">
                <input type="text" name="productID" placeholder="Mã sản phẩm">
                <input type="text" name="productName" placeholder="Tên sản phẩm">
                <select name="status">
                    <option value="">Tất cả</option>
                    <option value="Còn hàng">Còn hàng</option>
                    <option value="Hết hàng">Hết hàng</option>
                </select>
                <button type="submit">Tìm kiếm</button>
            </form>

            <!-- Nút Thêm sản phẩm -->
            <button class="btn-add" onclick="toggleAddForm()">+ Thêm sản phẩm</button>
            <form action="${pageContext.request.contextPath}/ProductManagement" method="post" id="addProductForm" class="hidden">
                <input type="hidden" name="action" value="add">
                <input name="name" type="text" id="addProductName" placeholder="Tên sản phẩm" required>
                <%--                <input name="type" type="text" id="addProductType" placeholder="Loại" required>--%>
                <select name="type" id="addProductType" required>
                    <c:choose>
                        <c:when test="${not empty allowedType}">
                            <option value="${allowedType}">${allowedType}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="Giải trí">Giải trí</option>
                            <option value="Học tập">Học tập</option>
                            <option value="Làm việc">Làm việc</option>
                            <option value="Tiện ích">Tiện ích</option>
                            <option value="Bảo mật">Bảo mật</option>
                            <option value="Lưu trữ">Lưu trữ</option>
                        </c:otherwise>
                    </c:choose>
                </select>


                <input name="price" type="number" id="addProductPrice" placeholder="Giá (VNĐ)" required>
                <input name="duration" type="text" id="addProductDuration" placeholder="Thời hạn" required>
                <input name="img" type="text" id="addProductImg" placeholder="Hình ảnh" required>
                <button type="submit">Lưu</button>
            </form>

            <%--form nhập key--%>
            <div id="addKey" class="hidden">
                <form action="${pageContext.request.contextPath}/ProductManagement" method="post">
                    <input type="hidden" name="action" value="addKey">
                    <table style="margin-bottom: 10px">
                        <tr>
                            <td>Mã sản phẩm:</td>
                            <td><input type="text" id="idProductKey" name="pid" required readonly></td>
                        </tr>
                        <tr>
                            <td>Mã key:</td>
                            <td>
                                <textarea class="keylist" id="keys" name="keys" required
                                          placeholder="Nhập mỗi key trên một dòng"></textarea>
                            </td>
                        </tr>
                    </table>
                    <div class="d-flex">
                        <button style="width: 100px !important;" type="submit">Lưu</button>
                        <button style="width: 70px !important; background: #f12323" type="button" onclick="cancelledKey()">
                            Hủy
                        </button>
                    </div>
                </form>
            </div>

            <%--                Form chỉnh sửa chi tiết sản phẩm--%>
            <div id="editProductDetails" class="hidden">
                <form action="${pageContext.request.contextPath}/ProductManagement" method="post">
                    <input type="hidden" name="action" value="update">
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
                            <td><input type="text" id="editProductDuration" name="pDuration" required></td>
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
                    <div class="d-flex">
                        <button style="width: 100px !important;" type="submit">Lưu</button>
                        <button style="width: 70px !important; background: #f12323" type="button" onclick="cancelled()">
                            Hủy
                        </button>
                    </div>
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
                <tbody id="productTable">
                <!-- Các sản phẩm cố định được thêm vào ở đây -->
                <c:forEach var="product" items="${products}">
                    <tr data-id="${product.id}"
                        data-type="${product.type_name}"
                        data-name="${product.name}"
                        data-price="${product.price}"
                        data-duration="${product.duration}"
                        data-quantity="${product.quantity}"
                        data-image="${product.image}"
                        data-status="${product.status}"
                        data-description="${product.description}"
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
                        <td class="status ${product.status eq 'còn hàng' ? 'in-stock' : 'out-of-stock'}">
                                ${product.status}
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <button class="d-flex align-items-center"
                                        style="width: 60px; height: 30px; padding-left: 8px; font-weight: bold"
                                        onclick="editProduct(this)">Update
                                </button>
                                <button class="d-flex align-items-center"
                                        style="width: 40px; height: 30px; padding-left: 8px;
                                         background: #ffe250; font-weight: bold" onclick="loadProductKeyInfo(this)"
                                        data-id="${product.id}">Key
                                </button>
                                <form action="${pageContext.request.contextPath}/ProductManagement" method="post" style="margin: 0">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="pid" value="${product.id}">
                                    <button type="submit"
                                            style="color: #fbfbfb;width: 30px; height: 30px; background: #ff3d3d; border-radius: 4px; padding-left: 8px">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </form>
                            </div>
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
