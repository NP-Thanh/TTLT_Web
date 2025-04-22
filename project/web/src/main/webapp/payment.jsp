<%@ page import="java.util.List" %>
<%@ page import="vn.edu.hcmuaf.fit.web.model.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%
    OrderWithUser orderWithUser = (OrderWithUser) request.getAttribute("orderWithUser");
    Order order = (Order) request.getAttribute("order");
    Discount discount = (Discount) request.getAttribute("discount");
    User user = (User) request.getAttribute("user_order");
    List<Product> products = (List<Product>) request.getAttribute("products");
    List<OrderDetail> orderDetails = (List<OrderDetail>) request.getAttribute("orderDetails");
    List<Bank> banks = (List<Bank>) request.getAttribute("banks");
    Bank bank_selected = (Bank) request.getAttribute("bank_selected");
    DecimalFormat formatter = new DecimalFormat("#,###");
    String formattedPrice = formatter.format(order.getTotal_amount());
    if (discount != null) {
        formattedPrice = formatter.format(order.getTotal_amount() * (100 - discount.getValue()) / 100);
    }
%>
<head>
    <meta charset="UTF-8">
    <title>Trang thanh toán</title>
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

        #body_page .payment {
            width: 100%;
            min-height: 380px;
            padding: 15px;
            padding-top: 30px !important;
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

        .w-full {
            width: 100%;
            height: 100px;
        }

        .text-gray {
            font-size: 16px;
            color: #6e7c87;
        }

        .text-code {
            text-decoration: underline;
        }

        .info {
            width: 95%;
            height: 200px;
            border: 2px solid #e5e7eb;
            border-radius: 5px;
            margin-top: 20px;
            flex-direction: column;
            padding-top: 20px;
            line-height: 25px;
            margin-left: 20px;
        }

        .info-item {
            display: flex;
            justify-content: flex-start; /* Căn lề trái cho các phần tử */
            align-items: center; /* Căn các phần tử theo chiều dọc */
            margin-bottom: 10px; /* Khoảng cách giữa các phần tử */
            flex-wrap: nowrap;
        }

        .info-item label {
            width: 200px; /* Cố định chiều rộng cho label */
            font-weight: 600;
            white-space: nowrap; /* Đảm bảo text không bị xuống dòng */
        }

        .text-title {
            font-size: 20px;
            line-height: 120%;
            margin: 0;
        }

        .editable {
            outline: none;
            border: none;
            background: transparent;
            width: 100%;
            height: 20px;
        }

        .editing-border {
            border: 1px solid #ccc; /* Viền xám nhạt */
            border-radius: 5px; /* Bo góc */
            padding: 1px; /* Tạo khoảng cách nhỏ bên trong */
        }

        .transfer-info .info-item {
            display: flex;
            justify-content: flex-start;
            align-items: center;
            margin-bottom: 10px;
            flex-wrap: nowrap;
        }

        .transfer-info .info-item label {
            width: 140px; /* Chiều rộng cố định cho nhãn */
            font-weight: 600;
            white-space: nowrap;
        }

        .transfer-info .info-item select {
            width: auto;
            height: 25px;
            min-width: 100px;
            font-weight: 600;
            white-space: nowrap;
            background: rgba(216, 216, 216, 0.5);
            color: #6e7c87;
        }

        .copyable {
            cursor: pointer;
            color: #1b5bd7;
        }

        .qr-code {
            width: 100px;
            height: 100px;
        }

        .button-pay {
            background: #1b5bd7;
            width: 220px;
            height: 44px;
            border-radius: 5px;
            border: 1px solid #1976d2;
            cursor: pointer;
            margin-top: 10px;
        }

        .back-button {
            height: 44px;
            width: 150px;
            background-color: white;
            color: rgba(255, 0, 0, 0.54);
            border: 1px solid rgba(255, 0, 0, 0.54);
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            font-weight: 600;
            margin-top: 10px;
        }

        .back-button:hover {
            background-color: rgba(251, 45, 45, 0.79); /* Nền màu hồng đậm khi rê chuột */
            color: white;
        }

        .rating-button {
            height: 44px;
            width: 150px;
            background-color: white;
            color: #f1ce2f;
            border: 1px solid #f1ce2f;
            border-radius: 5px;
            cursor: pointer;
            font-size: 18px;
            font-weight: 600;
            margin-top: 10px;
        }

        .rating-button:hover {
            background-color: #FFD966; /* Màu nền vàng đậm khi hover */
            color: #ffffff; /* Chữ trắng */
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
                    Thông tin nhận hàng
                </h5>
                <div class="text-title text-gray font-600">
                    <span>Mã đơn hàng:</span>
                    <span class="text-code"><%=order.getId()%></span>
                </div>
            </div>
            <div class="info d-flex items-center">
                <div style="margin-bottom: 15px; margin-left: 120px">
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Tên người nhận:</label>
                        <input class="text-gray editable" id="name" style="margin-left: 10px"
                               value="<%=user.getName()%>"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Số điện thoại:</label>
                        <input class="text-gray editable" id="phone-number" style="margin-left: 10px"
                               value="<%=user.getPhone()%>"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Email:</label>
                        <input class="text-gray editable" id="email" style="margin-left: 10px"
                               value="<%=user.getEmail()%>" readonly>
                    </div>
                </div>

                <div class="pd-12" style="background: #f6f8f9; margin-top: auto; color: #7a8c96; font-size: 13px">
                    Hãy kiểm tra kĩ thông tin cá nhân để chúng tôi có thể liên hệ bạn dễ dàng và nhanh chóng.
                    <span class="font-600" style="color: #5f6c73">Mã kích hoạt sản phẩm sẽ được gửi về email và số điện thoại !</span>
                </div>
            </div>
            <h5 class="font-600 text-title" style="margin-top: 20px">
                Thông tin thanh toán
            </h5>
            <div class="info transfer-info d-flex items-center" style="height: auto">
                <div style="margin-bottom: 15px; margin-left: 55px">
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">NH thụ hưởng:</label>
                        <div style="width: 200px">
                            <select id="bankSelect" class="editable" onchange="updateTransferInfo()">
                                <%
                                    for (Bank b : banks) {
                                %>
                                <option value="<%=b.getId()%>" <%= b.getId() == bank_selected.getId() ? "selected" : "" %>><%=b.getName()%>
                                </option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Chủ tài khoản:</label>
                        <span class="text-gray" id="accountHolder"><%=bank_selected.getOwner()%></span>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Số tài khoản:</label>
                        <span class="copyable text-gray" id="accountNumber"
                              onclick="copyText('accountNumber')"><%=bank_selected.getNumber()%></span>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Nội dung:</label>
                        <span class="copyable text-gray" id="description"
                              onclick="copyText('description')"><%=order.getId()%></span>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Số tiền:</label>
                        <span class="copyable text-gray" id="amount"
                              onclick="copyText('amount')"><%=formattedPrice%>đ</span>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Mã QR:</label>
                        <img src="<%=bank_selected.getQr()%>" alt="QR Code" class="qr-code">
                    </div>
                </div>
                <div class="pd-12" style="background: #f6f8f9; margin-top: auto; color: #7a8c96; font-size: 13px">
                    Vui lòng chọn NH thụ hưởng phù hợp và chuyển khoản đúng thông tin trên.
                    <span class="font-600" style="color: #5f6c73">Chúng tôi sẽ không chịu trách nhiệm nếu đơn thanh toán sai nội dung và số tiền !</span>
                </div>
            </div>

        </div>
        <div class="containersp omega">
            <div class="box-shadow-payment border-gray payment">
                <h5 class="font-600 text-title" style="margin-bottom: 15px">Đơn hàng của bạn</h5>
                <p class="font-600 upper-case text-middle">Sản phẩm</p>
                <hr class="w-full" style="height: auto; margin-top: 5px">
                <%
                    for (OrderDetail od : orderDetails) {
                        for (Product p : products) {
                            if (od.getProduct_id() == p.getId()) {
                                String price = formatter.format(p.getPrice() * od.getQuantity());
                                String nameFormat = p.getName();
                                if (nameFormat.length() > 25) {
                                    nameFormat = p.getName().substring(0, 25) + "...";
                                }
                %>
                <div class="d-flex items-center justify-between" style="margin-top: 20px; margin-bottom: 20px">
                    <div>
                        <span class="text-gray"><%=nameFormat%></span>
                        <span class="font-sz16">X<%=od.getQuantity()%></span>
                    </div>
                    <div class="font-sz16"><%=price%>đ</div>
                </div>
                <%
                            }
                        }
                    }
                %>
                <hr class="w-full" style="height: auto; margin-top: 5px">
                <div class="d-flex items-center justify-between" style="margin-top: 20px; margin-bottom: 20px">
                    <div>
                        <span class="text-gray">Số tiền cần thanh toán</span>
                    </div>
                    <div class="font-sz16"><%=formattedPrice%>đ</div>
                </div>
                <div class="d-flex items-center justify-between" style="margin-top: 20px; margin-bottom: 20px">
                    <div>
                        <span class="text-gray">Trạng thái</span>
                    </div>
                    <div class="font-sz16 font-600" id="status" style="color: rgba(255,0,0,0.7)"><%=order.getStatus()%>
                    </div>
                </div>
                <hr class="w-full" style="height: auto; margin-top: 5px">
                <div class="wrapper justify-between">
                    <form action="payment?oid=<%=order.getId()%>" method="post">
                        <input type="hidden" name="oid" value="<%=order.getId()%>"/>
                        <button type="submit" class="button-pay">
                            <span class="font-sz18 font-600" id="confirmText" style="color: white">Xác nhận</span>
                        </button>
                    </form>
                    <a href="Cart">
                        <button type="button" class="back-button" onclick="goBack()" id="btn-back">
                            <span>Hủy</span>
                        </button>
                    </a>
                </div>
                <div class="pd-12" style="background: #f6f8f9; margin-top: 20px; color: #7a8c96; font-size: 13px">
                    Thông tin cá nhân của bạn sẽ được sử dụng để xử lý đơn hàng, tăng trải nghiệm sử dụng website, và
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
        function confirmPayment() {
            if (confirm("Bạn có chắc chắn muốn xác nhận thanh toán?")) {
                document.getElementById("confirmPaymentForm").submit();
            }
        }
    </script>
    <script>
        function updateTransferInfo() {
            // Lấy giá trị ID của ngân hàng đã chọn
            var bankId = document.getElementById("bankSelect").value;

            window.location.href = "payment?oid=" + <%=order.getId()%> + "&bid=" + bankId;
        }
    </script>
</div>
</body>
</html>