
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="vi">
<%
    Product product = (Product) request.getAttribute("product");
    ProductDetail detail = (ProductDetail) request.getAttribute("productDetail");
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
    double rateAVG = (double) request.getAttribute("rateAVG");
    double roundedRateAVG = Math.round(rateAVG * 10) / 10.0;

    List<CommentByUser> commentsByUser = (List<CommentByUser>) request.getAttribute("commentsByUser");
    List<Product> relatedProducts= (List<Product>) request.getAttribute("relatedProducts");
%>
<head>
    <meta charset="UTF-8">
    <title>"Trang chi tiết sản phẩm"</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
    <style>
        html, body {
            width: 100%;
            margin: 0;
            padding: 0;
            font-size: 14px;
            color: #404040;
            font-family: Arial, sans-serif;
            height: 100%;
        }

        #page {
            display: flex;
            flex-direction: column;
            min-height: 100%;
        }


        .iframe-header {
            height: 170px;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .iframe-footer {
            width: 100%;
            height: 375px;
            position: relative;
            margin-top: auto;
        }

        #section_normal_2 {
            color: black;
            display: flex;
            flex-wrap: wrap;
            margin-top: 200px;
        }

        #section_normal_2 .alpha {
            width: 830px;
            height: auto;
            margin-right: 0;
        }

        #section_normal_2 .part1 {
            display: flex;
            flex-wrap: wrap;
            width: 100%;
        }

        #section_normal_2 .left_infor {
            height: 330px;
            width: 350px;
            margin-top: 10px;
        }

        #section_normal_2 .main_product {
            border: 1px solid rgba(0, 0, 0, 0.12);
        }

        #section_normal_2 .right_infor {
            display: block;
            height: 330px;
            width: 460px;
        }

        #section_normal_2 .title {
            color: #686868;
            font-size: max(18px, min(2.5vw, 24px));
            font-weight: 700;
            line-height: 170%;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        #section_normal_2 .kind_product {
            font-size: 15px;
            line-height: 16px;
            width: 80%;
            color: #7a8c96;
            margin: 0;
        }

        #section_normal_2 .rate-wrap {
            display: flex;
            width: 100%;
            align-items: center;
        }

        #section_normal_2 .rate-wrap .number-rate {
            font-weight: 600;
            width: auto;
            color: #FFD43B;
            margin-left: 5px;
            font-size: 15px;
            margin-right: 5px;
        }

        #section_normal_2 .rate-wrap .text-rate {
            color: rgb(63 141 233);
            width: 115px;
            font-weight: 550;
            cursor: pointer;
        }

        #section_normal_2 .info-product .info {
            margin-top: 10px;
        }

        #section_normal_2 .info-product .tile-info {
            font-weight: 600;
            font-size: 15px;
            color: #43494e;
        }

        #section_normal_2 .info-product .text {
            font-size: 14px;
            color: #50555a;
        }

        #section_normal_2 .part2 {
            height: auto;
            width: 100%;
        }

        #section_normal_2 .commitment {
            width: 840px;
            height: 150px;
            background: #f6f8f9;
            display: flex;
            flex-wrap: wrap;
        }

        #section_normal_2 .commitment .box {
            width: 210px;
            height: 150px;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            text-align: center;
        }

        #section_normal_2 .commitment .box .atext {
            width: 150px;
            height: auto;
            font-size: 13px;
            color: #a9b1b8;
            line-height: 150%;
        }

        #section_normal_2 .commitment .box .btext {
            width: 150px;
            height: auto;
            font-size: 15px;
            font-weight: 600;
            color: #74828c;
            line-height: 130%;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        #section_normal_2 .introduce {
            margin-top: 40px;
            height: auto;
        }

        #section_normal_2 .introduce .ctext {
            line-height: 150%;
            margin-bottom: 16px;
            font-size: 15px;
        }

        .description {
            width: 100%; /* Chiều rộng 100% khung chứa */
            border: none; /* Loại bỏ khung */
            resize: none; /* Không cho thay đổi kích thước */
            overflow: hidden; /* Ẩn thanh cuộn */
            outline: none; /* Không viền khi focus */
            background-color: transparent; /* Nền trong suốt */
            font-family: inherit; /* Sử dụng font của khung chứa */
            font-size: inherit; /* Sử dụng kích cỡ chữ của khung chứa */
        }

        #section_normal_2 .comment {
            margin-top: 30px;
            width: 100%;
            height: auto;
        }

        #section_normal_2 .see-all {
            margin-bottom: 15px;
        }

        #section_normal_2 .see-all a {
            color: #1b5bd7;
        }

        #section_normal_2 .customer-review {
            min-height: 100px;
            display: flex;
            flex-wrap: wrap;
            margin-bottom: 15px;
            padding-bottom: 15px;
            border: 2px solid #ddd;
            border-radius: 10px;
        }

        #section_normal_2 .review-content {
            width: 100%;
            height: auto;
            padding: 15px;
        }


        #section_normal_2 .customer-name {
            font-size: 15px;
            font-weight: bold;
            color: #43494e;
            margin: 0;
        }

        #section_normal_2 .time-comment {
            font-size: 13px;
            font-weight: normal;
            color: rgba(116, 130, 140, 0.45);
        }

        #section_normal_2 .omega {
            width: 415px;
            height: auto;
            margin-left: 30px;
            margin-top: 30px;
        }

        #section_normal_2 .box-omega {
            width: 90%;
            margin-top: 100px;
            padding: 10px;
        }

        #section_normal_2 .buyer-border {
            width: 100%;
            border: 1px solid #ddd;
            padding: 15px;
        }

        #section_normal_2 .duration {
            width: auto;
            margin-top: 15px;
        }

        #section_normal_2 .duration .time-duration {
            width: 90px;
            height: 32px;
            background: #f5f8ff;
            border: 1px solid #1b5bd7;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-top: 10px;
        }

        #section_normal_2 .time-duration .time {
            font-weight: 400;
            font-size: 14px;
            color: #1b5bd7;
        }

        #section_normal_2 .cost {
            margin-top: 25px;
            line-height: 23px;
        }

        .main-cost {
            text-decoration: line-through;
            color: #c0c0c0;
        }

        #section_normal_2 .button-buyer {
            display: flex;
        }

        #section_normal_2 .elevation-0 {
            border-radius: 5px;
            cursor: pointer;
            border: thin solid;
        }

        #section_normal_2 .elevation-0:hover {
            background-color: rgba(150, 175, 214, 0.13) !important;
        }

        #section_normal_2 .btn-buy:hover {
            background-color: #0560cd !important;
        }

        #section_normal_2 .button-buyer .text {
            font-weight: 700;
            font-size: 15px;
            line-height: 22px;
        }

        #section_normal_2 .mt-4 {
            width: 410px;
        }

        #section_normal_2 .item {
            width: 94%;
            height: auto;
            align-items: center;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 10px;
        }

        #section_normal_2 .item .item-info {
            align-items: center;
        }

        .flex {
            display: flex;
        }

        .justify-space {
            justify-content: space-between;
        }

        .mx-3 {
            margin-left: 10px;
            margin-right: 10px;
        }

        .text-xs {
            width: auto;
            font-size: 15px;
            line-height: 130%;
            height: auto;
            margin-bottom: 5px;
            margin-right: 10px;
            max-width: 220px;
        }


        .gray {
            color: #bbb;
        }

        .color-gray-black {
            color: #54546a;
        }

        .star-rating {
            margin: 5px 0;
        }

        .empty-star {
            color: #ccc; /* Màu xám cho sao trống */
        }


        .star-rating i {
            color: #FFD43B;
            margin-right: 2px;
        }

        .customer-comment {
            font-size: 14px;
            color: #50555a;
            line-height: 1.5;
            margin-top: 5px;
        }


        .divider {
            width: 100%;
            margin-top: 15px;
            margin-bottom: 15px;
            border-color: rgba(0, 0, 0, .12);
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 0;
            line-height: 150%;
            font-size: 15px;
        }

        h2 {
            font-size: inherit;
        }

        .containersp {
            margin-left: 35px;
            margin-right: 35px;
        }

        .cart-notification {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #4faf53;
            color: white;
            padding: 15px 20px;
            border-radius: 5px;
            font-size: 14px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            z-index: 1000;
            animation: fadeIn 0.5s ease-out, fadeOut 0.5s ease-in 2.5s;
        }

        @keyframes fadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes fadeOut {
            from {
                opacity: 1;
            }
            to {
                opacity: 0;
            }
        }


    </style>
</head>

<body>
<div id="page" class="page">
    <div class="iframe-header">
        <jsp:include page="header.jsp"/>
    </div>
    <div class="pix_normal_2" id="section_normal_2">
        <div class="containersp alpha">
            <div class="part1">
                <div class="left_infor">
                    <img class="main_product" src="<%=product.getImage()%>" height="250" width="250"/>
                </div>
                <div class="right_infor">
                    <h1 class="title"><%= product.getName() %></h1>
                    <h3 class="kind_product"><%=detail.getManufacturer()%></h3>
                    <hr class="divider">
                    <div class="rate-wrap">
                        <i class="fas fa-star fa-lg" style="color: #FFD43B;"></i>
                        <p class="number-rate"><%=roundedRateAVG%></p>
                        <a href="Comment?id=<%=product.getId()%>" style="text-decoration: none;">
                            <p class="text-rate">(<%=comments.size()%> lượt đánh giá)</p>
                        </a>
                    </div>
                    <div class="info-product">
                        <div class="info">
                            <span style="font-size: 15px; color: gray"><%=detail.getIntroduction()%></span>
                        </div>
                        <div class="info">
                            <span class="tile-info">Hỗ trợ:</span>
                            <span class="text"><%=detail.getSupport()%></span>
                        </div>
                        <div class="info">
                            <span class="tile-info">Thời hạn sử dụng:</span>
                            <span class="text"><%=product.getDuration()%></span>
                        </div>
                        <div class="info">
                            <span class="tile-info">Hoàn tiền:</span>
                            <span class="text">100% nếu key lỗi</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="part2">
                <!-- Nội dung khác của bạn -->
            </div>
        </div>
        <div class="containersp omega">
            <div class=box-omega>
                <div class="buyer-border">
                    <h1 class="title" style="font-size: 18px; margin-top: 0"><%=product.getName()%></h1>
                    <h3 class="kind_product"><%=detail.getManufacturer()%></h3>
                    <div class="duration">
                        <p style="font-size: 15px; font-weight: 600; color: #5c5c5c">Thời hạn</p>
                        <div class="time-duration">
                            <span class="time" style="text-transform: uppercase"><%=product.getDuration()%></span>
                        </div>
                    </div>
                    <%
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formattedPrice = formatter.format(product.getPrice());
                    %>
                    <div class="cost">
                        <p style="font-size: 18px; font-weight: 600"><%=formattedPrice%>đ</p>
                    </div>
                    <hr class="divider">
                    <div class="button-buyer">
                        <button type="button" class="elevation-0 btn-buy"
                                style="height: 48px; width: 40%; margin-right: 10px;
                                background-color: #076CE3; border-color: #076CE3; color: white"
                                onclick="window.location.href='paymentProduct?pid=<%=product.getId()%>';">
                            <span class="text">Mua ngay</span>
                        </button>
                        <!-- Thêm ID cho nút "Thêm vào giỏ hàng" -->
                        <a href="#" id="add-to-cart-btn" class="elevation-0 add-to-cart"
                           style="height:48px; width:60%; color:#076CE3; background-color:#ffffff; text-decoration: none; display: flex; align-items: center; justify-content: center; border: 1px solid #076CE3; border-radius: 5px;">
                            <i class="fas fa-shopping-cart fa-lg" style="color: #076CE3; margin-right: 7px;"></i>
                            <span class="text">Thêm giỏ hàng</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="iframe-footer">
        <jsp:include page="footer.jsp"/>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#add-to-cart-btn').click(function(event) {
            event.preventDefault(); // Ngăn chặn hành động mặc định của liên kết
            var productId = <%= product.getId() %>; // Lấy ID sản phẩm

            $.ajax({
                url: 'add-cart', // Đường dẫn đến servlet xử lý
                type: 'GET',
                data: { id: productId },
                success: function(response) {
                    // Cập nhật số lượng giỏ hàng nếu cần
                    $('#cart-count').text(response.newCartCount);
                },
                error: function(xhr, status, error) {
                    alert('Có lỗi xảy ra. Vui lòng thử lại.');
                }
            });
        });
    });
</script>
</body>
</html>