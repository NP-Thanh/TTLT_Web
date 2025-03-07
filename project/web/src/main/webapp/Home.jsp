<%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 1/8/2025
  Time: 2:49 PM
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Home.css">

    <title>Home</title>
</head>

<body>
<div id="iframe-header">
    <jsp:include page="header.jsp"/>
</div>
<div id="content-home" >
    <div id="container" style="max-width: 100%">
        <div id="section0">
            <div class="slider">
                <div class="slide-show">
                    <div class="list-imgs">
                        <img src="${pageContext.request.contextPath}/img/Cover.jpg" alt="" class="img-slide">
                        <img src="${pageContext.request.contextPath}/img/slider2.webp" alt="" class="img-slide">
                        <img src="${pageContext.request.contextPath}/img/slider3.webp" alt="" class="img-slide">
                    </div>
                    <div class="btns-slide">
                        <div class="btn-left btn-slide">
                            <i class="fa-solid fa-caret-left"></i>
                        </div>
                        <div class="btn-right btn-slide">
                            <i class="fa-solid fa-caret-right"></i>
                        </div>
                    </div>
                    <div class="dot-imgs">
                        <div class="dot-item item-0 active">

                        </div>
                        <div class="dot-item item-1">

                        </div>
                        <div class="dot-item item-2">

                        </div>
                    </div>
                </div>
            </div>
            <div class="banner">
                <div class="list-imgs-banner">
                    <img src="${pageContext.request.contextPath}/img/Youtube.webp" alt="" class="img-banner">
                    <img src="${pageContext.request.contextPath}/img/Google%20Drive.webp" alt="" class="img-banner" style="padding: 0 10px;">
                    <img src="${pageContext.request.contextPath}/img/Banner%20Canva.webp" alt="" class="img-banner" style="padding: 0 10px;">
                    <img src="${pageContext.request.contextPath}/img/Banner%20Office.webp" alt="" class="img-banner">
                </div>
            </div>
        </div>
        <div id="section1">
            <div class="header-section1" style="height: 40px;">
                <a href="javascript:void(0);" class="title text-decoration-none">
                    <i class="fa-solid fa-star" style="color: #1b5bd7;padding-right: 10px;"></i>
                    Sản phẩm nổi bậc
                </a>
                <a href="products" class="more-infor text-decoration-none">Xem toàn bộ</a>
            </div>

            <div class="product-grid d-flex row t-between">
                <c:forEach var="product" items="${mostSaleProducts}">
                    <a href="ProductDetail?id=${product.id}" class="product" style="text-decoration: none; color: inherit;" data-category="${product.typeId}">
                        <div class="img">
                            <img src="${product.image}" alt="${product.name}" class="img-p" style="width: 100%;">
                        </div>
                        <div class="infor-p" style="padding: 10px 16px;">
                            <span class="title-p t-none t-black d-block">${product.name}</span>
                            <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">${product.duration}</span>
                            <div class="price-p d-flex t-between">
                                <span class="t-none t-black" style="font-weight: 550;">
                                    <f:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/>đ
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
        <!--  -->
        <div id="section2">
            <div class="header-section2">
                <div class="header-section1" style="height: 40px;">
                    <a href="javascript:void(0);" class="title text-decoration-none">

                        <i class="fa-solid fa-magnifying-glass-dollar" style="color: #1b5bd7; padding-right: 10px;"></i>
                        Từ khoá nhiều lượt tìm kiếm
                        <span>Xem sản phẩm đang trending</span>
                    </a>
                    <a href="products" class="more-infor text-decoration-none">Xem toàn bộ</a>
                </div>
                <div class="search row">
                    <a href="products?search=Bản quyền Windows" class="search-1 t-none t-black">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        Bản quyền Windows
                    </a>
                    <a href="products?search=Youtube Premium" class="search-1 t-none t-black">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        Youtube Premium
                    </a>
                    <a href="products?search=Office" class="search-1 t-none t-black">
                        <i class="fa-solid fa-magnifying-glass"></i>
                        Microsoft Office
                    </a>
                </div>
            </div>
        </div>
        <div id="section-bestseller" style="border-radius: 8px;">
            <div class="container">
                <div class="header-bestseller d-flex t-between">
                    <div class="d-flex t-center">
                        <i class="fa-solid fa-arrow-trend-up" style="color: #1b5bd7; margin-right: 20px;font-size: 30px;
              border-bottom: solid 4px #1b5bd7;"></i>
                        <a href="javascript:void(0);" class="title text-decoration-none t-white" style="font-size: 20px;">
                            Sản phẩm được xem nhiều nhất
                            <span class="d-block" style="font-size: 14px;font-weight: 100;">
                  Chúng tôi nghỉ rằng bạn sẽ thích những sản phẩm này</span>
                        </a>
                    </div>
                    <a href="products" class="more-infor text-decoration-none t-white t-center d-flex">Xem toàn bộ</a>
                </div>
                <div class="grid-product row t-between">
                    <c:forEach var="product" items="${mostViewedProducts}">
                        <a href="ProductDetail?id=${product.id}" class="product" style="height: auto; text-decoration: none; color: inherit; width: 28%" data-category="${product.typeId}">
                            <div class="img">
                                <img src="${product.image}" alt="${product.name}" class="img-p" style="width: 100%;">
                            </div>
                            <div class="infor-p" style="padding: 10px 16px;">
                                <span class="title-p t-none t-white d-block">${product.name}</span>
                                <span class="t-none t-white d-block" style="padding: 10px 0; font-size: 13px;">${product.duration}</span>
                                <div class="price-p d-flex t-between">
                                    <span class="t-none t-white" style="font-weight: 550;">
                                        <f:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/>đ
                                    </span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                    <!-- display none product-->

                    <c:forEach var="product" items="${mostViewedProductsNones}">
                        <a href="ProductDetail?id=${product.id}" class="product product-none" style="text-decoration: none; color: inherit; width: 28%" data-category="${product.typeId}">
                            <div class="img">
                                <img src="${product.image}" alt="${product.name}" class="img-p" style="height: auto; width: 100%;">
                            </div>
                            <div class="infor-p" style="padding: 10px 16px;">
                                <span class="title-p t-none t-white d-block">${product.name}</span>
                                <span class="t-none t-white d-block" style="padding: 10px 0; font-size: 13px;">${product.duration}</span>
                                <div class="price-p d-flex t-between">
                                    <span class="t-none t-white" style="font-weight: 550;">
                                        <f:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/>đ
                                    </span>
                                </div>
                            </div>
                        </a>
                    </c:forEach>

                    <!-- end display none product -->
                </div>
                <div class="div d-flex t-center" style="padding: 20px 0;">
                    <button class="t-white t-center t-none more"
                            style="background-color:#2d3539 ;border: none;cursor: pointer;padding: 8px;">
                        Xem thêm</button>
                </div>
                <div class="div d-none t-center hide" style="padding: 20px 0;">
                    <button class="t-white t-center t-none hide-btn"
                            style="background-color:#2d3539 ;border: none;cursor: pointer;padding: 8px;">
                        Ẩn bớt</button>
                </div>
                <div class="bar"></div>
            </div>
        </div>
        <div id="section3">
            <div class="header-section1" style="height: 40px;">
                <a href="javascript:void(0);" class="title text-decoration-none">
                    <i class="fa-solid fa-music" style="color: #1b5bd7; padding-right: 10px;"></i>
                    Giải trí
                    <span>Netflix, Spotify, Youtube Premium, ...</span>
                    <p style="color: #999; font-weight: 100;padding-left: 35px;">Tổng hợp bản quyền các nền tảng xem phim, nghe
                        nhạc phù hợp với nhu cầu của bạn</p>
                </a>
                <a href="products" class="more-infor text-decoration-none">Xem toàn bộ</a>
            </div>
            <div class="product-grid d-flex row t-between">
                <c:forEach var="product" items="${entertainmentProducts}">
                    <a href="ProductDetail?id=${product.id}" class="product" style="text-decoration: none; color: inherit;" data-category="${product.typeId}">
                        <div class="img">
                            <img src="${product.image}" alt="${product.name}" class="img-p" style="width: 100%;">
                        </div>
                        <div class="infor-p" style="padding: 10px 16px;">
                            <span class="title-p t-none t-black d-block">${product.name}</span>
                            <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">${product.duration}</span>
                            <div class="price-p d-flex t-between">
                                <span class="t-none t-black" style="font-weight: 550;">
                                    <f:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/>đ
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
        <!--  -->
        <div id="section4">
            <div class="header-section1" style="height: 40px;">
                <a href="javascript:void(0);" class="title text-decoration-none">
                    <i class="fa-brands fa-windows" style="color: #1b5bd7; padding-right: 10px;"></i>
                    Làm việc
                    <span>Windows, Office, Canva, Github...</span>
                    <p style="color: #999; font-weight: 100;padding-left: 35px;">Tổng hợp bản quyền các phần mềm và công cụ hỗ
                        trợ tối đa công việc của bạn</p>
                </a>
                <a href="products" class="more-infor text-decoration-none">Xem toàn bộ</a>
            </div>
            <div class="product-grid d-flex row t-between">
                <c:forEach var="product" items="${workProducts}">
                    <a href="ProductDetail?id=${product.id}" class="product" style="text-decoration: none; color: inherit;" data-category="${product.typeId}">
                        <div class="img">
                            <img src="${product.image}" alt="${product.name}" class="img-p" style="width: 100%;">
                        </div>
                        <div class="infor-p" style="padding: 10px 16px;">
                            <span class="title-p t-none t-black d-block">${product.name}</span>
                            <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">${product.duration}</span>
                            <div class="price-p d-flex t-between">
                                <span class="t-none t-black" style="font-weight: 550;">
                                    <f:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/>đ
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
        <!--  -->
        <div id="section5">
            <div class="header-section1" style="height: 40px;">
                <a href="javascript:void(0);" class="title text-decoration-none">
                    <i class="fa-solid fa-circle-check" style="color: #1b5bd7; padding-right: 10px;"></i>
                    Tiện ích
                    <span>Notion, AdGuard...</span>
                    <p style="color: #999; font-weight: 100;padding-left: 35px;">Tổng hợp bản quyền các phần mềm tiện ích đa
                        chức năng</p>
                </a>
                <a href="products" class="more-infor text-decoration-none">Xem toàn bộ</a>
            </div>
            <div class="product-grid d-flex row t-between">
                <c:forEach var="product" items="${utilityProducts}">
                    <a href="ProductDetail?id=${product.id}" class="product" style="text-decoration: none; color: inherit;" data-category="${product.typeId}">
                        <div class="img">
                            <img src="${product.image}" alt="${product.name}" class="img-p" style="width: 100%;">
                        </div>
                        <div class="infor-p" style="padding: 10px 16px;">
                            <span class="title-p t-none t-black d-block">${product.name}</span>
                            <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">${product.duration}</span>
                            <div class="price-p d-flex t-between">
                                <span class="t-none t-black" style="font-weight: 550;">
                                    <f:formatNumber value="${product.price}" type="number" maxFractionDigits="0" groupingUsed="true"/>đ
                                </span>
                            </div>
                        </div>
                    </a>
                </c:forEach>

            </div>
        </div>
    </div>
</div>
<div id="footer">
    <jsp:include page="footer.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/Js/home.js"></script>
</body>
</html>

