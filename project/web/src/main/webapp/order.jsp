<%@ page import="vn.edu.hcmuaf.fit.web.model.*" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="org.w3c.dom.ls.LSOutput" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<%
    OrderWithUser orderWithUser = (OrderWithUser) request.getAttribute("orderWithUser");
    Order order = (Order) request.getAttribute("order");
    User user = (User) request.getAttribute("userOrder");
    Product product = (Product) request.getAttribute("product");
    OrderDetail orderDetail = (OrderDetail) request.getAttribute("orderDetail");
    Comment comment = (Comment) request.getAttribute("comment");
    List<Product> relatedProducts = (List<Product>) request.getAttribute("relatedProducts");
    DecimalFormat formatter = new DecimalFormat("#,###");
    String formattedPrice = formatter.format(product.getPrice() * orderDetail.getQuantity());
    Discount discount = (Discount) request.getAttribute("discount");
    String formattedTotal = formatter.format(order.getTotal_amount());
    if (discount != null) {
        formattedTotal = formatter.format(order.getTotal_amount()*(100-discount.getValue())/100);
    }
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hóa Đơn</title>
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
            height: auto;
            padding: 10px;
        }

        #body_page .omega {
            width: 380px;
            height: auto;
            margin-left: 30px;
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

        .font-600 {
            font-weight: 600;
        }

        .text-title {
            font-size: 20px !important;
            line-height: 120%;
            margin: 0;
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

        .text-green {
            font-size: 16px;
            color: green;
        }

        .font-sz16 {
            font-size: 16px;
        }

        .editable {
            outline: none;
            border: none;
            background: transparent;
            width: 100%;
            height: 20px;
        }

        .product-editable {
            background: transparent;
            width: 100%;
            height: 20px;
        }

        #body_page .rate {
            width: 100%;
            min-height: 300px;
            padding: 15px;
            padding-top: 30px !important;
        }

        .box-shadow {
            box-shadow: 0 1px 1px rgba(0, 0, 0, .25), 0 2px 4px rgba(5, 27, 68, .08) !important;
        }

        .border-gray {
            border: 1px solid #e5e7eb;
            border-radius: 5px;
        }

        .status-paid {
            color: green;
            font-weight: bold;
        }

        .rating {
            display: flex;
            gap: 5px;
            margin-bottom: 15px;
        }

        .star {
            font-size: 24px;
            cursor: pointer;
            color: #b1b1b1;
            transition: color 0.3s;
        }

        .star-rating {
            font-size: 24px;
            color: #ffe250;
        }

        .star.selected {
            color: gold;
        }

        .review-box {
            width: 94%;
            height: 100px;
            border: 2px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 15px;
            font-size: 14px;
            resize: none;
            font-family: Arial;
        }

        .review-box::placeholder {
            font-family: Arial;
        }

        .submit-button {
            background: #1b5bd7;
            min-width: 100px;
            height: 44px;
            width: auto;
            border-radius: 5px;
            border: 1px solid #1976d2;
            cursor: pointer;
            margin-top: 10px;
            color: white;
        }

        .submit-button:hover {
            background-color: #0056b3;
        }

        .font-sz18 {
            font-size: 18px;
        }

        .pd-12 {
            padding: 12px;
        }

        .related-products {
            margin-top: 20px;
        }

        .products-grid {
            display: flex;
            gap: 15px;
            justify-content: flex-start;
            flex-wrap: wrap;
            padding: 15px;
        }

        .product {
            width: 120px;
            text-align: center;
            cursor: pointer;
            transition: transform 0.3s;
            box-shadow: 0 1px 1px rgba(0, 0, 0, .25), 0 2px 4px rgba(5, 27, 68, .08);
            padding: 10px;
            border-radius: 5px;
            margin-left: 5px;
        }

        .product:hover {
            transform: scale(1.1);
        }

        .product-image {
            width: 100%;
            height: auto;
            border-radius: 5px;
            margin-bottom: 10px;
        }

        .product-name {
            font-size: 14px;
            color: #404040;
            font-weight: 600;
            margin: 5px 0;
            height: auto;
        }

        .product-price {
            font-size: 14px;
            color: #6e7c87;
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
                    Hóa đơn
                </h5>
                <div class="text-title text-gray font-600">
                    <span>Mã đơn hàng:</span>
                    <span class="text-code"><%=order.getId()%></span>
                </div>
            </div>
            <div class="info d-flex items-center">
                <div style="margin-bottom: 15px; margin-left: 139px">
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
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Sản phẩm:</label>
                        <select class="text-gray product-editable" id="product" style="margin-left: 10px"
                                onchange="updateProductURL()">
                            <% for (Product p : orderWithUser.getProduct()) { %>
                            <option value="<%=p.getId()%>"
                                    <%=p.getId() == product.getId() ? "selected" : ""%>>
                                <%=p.getName()%>
                            </option>
                            <% } %>
                        </select>
                    </div>

                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Số lượng:</label>
                        <input class="text-gray editable" id="quality" style="margin-left: 10px"
                               value="x<%=orderDetail.getQuantity()%>"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Giá:</label>
                        <input class="text-gray editable" id="price" style="margin-left: 10px"
                               value="<%=formattedPrice%>đ"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Ngày giao dịch:</label>
                        <input class="text-gray editable" id="date" style="margin-left: 10px"
                               value="<%=order.getDate()%>"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Tổng tiền:</label>
                        <input class="text-gray editable" id="cost" style="margin-left: 10px"
                               value="<%=formattedTotal%>đ"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Thanh toán:</label>
                        <input class="text-gray editable" id="bank" style="margin-left: 10px" value="Momo"
                               readonly>
                    </div>
                    <div class="d-flex info-item">
                        <label class="font-600 font-sz16">Trạng thái:</label>
                        <input class="text-green font-600 editable" id="status" style="margin-left: 10px"
                               value="<%=order.getStatus()%>"
                               readonly>
                    </div>
                </div>
                <div class="pd-12" style="background: #f6f8f9; margin-top: auto; color: #7a8c96; font-size: 13px;
                    width: 97%; margin-left: auto; margin-right: auto;">
                    Cảm ơn bạn đã sử dụng dịch vụ, nếu có vấn đề thắc mắc hãy
                    <span class="font-600" style="color: #5f6c73; cursor: pointer" onclick="window.location.href='#'">liên hệ chúng tôi.</span>
                </div>
            </div>
            <div class="related-products">
                <h2 class="font-600 text-title" style="margin-bottom: 15px">Sản phẩm liên quan</h2>
                <div class="products-grid">
                    <%
                        for (Product related : relatedProducts) {
                            formattedPrice = formatter.format(related.getPrice());

                    %>
                    <div class="product" onclick="window.location.href='ProductDetail?id=<%=related.getId()%>'">
                        <img src="<%=related.getImage()%>" alt="Sản phẩm" class="product-image">
                        <p class="product-name"><%=related.getName()%>
                        </p>
                        <p class="product-price"><%=formattedPrice%>đ</p>
                    </div>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
        <div class="containersp omega">
            <%
                if (comment != null) {
            %>
            <div class="box-shadow border-gray rate">
                <h2 class="font-600 text-title" style="margin-bottom: 10px">Đánh Giá</h2>
                <div class="d-flex rating">
                    <%
                        for (int i = 1; i <= comment.getNum_rate(); i++) {
                    %>
                    <span class="star-rating">&#9733;</span>
                    <%
                        }
                    %>
                </div>
                <textarea class="review-box" readonly><%=comment.getComment()%></textarea>
                <a href="Comment?id=<%=product.getId()%>" style="text-decoration: none">
                    <button class="font-sz18 font-600 submit-button"
                            style="border: 1px solid #ffe984; background: #ffe250">Xem đánh giá
                    </button>
                </a>
                <div class="pd-12" style="background: #f6f8f9; margin-top: 20px; color: #7a8c96; font-size: 13px">
                    Hãy trải nghiệm sản phẩm 3-5 ngày trước khi gửi đánh giá.
                </div>
            </div>
            <%
            } else {
            %>
            <div class="box-shadow border-gray rate">
                <h2 class="font-600 text-title" style="margin-bottom: 10px">Đánh Giá</h2>
                <div class="d-flex rating">
                    <span class="star" style="cursor: pointer; transition: color 0.3s ease;"
                          data-value="1">&#9733;</span>
                    <span class="star" style="cursor: pointer; transition: color 0.3s ease;"
                          data-value="2">&#9733;</span>
                    <span class="star" style="cursor: pointer; transition: color 0.3s ease;"
                          data-value="3">&#9733;</span>
                    <span class="star" style="cursor: pointer; transition: color 0.3s ease;"
                          data-value="4">&#9733;</span>
                    <span class="star" style="cursor: pointer; transition: color 0.3s ease;"
                          data-value="5">&#9733;</span>
                </div>
                <textarea class="review-box" placeholder="Viết đánh giá của bạn..."></textarea>
                <button id="submit" class="font-sz18 font-600 submit-button">Gửi</button>
                <div class="pd-12" style="background: #f6f8f9; margin-top: 20px; color: #7a8c96; font-size: 13px">
                    Hãy trải nghiệm sản phẩm 3-5 ngày trước khi gửi đánh giá.
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    <div class="iframe-footer">
        <jsp:include page="footer.jsp"/>
    </div>
    <script>
        function updateProductURL() {
            const productId = document.getElementById('product').value;
            const url = new URL(window.location.href);
            url.searchParams.set('pid', productId); // Thay đổi giá trị 'pid'
            window.location.href = url.toString(); // Tải lại trang với URL mới
        }
    </script>
    <script>
        let numRate = null;
        let comment = '';

        // Xử lý chọn sao
        document.querySelectorAll('.star').forEach(star => {
            star.addEventListener('click', function () {
                document.querySelectorAll('.star').forEach(s => s.classList.remove('selected'));
                const value = this.getAttribute('data-value');
                document.querySelectorAll('.star').forEach(s => {
                    if (s.getAttribute('data-value') <= value) {
                        s.classList.add('selected');
                    }
                });
                numRate = value;
            });
        });

        // Gửi đánh giá
        document.querySelector('#submit').addEventListener('click', function () {
            comment = document.querySelector('.review-box').value;

            // Kiểm tra dữ liệu hợp lệ
            if (!numRate) {
                alert('Vui lòng chọn số sao.');
                return;
            }
            if (comment === '') {
                alert('Vui lòng nhập bình luận.');
                return;
            }

            // Chuẩn bị dữ liệu để gửi
            const requestData = {
                order_id: '<%= order.getId() %>', // Giá trị này cần được render trước khi gửi
                product_id: '<%= product.getId() %>',
                user_id: '<%= user.getId() %>',
                num_rate: numRate,
                comment: comment
            };

            // Gửi yêu cầu AJAX (POST) đến server
            fetch('/web/order', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json' // Đảm bảo server hiểu dữ liệu dạng JSON
                },
                body: JSON.stringify(requestData) // Chuyển đổi dữ liệu thành chuỗi JSON
            })
                .then(response => response.json()) // Giả sử server trả về JSON
                .then(result => {
                    if (result.success) {
                        alert('Đánh giá của bạn đã được gửi!');
                        // Load trang
                        window.location.href = window.location.href;
                    } else {
                        alert('Gửi đánh giá thất bại.');
                    }
                })
                .catch(error => {
                    console.error('Lỗi:', error);
                    alert('Đã xảy ra lỗi khi gửi đánh giá.');
                });
        });

    </script>
</div>
</body>
</html>
