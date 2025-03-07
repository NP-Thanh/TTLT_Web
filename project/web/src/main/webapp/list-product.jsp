<%@ page import="vn.edu.hcmuaf.fit.web.model.ProductType" %><%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 12/23/2024
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="vn.edu.hcmuaf.fit.web.model.ProductType" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.Cart" %>
<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.CartProduct" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%
  List<ProductType> productTypes = (List<ProductType>) request.getAttribute("productTypes");
  Cart shoppingCart = (Cart) session.getAttribute("cart");
  if (shoppingCart == null) {
    shoppingCart= new Cart();
  }

  User user = (User) request.getAttribute("user");
  String userName;
  String url;
  String logoutUrl = "logout";
  if (user==null){
    userName="Đăng nhập";
    url= "login";
  } else {
    String originalName = user.getName();
    if (originalName != null && originalName.length() > 4) {
      userName = "Chào, " + originalName.substring(0, 4) + "...";
    } else {
      userName = "Chào, " + originalName;
    }
    url="#";
  }
  List<CartProduct> cartItems = shoppingCart.getList();
  String e = request.getAttribute("error") == null ? "" : (String) request.getAttribute("error");
%>

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Css/header.css">
  <title>Document</title>

</head>

<body>
<div id="header">
  <div class="header_style pix_buider_bg" id="section_normal_1">
    <div class="container" style="    margin-left: 35px;
    margin-right: 35px; width: auto">
      <div class="first_column">
        <div class="two-rows-text omega">
                      <span class="editContent">
                          <a href="trungtamtrogiup.jsp" target="_blank" class="pix_text">Trung tâm trợ giúp</a>
                          <hr class="separator">
                          <a href="lienhehotro.jsp" target="_blank" class="pix_text">Liên hệ hỗ trợ</a>
                      </span>
        </div>
      </div>
      <div class="second_column">
        <div class="logo alpha">
          <a href="home" class="logo_img" target="_top">
            <img src="Images/logo.png" height="37" width="135"/></a>
        </div>
        <div class="search_box">
          <form id="searchForm" style="display: flex" action="products" method="GET">
            <input id="searchInput" name="search"    style="margin-right: 6px;" type="text" class="input" placeholder="Tìm kiếm sản phẩm. vd:Spotify, Windows...">
            <button id="searchButton" class="bt_search" style="border: none">
              <i class="fas fa-search fa-2x" style="color: white"></i>
            </button>
          </form>
        </div>
        <div class="omega">
          <a href="Cart" target="_top" class="bt_gio_hang">
            <i class="fas fa-shopping-cart fa-lg" style="color: #ffffff;"></i>
            <span class="text">Giỏ hàng</span>
            <span class="number"><%=cartItems.size()%></span>
          </a>
          <a href="<%=url%>" target="_top" class="bt_user">
            <i class="fas fa-user fa-lg" style="color: #1b5bd7;"></i>
            <span class="text"><%=userName%></span>
          </a>


        </div>
      </div>
      <div class="third_column">
        <div class="menu">
          <% for (ProductType p : productTypes) {%>
          <a href="products?category=<%=p.getType()%>" class="htext" target="_top"><%=p.getType()%></a>
          <% } %>
        </div>
      </div>
    </div>
  </div>
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
              <%--                               style="color: #1b5bd7;"--%>
              >Tất Cả</a>

            </div>
          </div>
          <div class="mt-8">
            <div class="mt-8 d-flex align-center cursor-pointer">
              <i class="fa-solid fa-folder-closed icon-folder"></i>
              <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600">Giải Trí</a> -->
              <a href="products?category=Giải trí"  class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                 data-category="Giải trí"
              >Giải Trí</a>
            </div>
          </div>
          <div class="mt-8">
            <div class="mt-8 d-flex align-center cursor-pointer">
              <i class="fa-solid fa-folder-closed icon-folder"></i>
              <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Học Tập</a> -->
              <a href="products?category=Học tập"  class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                 data-category="Học tập"
              >Học Tập</a>
            </div>
          </div>
          <div class="mt-8">
            <div class="mt-8 d-flex align-center cursor-pointer">
              <i class="fa-solid fa-folder-closed icon-folder"></i>
              <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Làm Việc</a> -->
              <a href="products?category=Làm việc"  class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                 data-category="Làm việc"
              >Làm Việc</a>
            </div>
          </div>
          <div class="mt-8">
            <div class="mt-8 d-flex align-center cursor-pointer">
              <i class="fa-solid fa-folder-closed icon-folder"></i>
              <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Tiện Ích</a> -->
              <a href="products?category=Tiện ích" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                 data-category="Tiện ích"
              <%--                            //onclick="filterProducts(event)//   --%>
              >Tiện Ích</a>
            </div>
          </div>
          <div class="mt-8">
            <div class="mt-8 d-flex align-center cursor-pointer">
              <i class="fa-solid fa-folder-closed icon-folder"></i>
              <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Bảo Mật</a> -->
              <a href="products?category=Bảo mật" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
                 data-category="Bảo mật"
              >Bảo Mật</a>
            </div>
          </div>
          <div class="mt-8">
            <div class="mt-8 d-flex align-center cursor-pointer">
              <i class="fa-solid fa-folder-closed icon-folder"></i>
              <!-- <a href="" class="ml-8 text-decoration-none t-black f-weight-600 t-black">Lưu Trữ</a> -->
              <a href="products?category=Lưu trữ" class="ml-8 text-decoration-none f-weight-600 t-black category-filter"
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
            <div class="d-flex align-center justify-space-between cursor-pointer title-sort">
              <p style="font-weight: bold; font-size: 16px;">Sắp Xếp Theo</p>
              <i id="icon-down" class="fa-solid fa-caret-down "></i>

            </div>
            <div id="sort-options" class="hide-list-category mt-16">
              <div class="mt-8">
                <div class="mt-8 d-flex align-center cursor-pointer" style="margin-bottom: 15px;">
<%--                  <div class="wrap-btn">--%>
<%--                    <div class="radio-btn"></div>--%>
<%--                  </div>--%>
                  <a href="products?sortsale=1" class="ml-8" style="color: gray;text-decoration: none">Bán Chạy Nhất</a>
                </div>
              </div>
              <div class="mt-8">
                <div class="mt-8 d-flex align-center cursor-pointer sort-option" style="margin-bottom: 15px;" data-sort="newest">
<%--                  <div class="wrap-btn">--%>
<%--                    <div class="radio-btn"></div>--%>
<%--                  </div>--%>
                  <a href="products?sort=2" class="ml-8" style="color: gray;text-decoration: none">Mới Cập Nhật</a>
                </div>
              </div>
              <div class="mt-8">
                <div class="mt-8 d-flex align-center cursor-pointer sort-option" style="margin-bottom: 15px;" data-sort="price-asc">
<%--                  <div class="wrap-btn">--%>
<%--                    <div class="radio-btn"></div>--%>
<%--                  </div>--%>
                  <a href="products?sortasc=3" class="ml-8" style="color: gray;text-decoration: none">Giá Thấp Đến Cao</a>
                </div>
              </div>
              <div class="mt-8">
                <div class="mt-8 d-flex align-center cursor-pointer sort-option" style="margin-bottom: 15px;" data-sort="price-desc">
<%--                  <div class="wrap-btn">--%>
<%--                    <div class="radio-btn"></div>--%>
<%--                  </div>--%>
                  <a href="products?sortdesc=4" class="ml-8" style="color: gray;text-decoration: none">Giá Cao Đến Thấp</a>
                </div>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-8">
      <h1 style="margin: 0;padding-left: 10px">Danh sách sản phẩm</h1>
      <div id="list-product" class="list-product" style="margin-left: 20px;">
        <c:choose>
          <c:when test="${not empty products}">
            <div id="contentProduct" class="row justify-space-between">
              <c:forEach var="product" items="${products}">
                <a onclick="viewProduct()" href="ProductDetail?id=${product.id}" class="product" data-category="${product.typeId}" style="text-decoration: none; color: inherit;">
                  <div class="img">
                    <!-- Hiển thị hình ảnh, kiểm tra null -->
                    <img src="${product.image != null ? product.image : 'default-image.jpg'}" alt="${product.name}" class="img-p" style="width: 100%; height: 220px">
                  </div>
                  <div class="infor-p" style="padding: 10px 16px;">
                    <!-- Tên sản phẩm -->
                    <span class="title-p t-none t-black d-block">${product.name}</span>
                    <!-- Thời gian sử dụng -->
                    <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">
                        ${product.duration}
                    </span>
                    <div class="price-p d-flex t-between">
                      <!-- Giá sản phẩm -->

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
  <jsp:include page="footer.jsp"/>
</div>
<script src="${pageContext.request.contextPath}/Js/product.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</body>

</html>