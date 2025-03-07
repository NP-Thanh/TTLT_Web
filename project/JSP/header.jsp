<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
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
            margin-left: 12px;
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
            margin-left: 20px;
            margin-right: 15px;
        }

        #section_normal_1 .third_column {
            height: 58px;
        }

        #section_normal_1 .third_column .menu {
            margin-top: 15px;
            margin-left: 10px;
            font-weight: 600;
            cursor: pointer;
            font-size: 15px;
        }

        #section_normal_1 .third_column .menu .htext {
            text-decoration: none;
            color: white;
            margin-right: 20px;
            height: 20px;
            padding-bottom: 14px;
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
    <div class="container">
        <div class="first_column">
            <div class="two-rows-text omega">
                      <span class="editContent">
                          <a href="../HTML/trungtamtrogiup.html" target="_blank" class="pix_text">Trung tâm trợ giúp</a>
                          <hr class="separator">
                          <a href="../HTML/lienhehotro.html" target="_blank" class="pix_text">Liên hệ hỗ trợ</a>
                      </span>
            </div>
        </div>
        <div class="second_column">
            <div class="logo alpha">
                <a href="../HTML/home.html" class="logo_img" target="_top">
                    <img src="../Images/logo.png" height="37" width="135"/></a>
            </div>
            <div class="search_box">
                <input type="text" class="input" placeholder="Tìm kiếm sản phẩm. vd:Spotify, Windows...">
                <div class="bt_search">
                    <i class="fas fa-search fa-2x"></i>
                </div>
            </div>
            <div class="omega">
                <a href="../HTML/gio_hang.html" target="_top" class="bt_gio_hang">
                    <i class="fas fa-shopping-cart fa-lg" style="color: #ffffff;"></i>
                    <span class="text">Giỏ hàng</span>
                    <span class="number">0</span>
                </a>
                <a href="../HTML/taikhoan.html" target="_top" class="bt_user">
                    <i class="fas fa-user fa-lg" style="color: #1b5bd7;"></i>
                    <span class="text">Tài khoản</span>
                </a>
            </div>
        </div>
        <div class="third_column">
            <div class="menu">
                <c:forEach var="p" items="${data}">
                    <a href="#" class="htext" target="_top">${p.type}</a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>