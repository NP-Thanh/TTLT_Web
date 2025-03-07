<%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 12/23/2024
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/js/all.min.js"
            integrity="sha512-6sSYJqDreZRZGkJ3b+YfdhB3MzmuP9R7X1QZ6g5aIXhRvR1Y/N/P47jmnkENm7YL3oqsmI6AK+V6AD99uWDnIw=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/product.css">
    <title>Document</title>y
</head>

<body>
<div id="header">
    <iframe class="iframe-header" src="${pageContext.request.contextPath}/Index/header.jsp" frameborder="0" scrolling="no"></iframe>
</div>
<div id="content">
    <div id="container" class="row">
        <div class="col-md-2">
            <div id="list-categoty">
                <p style="font-weight: bold; font-size: 16px;">Chọn Danh Mục</p>
                <div class="mt-8">
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <a href="products" class="ml-8 text-decoration-none t-black f-weight-600 category-filter" data-category = "Tất Cả"

                            >Tất Cả</a>

                        </div>
                    </div>
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600">Giải Trí</a> -->
                            <a href="#" onclick="loadProduct('Giải trí')" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                               data-category="Giải trí"
                            >Giải Trí</a>
                        </div>
                    </div>
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Học Tập</a> -->
                            <a href="#" onclick="loadProduct('Học tập')" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                               data-category="Học tập"
                            >Học Tập</a>
                        </div>
                    </div>
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Làm Việc</a> -->
                            <a href="#" onclick="loadProduct('Làm việc')" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                               data-category="Làm việc"
                            >Làm Việc</a>
                        </div>
                    </div>
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Tiện Ích</a> -->
                            <a href="#" onclick="loadProduct('Tiện ích')" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                               data-category="Tiện ích"
                            >Tiện Ích</a>
                        </div>
                    </div>
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Bảo Mật</a> -->
                            <a href="#" onclick="loadProduct('Bảo mật')" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                               data-category="Bảo mật"
                            >Bảo Mật</a>
                        </div>
                    </div>
                    <div class="mt-8">
                        <div class="mt-8 d-flex align-center cursor-pointer">
                            <i class="fa-solid fa-folder-closed icon-folder"></i>
                            <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Lưu Trữ</a> -->
                            <a href="#" onclick="loadProduct('Lưu trữ')" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                               data-category="Lưu trữ"
                            >Lưu Trữ</a>
                        </div>
                    </div>
                </div>
                <hr style="border-color: rgba(0, 0, 0, .12);" class="mt-16">
                <div class="mt-16">
                    <div class="">
                        <div class="d-flex align-center justify-space-between">
                            <p style="font-weight: bold; font-size: 16px;">Giá</p>

                        </div>
                        <form action="products" method="GET">
                            <div class="d-flex t-between align-center mt-8" style="justify-content: space-around;">
                                <div class="d-flex price">
                                    <input type="number" name="startPrice" id="start-number" placeholder="0" min="0">
                                    <div class="d-flex align-center">đ</div>
                                </div>
                                <div class="">Đến</div>
                                <div class="d-flex price">
                                    <input type="number" name="endPrice" id="end-number" placeholder="0" min="0">
                                    <div class="d-flex align-center">đ</div>
                                </div>
                            </div>
                            <button type="submit" class="btn-confirm">Xác Nhận</button>
                        </form>
                    </div>
                </div>
                <hr style="border-color: rgba(0, 0, 0, .12);" class="mt-16">
                <div class="mt-16">
                    <div class="">
                        <div class="d-flex align-center justify-space-between cursor-pointer title-sort" onclick="toggleSort()">
                            <p style="font-weight: bold; font-size: 16px;">Sắp Xếp Theo</p>
                            <i id="icon-down" class="fa-solid fa-caret-down "></i>
                            <i id="icon-up" class="fa-solid fa-caret-up " style="display: none;"></i>
                        </div>
                        <div id="sort-options" class="hide-list-category mt-16" style="display: none;">
                            <div class="mt-8">
                                <div class="mt-8 d-flex align-center cursor-pointer">
                                    <div class="wrap-btn">
                                        <div class="radio-btn"></div>
                                    </div>
                                    <p class="ml-8" style="color: gray;">Bán Chạy Nhất</p>
                                </div>
                            </div>
                            <div class="mt-8">
                                <div class="mt-8 d-flex align-center cursor-pointer sort-option" data-sort="newest">
                                    <div class="wrap-btn">
                                        <div class="radio-btn"></div>
                                    </div>
                                    <p class="ml-8" style="color: gray;">Mới Cập Nhật</p>
                                </div>
                            </div>
                            <div class="mt-8">
                                <div class="mt-8 d-flex align-center cursor-pointer sort-option" data-sort="price-asc">
                                    <div class="wrap-btn">
                                        <div class="radio-btn"></div>
                                    </div>
                                    <p class="ml-8" style="color: gray;">Giá Thấp Đến Cao</p>
                                </div>
                            </div>
                            <div class="mt-8">
                                <div class="mt-8 d-flex align-center cursor-pointer sort-option" data-sort="price-desc">
                                    <div class="wrap-btn">
                                        <div class="radio-btn"></div>
                                    </div>
                                    <p class="ml-8" style="color: gray;">Giá Cao Đến Thấp</p>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div id="list-product" class="list-product" style="margin-left: 20px;">
                <c:choose>
                    <c:when test="${not empty products}">
                        <div id="contentProduct" class="row justify-space-between">
                            <c:forEach var="product" items="${products}">
                                <a href="../HTML/chi_tiet_sp.html" class="product" data-category="${product.type_id}"
                                   style="text-decoration: none; color: inherit;">
                                    <div class="img">
                                        <img src="${product.img}" alt="${product.name}" class="img-p" style="width: 100%;">
                                    </div>
                                    <div class="infor-p" style="padding: 10px 16px;">
                                        <span class="title-p t-none t-black d-block">${product.name}</span>
                                        <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">
                                                ${product.duration}
                                        </span>
                                        <div class="price-p d-flex t-between">
                                            <span class="t-none t-black" style="font-weight: 550;">${product.price}₫</span>
                                        </div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p style="text-align: center; font-size: 18px; color: gray;">Không có sản phẩm nào được tìm thấy.</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- Nút phân trang -->
                    <div class="pagination">
                        <button id="page1" onclick="changePage(1)">1</button>
                        <button id="page2" onclick="changePage(2)">2</button>
                        <button id="page3" onclick="changePage(3)">3</button>
                        <!-- Thêm các nút trang nếu cần -->
                    </div>
                </div>
            </div>
        </div>
<div id="footer" style="margin-top: 20px;">
<%--    <iframe class="iframe-footer" src="./footer.html" frameborder="0" scrolling="no"></iframe>--%>
</div>
<script src="${pageContext.request.contextPath}/JS/product.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script src="${pageContext.request.contextPath}/JS/product.js"></script>
<script>
    function loadProduct(category) {
        history.pushState(null, null, `?category=${category}`);
        $.ajax({
            url: `${pageContext.request.contextPath}/load`,
            type: 'GET', // Viết hoa "GET" để tuân thủ tiêu chuẩn
            data: { category: category },
            success: function (data) {
                const row = document.getElementById('contentProduct');
                if (row) {
                    row.innerHTML = data;
                } else {
                    console.error("Element with id 'contentProduct' not found.");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error("Request failed: " + textStatus + ", " + errorThrown);
                alert("An error occurred while loading the product.");
            }
        });
    }

</script>
</body>

</html>