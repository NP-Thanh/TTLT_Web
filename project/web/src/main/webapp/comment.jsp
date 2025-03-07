<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%
    List<Comment> comments = (List<Comment>) request.getAttribute("comments");
    List<CommentByUser> commentByUsers = (List<CommentByUser>) request.getAttribute("commentByUsers");
    Product product = (Product) request.getAttribute("product");
    ProductDetail detail = (ProductDetail) request.getAttribute("productDetail");
%>
<head>
    <meta charset="UTF-8">
    <title>Trang Đánh Giá</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
    <style>
        html, body, p {
            width: 100%;
            margin: 0;
            padding: 0;
            font-size: 13px;
            color: black;
            font-family: Arial, Helvetica, sans-serif;
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

        #body_page {
            min-height: 700px;
            height: auto;
            margin-top: 200px;
            display: flex;
            flex-wrap: wrap;
        }

        #body_page .alpha {
            width: 830px;
            height: auto;
            margin-right: 0;
        }

        #body_page .omega {
            width: 415px;
            height: auto;
            margin-left: 30px;
            margin-top: 30px;
        }

        .box-omega {
            width: 90%;
            margin-top: 100px;
            padding: 10px;
        }

        .buyer-border {
            width: 100%;
            border: 1px solid #ddd;
            padding: 15px;
        }

        .title {
            color: #686868;
            font-size: max(18px, min(2.5vw, 24px));
            font-weight: 700;
            line-height: 170%;
            margin-top: 10px;
            margin-bottom: 5px;
        }

        .kind_product {
            font-size: 15px;
            line-height: 16px;
            width: 80%;
            color: #7a8c96;
            margin: 0;
        }

        .duration {
            width: auto;
            margin-top: 15px;
        }

        .time-duration {
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

        .time-duration .time {
            font-weight: 400;
            font-size: 14px;
            color: #1b5bd7;
        }

        .cost {
            margin-top: 25px;
            line-height: 23px;
        }

        .main-cost {
            text-decoration: line-through;
            color: #c0c0c0;
        }

        .button-buyer {
            display: flex;
        }

        .elevation-0 {
            border-radius: 5px;
            cursor: pointer;
            border: thin solid;
        }

        .elevation-0:hover {
            background-color: rgba(150, 175, 214, 0.13) !important;
        }

        .btn-buy:hover {
            background-color: #0560cd !important;
        }

        .button-buyer .text {
            font-weight: 700;
            font-size: 15px;
            line-height: 22px;
        }

        .divider {
            width: 100%;
            margin-top: 15px;
            margin-bottom: 15px;
            border-color: rgba(0, 0, 0, .12);
        }

        .containersp {
            margin-left: 35px;
            margin-right: 35px;
        }

        .text-filter {
            font-weight: 600;
            font-size: 16px;
            color: #686868;
            margin-right: 3px;
        }

        .filter-sort-menu {
            display: flex;
            align-items: center;
            margin-bottom: 20px;
        }

        .filter, .sort {
            margin-right: 30px;
        }

        .dropdown {
            position: relative;
            display: inline-block;
        }

        .dropdown-toggle {
            color: #434343;
            padding: 5px;
            background-color: rgba(224, 224, 224, 0.4);
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
        }

        .dropdown-menu {
            display: none;
            position: absolute;
            top: 100%;
            left: 0;
            background-color: #f0f0f0;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            z-index: 1;
            border-radius: 5px;
            padding: 10px;
            color: #434343;
            font-weight: bold;
        }

        .dropdown-menu li {
            padding: 8px 10px;
            cursor: pointer;
            list-style-type: none; /* Bỏ dấu chấm */
            border-bottom: 1px solid #ddd; /* Đường gạch phân cách */
        }

        .dropdown-menu li:last-child {
            border-bottom: none;
        }

        .dropdown-menu li:hover {
            background-color: #ddd;
        }

        .review-box {
            margin-bottom: 15px;
            padding-bottom: 15px;
            background-color: #f9f9f9;
            border-radius: 10px;
            border: 1px solid #ddd;
        }

        .review-content {
            width: 100%;
            height: auto;
            padding: 15px;
        }

        .customer-name {
            font-size: 15px;
            font-weight: bold;
            color: #43494e;
            margin: 0;
        }

        .time-comment {
            font-size: 13px;
            font-weight: normal;
            color: rgba(116, 130, 140, 0.45);
        }

        .star-rating {
            margin: 5px 0;
        }

        .star-rating i {
            color: #FFD43B;
            margin-right: 2px;
        }

        .d-flex {
            display: flex;
        }

        .justify-space {
            justify-content: space-between;
        }

        .items-center {
            align-items: center;
        }

        .back-button {
            height: 35px;
            width: 110px;
            background-color: white;
            color: rgba(255, 0, 0, 0.54);
            border: 2px solid rgba(255, 0, 0, 0.54);
            border-radius: 5px;
            font-weight: 600;
            font-size: 16px;
            cursor: pointer;
        }

        .back-button i {
            color: #FF000089;
        }

        .back-button:hover {
            background-color: rgba(251, 45, 45, 0.79); /* Nền màu hồng đậm khi rê chuột */
            color: white;
        }

        .back-button:hover i {
            color: white;
        }

        .customer-comment {
            font-size: 14px;
            color: #50555a;
            line-height: 1.5;
            margin-top: 5px;
        }

    </style>
</head>
<body>
<div class="page" id="page">
    <div class="iframe-header">
        <jsp:include page="header.jsp"/>
    </div>
    <div id="body_page">
        <div class="alpha containersp">
            <div class="d-flex justify-space items-center">
                <h2 style="float: right;font-weight: 600; font-size: 24px; color: #686868">Bình luận & Đánh giá (<span
                        class="comment-count"><%=comments.size()%></span>)</h2>
                <a href="ProductDetail?id=<%=product.getId()%>" class="back-button"
                   style="text-decoration: none; display: flex; align-items: center; justify-content: center;">
                    <i class="fas fa-chevron-left fa-lg"></i>
                    Quay lại</a>
            </div>
            <form id="filter-sort-form" action="Comment" method="get">
                <input type="hidden" name="id" value="<%=product.getId()%>">
                <div class="filter-sort-menu">
                    <div class="filter">
                        <span class="text-filter">Phân loại:</span>
                        <select name="ratingFilter" id="ratingFilter">
                            <option value="all" <%= "all".equals(request.getParameter("ratingFilter")) ? "selected" : "" %>>
                                Tất cả
                            </option>
                            <option value="1" <%= "1".equals(request.getParameter("ratingFilter")) ? "selected" : "" %>>
                                1 ★
                            </option>
                            <option value="2" <%= "2".equals(request.getParameter("ratingFilter")) ? "selected" : "" %>>
                                2 ★
                            </option>
                            <option value="3" <%= "3".equals(request.getParameter("ratingFilter")) ? "selected" : "" %>>
                                3 ★
                            </option>
                            <option value="4" <%= "4".equals(request.getParameter("ratingFilter")) ? "selected" : "" %>>
                                4 ★
                            </option>
                            <option value="5" <%= "5".equals(request.getParameter("ratingFilter")) ? "selected" : "" %>>
                                5 ★
                            </option>
                        </select>

                    </div>
                    <div class="sort">
                        <span class="text-filter">Sắp xếp:</span>
                        <select name="sortOrder" id="sortOrder">
                            <option value="newest" <%= "newest".equals(request.getParameter("sortOrder")) ? "selected" : "" %>>
                                Gần đây nhất
                            </option>
                            <option value="oldest" <%= "oldest".equals(request.getParameter("sortOrder")) ? "selected" : "" %>>
                                Cũ nhất
                            </option>
                        </select>

                    </div>
                    <button type="submit">Lọc</button>
                </div>
            </form>


            <div class="review-box">
                <%
                    SimpleDateFormat formatterDate = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                    if (commentByUsers != null && !commentByUsers.isEmpty()) {
                        for (CommentByUser commentByUser : commentByUsers) {
                            User user = commentByUser.getUser();
                            Comment comment = commentByUser.getComment();
                            Timestamp date = comment.getDate();
                            String formattedDate = formatterDate.format(new java.util.Date(date.getTime()));
                            String userName = user.getName();
                            int maxLength = (int) (userName.length() * 0.7); // 70% tổng chiều dài
                            String displayName = userName.length() > maxLength ? userName.substring(0, maxLength) + "..." : userName;
                %>
                <div class="review-content">
                    <h4 class="customer-name">
                        <%= displayName %>
                        <span class="time-comment"><%= formattedDate %></span>
                    </h4>
                    <div class="star-rating">
                        <% for (int i = 1; i <= comment.getNum_rate(); i++) { %>
                        <i class="fas fa-star"></i>
                        <% } %>
                    </div>
                    <p class="customer-comment">
                        <%= comment.getComment() %>
                    </p>
                </div>
                <hr class="divider" style="width: 96%">
                <%
                    }
                } else {
                %>
                <div class="no-comments">
                    <p>Không có đánh giá nào cho sản phẩm này.</p>
                </div>
                <%
                    }
                %>
            </div>
        </div>
        <div class="containersp omega">
            <div class=box-omega>
                <div class="buyer-border">
                    <h1 class="title" style="font-size: 18px; margin-top: 0"><%=product.getName()%>
                    </h1>
                    <h3 class="kind_product"><%=detail.getManufacturer()%>
                    </h3>
                    <div class="duration">
                        <p style="font-size: 15px; font-weight: 600; color: #5c5c5c">Thời hạn</p>
                        <div class="time-duration">
                            <span class="time" style="text-transform: uppercase"><%=product.getDuration()%></span>
                        </div>
                    </div>
                    <%
                        // Định dạng số tiền
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
                        <a href="add-cart?id=<%=product.getId()%>" class="elevation-0 add-to-cart"
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
</body>
</html>
