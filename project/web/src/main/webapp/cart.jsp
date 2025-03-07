<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.Cart" %>
<%@ page import="vn.edu.hcmuaf.fit.web.dao.cart.CartProduct" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.Discount" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%
    Cart shoppingCart = (Cart) session.getAttribute("cart");
    if (shoppingCart == null) {
        shoppingCart = new Cart();
    }
    double total = shoppingCart.getTotal();
    List<CartProduct> cartItems = shoppingCart.getList();
    String paymentURL = "#";
    if (cartItems.size() > 0) {
        paymentURL = "payment";
    }
    Discount discount = (Discount) request.getAttribute("discount");
    String e = request.getAttribute("error") == null ? "" : (String) request.getAttribute("error");
%>
%>
<head>
    <meta charset="UTF-8">
    <title>Trang giỏ hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"/>
    <style>
        html, body, p {
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

        .body_content {
            min-height: 400px;
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
            width: 810px;
            margin-right: 0;
            padding: 10px;
        }

        #body_page .omega {
            width: 380px;
            min-height: 400px;
            height: auto;
            margin-left: 30px;
        }

        #body_page .list-item {
            padding-top: 10px;
        }

        #body_page .item {
            width: 100%;
            height: 140px;
            border-radius: 5px;
            border: 1px solid #e5e7eb;
            margin-top: 20px;
            padding: 8px;
        }

        #body_page .payment {
            width: 100%;
            min-height: 400px;
            padding: 15px;
            padding-top: 30px !important;
        }

        .loc-relative {
            position: relative;
        }

        .border-gray {
            border: 1px solid #e5e7eb;
            border-radius: 5px;
        }

        .box-shadow-payment {
            box-shadow: 0 1px 1px rgba(0, 0, 0, .25), 0 2px 4px rgba(5, 27, 68, .08) !important;
        }

        .text-middle {
            font-size: 16px;
        }

        .upper-case {
            text-transform: uppercase;
        }

        .select-checkbox {
            position: absolute;
            top: 15px; /* Cách phía trên một khoảng nhỏ */
            right: 15px; /* Cách bên phải một khoảng nhỏ */
            width: 20px;
            height: 20px;
            appearance: none; /* Ẩn hiển thị mặc định của checkbox */
            background-color: #fff;
            border: 1px solid #c5c5c5;
            border-radius: 50%;
            cursor: pointer;
            margin: 0;
        }

        /* Hiệu ứng hình tròn khi checkbox được chọn */
        .select-checkbox:checked::after {
            content: "";
            display: block;
            width: 10px;
            height: 10px;
            margin: auto;
            background-color: rgba(7, 108, 227, 0.6);
            border-radius: 50%;
            position: relative;
            top: 4px;
        }

        #body_page .product-img {
            height: 120px;
            width: 120px;
            vertical-align: middle;
            border: 1px solid #e5e7eb;
        }

        .margin-top-bot {
            margin: 20px 0;
        }

        .sale-round-border {
            width: 55px;
            height: 25px;
            background: #f44c4c;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 15px;
            font-weight: 600;
        }

        .duration-round-border {
            width: 135px;
            height: 25px;
            background: #f2f4f7;
            color: #344054;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 14px;
        }

        .btn-add-sub {
            border: 1px solid #e5e7eb;
            border-radius: 5px;
            width: 120px;
            height: 30px;
        }

        .btn {
            width: 40px;
            font-size: 30px;
            color: #757575;
            cursor: pointer;
            transition: background-color 0.6s ease;
            user-select: none;
        }

        .btn:active {
            background-color: #b8c1ff;
        }

        .count {
            width: 42px;
            color: #416ec6;
            font-size: 15px;
            font-weight: 600;
            border-left: 1px solid #e5e7eb;
            border-right: 1px solid #e5e7eb;
        }

        .cost {
            font-size: 18px;
            color: #5a5a5a;
        }

        .btn-delete {
            width: 35px;
            height: 30px;
            border: 1px solid #ed5252;
            border-radius: 5px;
            margin-top: 5px;
            margin-bottom: 5px;
            cursor: pointer;
        }

        .btn-delete:hover {
            background: rgba(246, 159, 159, 0.51);
        }

        .f-right {
            float: right;
        }

        .main-cost {
            text-decoration: line-through;
            margin-right: 10px;
            color: #c0c0c0;
        }

        .w-full {
            width: 100%;
            height: 100px;
        }

        .iframe-header {
            height: 170px;
            width: 100%;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .text-gray {
            font-size: 16px;
            color: #6e7c87;
        }

        .text-title {
            font-size: 20px;
            line-height: 120%;
            margin: 0;
        }

        .button-voucher {
            background: white;
            width: 100px;
            height: 44px;
            border-radius: 5px;
            border: 1px solid #1976d2;
            pointer-events: auto;
            cursor: pointer;
        }

        .button-voucher:hover {
            background: rgba(25, 118, 210, 0.11) !important;
        }

        .button-pay {
            background: #1b5bd7;
            width: 100%;
            height: 44px;
            border-radius: 5px;
            border: 1px solid #1976d2;
            cursor: pointer;
            margin-top: 10px;
        }

        .font-sz18 {
            font-size: 18px;
        }

        .font-sz16 {
            font-size: 16px;
        }

        .pd-12 {
            padding: 12px;
        }


        .font-600 {
            font-weight: 600;
        }

        .d-flex {
            display: flex;
        }

        .justify-between {
            justify-content: space-between;
        }

        .items-center {
            align-items: center;
        }

        .justify-center {
            justify-content: center;
        }

        .wrapper {
            display: flex;
            flex-wrap: wrap;
        }

        .containersp {
            margin-left: 35px;
            margin-right: 35px;
        }
    </style>
</head>
<body>
<div class="page" id="page">
    <div class="iframe-header">
        <jsp:include page="header.jsp"/>
    </div>
    <div class="body_content" id="body_page">
        <div class="containersp alpha">
            <div class="d-flex justify-between items-center">
                <h5 class="font-600 text-title">
                    Giỏ hàng
                </h5>
                <span class="text-gray">
                        <span id="countProduct"><%=cartItems.size()%></span> Sản phẩm
                    </span>
            </div>
            <div class="list-item">
                <%
                    for (CartProduct c : cartItems) {
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String formattedPrice = formatter.format(c.getPrice());
                %>
                <div class="d-flex items-center loc-relative item">
                    <div class="product-img">
                        <img src="<%=c.getImage()%>" height="120" width="120"/>
                    </div>
                    <div class="w-full pd-12">
                        <div class="w-full d-flex" style="padding-left: 10px; height: auto">
                            <h4 class="font-600 text-title"><%=c.getProductName()%>
                            </h4>
                        </div>
                        <div class="w-full wrapper justify-between" style="margin-top: 8px; height: auto">
                            <span class="duration-round-border">Thời hạn: <%=c.getDuration()%></span>
                            <div class="d-flex btn-add-sub">
                                <a href="sub-cart?id=<%=c.getId()%>" style="text-decoration: none">
                                    <span class="d-flex items-center justify-center btn">-</span>
                                </a>
                                <div class="d-flex items-center justify-center count">
                                    <span><%=c.getQuantity()%></span>
                                </div>
                                <a href="add-cart?id=<%=c.getId()%>" style="text-decoration: none">
                                    <span class="d-flex items-center justify-center btn">+</span>
                                </a>
                            </div>
                            <span class="cost checkbox-cost"><%=formattedPrice%>đ</span>
                        </div>
                        <hr class="w-full" style="height: auto">
                        <a href="remove-cart?id=<%=c.getId()%>" style="text-decoration: none">
                            <div class="f-right">
                                <div class="d-flex items-center justify-center btn-delete">
                                    <i class="fas fa-trash-alt fa-lg" style="color: #ed5252;"></i>
                                </div>
                            </div>
                        </a>
                    </div>
                </div>
                <% }%>

            </div>
        </div>
        <div class="containersp omega">
            <div class="box-shadow-payment border-gray payment">
                <h5 class="font-600 text-title" style="margin-bottom: 15px">Đơn hàng của bạn</h5>
                <p class="font-600 upper-case text-middle">Sản phẩm</p>
                <hr class="w-full" style="height: auto; margin-top: 5px">
                <!-- Ô sản phẩm đc thêm vào -->
                <%
                    for (CartProduct c : cartItems) {
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String nameFormat = c.getProductName();
                        if (nameFormat.length() > 25) {
                            nameFormat = c.getProductName().substring(0, 25) + "...";
                        }
                        String totalPrice = formatter.format(c.getPrice() * c.getQuantity());
                %>
                <div class="d-flex items-center justify-between margin-top-bot omega-product">
                    <div>
                        <span class="text-gray"><%=nameFormat%></span>
                        <span class="font-sz16">X<%=c.getQuantity()%></span>
                    </div>
                    <div class="font-sz16 cost-add"><%=totalPrice%>đ</div>
                </div>
                <% }%>
                <hr class="w-full checkbox-content" style="height: auto; margin-top: 5px">
                <div class="d-flex items-center justify-between" style="margin-top: 20px; margin-bottom: 20px">
                    <div>
                        <span class="text-gray">Tạm tính</span>
                    </div>
                    <%
                        DecimalFormat formatter = new DecimalFormat("#,###");
                        String totalCost = formatter.format(total);
                        if (discount != null) {
                            totalCost = formatter.format(total * (100 - discount.getValue()) / 100);
                        }
                    %>
                    <div class="font-sz16 cost-all"><%=totalCost%>đ</div>
                </div>
                <div class="d-flex items-center">
                    <i class="fas fa-ticket-alt fa-2x"></i>
                    <p class="font-600 text-middle upper-case" style="margin-left: 10px">Mã ưu đãi</p>
                </div>
                <div class="d-flex items-center justify-between" style="margin-top: 15px; margin-bottom: 15px">
                    <%
                        if (discount != null) {
                    %>
                    <input id="discount-input" placeholder="Nhập mã giảm giá" value="<%=discount.getId()%>"
                           class="border-gray pd-12 text-gray"
                           style="width: 230px">
                    <%
                    } else {
                    %>
                    <input id="discount-input" placeholder="Nhập mã giảm giá" class="border-gray pd-12 text-gray"
                           style="width: 230px">
                    <%
                        }
                    %>
                    <button type="button" id="apply-button" class="button-voucher">
                        <span class="font-sz16" style="color: #1976d2">Áp dụng</span>
                    </button>
                </div>
                <hr class="w-full" style="height: auto; margin-top: 5px">
                <%
                    if (discount != null) {
                %>
                <form id="paymentForm" action="processPayment?did=<%=discount.getId()%>" method="POST"
                      style="display: none;">
                        <%
                    } else {
                        %>
                    <form id="paymentForm" action="processPayment" method="POST" style="display: none;">
                        <%
                            }
                        %>
                    </form>
                    <button id="payment" type="button" class="button-pay">
                        <span class="font-sz18 font-600" style="color: white">Thanh toán ngay</span>
                    </button>
                    <div class="pd-12" style="background: #f6f8f9; margin-top: 20px; color: #7a8c96; font-size: 13px">
                        Thông tin cá nhân của bạn sẽ được sử dụng để xử lý đơn hàng, tăng trải nghiệm sử dụng website,
                        và
                        cho các mục đích cụ thể khác đã được mô tả trong
                        <span style="color: rgba(0,0,0,0.62); font-weight: 600 ">chính sách riêng tư của chúng tôi.</span>
                    </div>
            </div>
        </div>
    </div>
    <div class="iframe-footer">
        <jsp:include page="footer.jsp"/>
    </div>
    <script>
        document.getElementById("apply-button").addEventListener("click", function () {
            // Lấy giá trị từ ô input
            const discountId = document.getElementById("discount-input").value.trim();

            // Kiểm tra nếu người dùng đã nhập ID
            if (discountId) {
                // Chuyển hướng đến trang /Cart?did=<id>
                window.location.href = '/web/Cart?did=' + discountId;
            } else {
                // Hiển thị thông báo nếu không nhập mã giảm giá
                alert("Vui lòng nhập mã giảm giá!");
            }
        });
    </script>
    <script>
        document.getElementById("payment").addEventListener("click", function () {
            const cartItem = <%= cartItems.size() %>;
            if (cartItem > 0) {
                // Gửi yêu cầu POST thông qua form
                document.getElementById("paymentForm").submit();
            } else {
                alert("Không có sản phẩm trong giỏ hàng!");
            }
        });
    </script>
</div>
</body>
</html>