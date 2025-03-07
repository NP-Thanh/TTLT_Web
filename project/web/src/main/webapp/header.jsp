<%@ page import="vn.edu.hcmuaf.fit.web.model.ProductType" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.Cart" %>
<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.CartProduct" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>--%>
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
        url="account";
    }
    List<CartProduct> cartItems = shoppingCart.getList();
    String e = request.getAttribute("error") == null ? "" : (String) request.getAttribute("error");
%>
<head>
    <meta charset="UTF-8">
    <title>Header</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
    <style>
        html, body, p {
            width: 100%;
            margin: 0;
            padding: 0;
            font-size: 14px;
            color: black;
            font-family: Arial, sans-serif;
        }

        #section_normal_1 {
            height: 160px;
            background: rgb(27 91 215);
            color: white;
        }

        #section_normal_1 .first_column {
            height: 24px;
            padding: 12px;
        }

        #section_normal_1 .first_column .alpha {
            float: left;
        }

        #section_normal_1 .first_column .omega {
            float: right;
        }

        #section_normal_1 .first_column .pix_text {
            color: white;
            text-decoration: none;
        }

        #section_normal_1 .first_column .pix_text {
            cursor: pointer;
            font-size: medium;
        }

        #section_normal_1 .second_column {
            height: 40px;
            padding: 12px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        #section_normal_1 .second_column .alpha {
            float: left;
        }

        #section_normal_1 .second_column .logo_img {
            color: white;
            text-decoration: none;
            font-weight: 600;
            font-size: larger;
        }

        #section_normal_1 .second_column .search_box {
            height: 43px;
            width: 520px;
            background: white;
            border-radius: 5px;
            display: flex;
            align-items: center;
        }

        #section_normal_1 .second_column .search_box .input {
            height: 38px;
            margin-right: 10px;
            margin-left: 10px;
            font-weight: 200;
            font-size: medium;
            border: none;
            width: 445px;
            outline: none;
        }

        #section_normal_1 .second_column .search_box .bt_search {
            height: 38px;
            width: 52px;
            background: #076ce3;
            margin-right: 5px;
            border-radius: 3px;
            display: flex;
            align-items: center;
        }

        #section_normal_1 .second_column .search_box .bt_search:hover {
            cursor: pointer;
        }

        #section_normal_1 .second_column .search_box .fa-search {
            margin-left: 7px;
        }

        #section_normal_1 .second_column .omega {
            float: right;
        }

        #section_normal_1 .second_column .bt_gio_hang {
            width: 150px;
            height: 35px;
            border: 1px solid white;
            border-radius: 5px;
            display: inline-flex;
            align-items: center;
            text-decoration: none;
            color: white;
            margin-right: 10px;
        }

        #section_normal_1 .second_column .fa-shopping-cart {
            margin-left: 10px;
            margin-right: 10px;
        }

        #section_normal_1 .second_column .text {
            font-weight: 500;
            font-size: 16px;
        }

        #section_normal_1 .second_column .bt_gio_hang .number {
            font-size: 15px;
            height: 22px;
            width: 22px;
            color: rgb(27 91 215);
            background: white;
            border-radius: 9999px;
            margin-left: 10px;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        #section_normal_1 .second_column .bt_user {
            width: 150px;
            height: 35px;
            background: white;
            border-radius: 5px;
            display: inline-flex;
            align-items: center;
            color: rgb(27 91 215);
            text-decoration: none;
        }

        #section_normal_1 .second_column .fa-user {
            margin-left: 15px;
            margin-right: 10px;
        }

        #section_normal_1 .third_column {
            height: 58px;
        }

        #section_normal_1 .third_column .menu {
            margin-top: 15px;
            margin-left: 10px;
            font-weight: 600;
            font-size: 15px;
            width: auto;
        }

        #section_normal_1 .third_column .menu .htext {
            text-decoration: none;
            color: white;
            margin-right: 20px;
            height: 20px;
            padding-bottom: 14px;
            cursor: pointer;
        }

        #section_normal_1 .third_column .menu .htext:hover {
            color: white;
            border-bottom: 2px solid white;

        }

        .separator {
            height: 12px;
            background-color: rgba(255, 255, 255, 0.25);
            align-self: stretch;
            display: inline-flex;
            margin: 0 3px;
            border-color: hsla(0, 0%, 100%, .12);
        }

        .container {
            margin-left: 35px;
            margin-right: 35px;
        }

    </style>
</head>
<body>
<div class="header_style pix_buider_bg" id="section_normal_1">
    <div class="container" style="    margin-left: 35px;
    margin-right: 35px; width: auto;display: block;">
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
                        <i class="fas fa-search fa-2x" style="color: white;margin-left: 7px"></i>
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
                <a href="products?category=Giải trí" class="htext" target="_top">Giải trí</a>
                <a href="products?category=Học tập" class="htext" target="_top">Học tập</a>
                <a href="products?category=Làm việc" class="htext" target="_top">Làm việc</a>
                <a href="products?category=Tiện ích" class="htext" target="_top">Tiện ích</a>
                <a href="products?category=Bảo mật" class="htext" target="_top">Bảo mật</a>
                <a href="products?category=Lưu trữ" class="htext" target="_top">Lưu trữ</a>
<%--                <% for (ProductType p : productTypes) {%>--%>
<%--                <a href="products?category='<%=p.getType()%>'" class="htext" target="_top"><%=p.getType()%></a>--%>
<%--                <% } %>--%>
            </div>
        </div>
    </div>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const searchForm = document.getElementById('searchForm');
        const searchInput = document.getElementById('searchInput');

        searchForm.addEventListener('submit', function (event) {
            const query = searchInput.value.trim();
            if (!query) {
                event.preventDefault(); // Ngăn gửi form
                alert('Vui lòng nhập thông tin tìm kiếm.');
            }
        });
    });

</script>
</body>
</html>