<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/dashboard_style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        html, body, p {
            width: 100%;
            margin: 0;
            padding: 0;
            font-size: 13px;
            color: #404040;
            font-family: Arial, Helvetica, sans-serif, Inter;
            height: 100%;
        }

        .admin-container {
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: #fff;
            padding: 15px;
        }

        .content {
            flex: 1;
            margin-left: 250px;
            padding: 20px;
        }

        header h1 {
            font-size: 24px;
            color: #34495e;
            margin-bottom: 20px;
        }

        .chart-container {
            width: 100%;
            max-width: 800px;
            margin: 0 auto 30px;
        }

        #revenueChart {
            background-color: #f8f9fa;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
        }

        .top-products h2 {
            margin-bottom: 15px;
        }

        .products-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-left: 15px;
        }

        .product-card {
            width: 150px;
            border: 1px solid #ddd;
            border-radius: 8px;
            overflow: hidden;
            text-align: center;
            background-color: #f9f9f9;
        }

        .product-card img {
            width: 100%;
            height: 150px;
            object-fit: cover;
        }

        .product-card h3 {
            font-size: 16px;
            margin: 10px 0 5px;
            color: #2c3e50;
        }

        .product-card p {
            font-size: 14px;
            color: #777;
            margin-top: auto;
            margin-bottom: 10px;
        }

        .block-container {
            display: flex;
            gap: 20px;
        }
        .stat-block {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 200px;
            text-align: center;
            transition: transform 0.3s ease;
        }
        .stat-block:hover {
            transform: translateY(-5px);
        }
        .stat-title {
            font-size: 16px;
            color: #666;
            margin-bottom: 10px;
        }
        .stat-value {
            font-size: 24px;
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>
<div class="admin-container">
    <div class="slidebar">
        <jsp:include page="slibar.jsp"/>
    </div>

    <div class="content">
        <div class="revenue">
            <div class="block-container">
                <div class="stat-block">
                    <div class="stat-title">Số lượng khách hàng</div>
                    <div class="stat-value">${userCount}</div>
                </div>
                <div class="stat-block">
                    <div class="stat-title">Số lượng đơn hàng</div>
                    <div class="stat-value">${orderCount}</div>
                </div>
                <div class="stat-block">
                    <div class="stat-title">Tổng doanh thu</div>
                    <div class="stat-value">${totalRevenue}</div>
                </div>
                <div class="stat-block">
                    <div class="stat-title">Số lượng sản phẩm</div>
                    <div class="stat-value">${productCount}</div>
                </div>
            </div>
            <h2>Doanh thu</h2>
            <!-- Phần sắp xếp -->
            <div class="sorting" style="margin-bottom: 15px">

            </div>
        </div>
        <!-- Top sản phẩm bán chạy -->
        <%--        <div class="top-products">--%>
        <%--            <h2>Top sản phẩm bán chạy</h2>--%>
        <%--            <div class="products-sorting" style="margin-bottom: 15px">--%>
        <%--                <label for="productTimeRange" style="font-size: 14px; margin-right: 5px">Sắp xếp:</label>--%>
        <%--                <select id="productTimeRange">--%>
        <%--                    <option value="1day">1 ngày</option>--%>
        <%--                    <option value="7days">7 ngày</option>--%>
        <%--                    <option value="1month">1 tháng</option>--%>
        <%--                    <option value="1year">1 năm</option>--%>
        <%--                </select>--%>
        <%--            </div>--%>
        <%--            <div class="products-grid">--%>
        <%--                <c:forEach var="product" items="${topSellingProducts}">--%>
        <%--                    <div class="product-card">--%>
        <%--                        <img src="${pageContext.request.contextPath}/Images/Product/${product.image}">--%>
        <%--                        <h3>${product.name}</h3>--%>
        <%--                        <p>${product.sales} lượt bán</p>--%>
        <%--                    </div>--%>
        <%--                </c:forEach>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <%
            List<Integer> orderCounts = (List<Integer>) request.getAttribute("data");
        %>
        <div>
            <canvas id="myChart" style="max-height: 500px;"></canvas>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            const ctx = document.getElementById('myChart');

            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
                    datasets: [{
                        label: 'Đơn hàng theo tháng',
                        data: <%= orderCounts.toString() %>,
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        </script>
    </div>
</div>
<%--<script>--%>
<%--    document.addEventListener("DOMContentLoaded", function () {--%>
<%--        const sidebarPlaceholder = document.getElementById("sidebar-placeholder");--%>

<%--        if (sidebarPlaceholder) {--%>
<%--            // Tải sidebar từ file sidebar.html--%>
<%--            fetch('sidebar.html')--%>
<%--                .then(response => response.text())--%>
<%--                .then(data => {--%>
<%--                    // Chèn nội dung vào placeholder--%>
<%--                    sidebarPlaceholder.innerHTML = data;--%>

<%--                    // Lấy tất cả liên kết trong sidebar--%>
<%--                    const links = sidebarPlaceholder.querySelectorAll(".sidebar ul li a");--%>

<%--                    // Đặt active dựa trên URL hiện tại--%>
<%--                    const currentPath = window.location.pathname.split("/").pop(); // Lấy tên file hiện tại--%>
<%--                    links.forEach(link => {--%>
<%--                        const linkPath = link.getAttribute("href").split("/").pop(); // Lấy tên file trong href--%>
<%--                        if (linkPath === currentPath) {--%>
<%--                            link.classList.add("active");--%>
<%--                        }--%>
<%--                    });--%>

<%--                    // Lắng nghe sự kiện click để đổi màu khi bấm--%>
<%--                    links.forEach(link => {--%>
<%--                        link.addEventListener("click", function () {--%>
<%--                            // Xóa lớp active khỏi tất cả các liên kết--%>
<%--                            links.forEach(l => l.classList.remove("active"));--%>

<%--                            // Thêm lớp active vào liên kết được bấm--%>
<%--                            this.classList.add("active");--%>
<%--                        });--%>
<%--                    });--%>
<%--                })--%>
<%--                .catch(error => console.error('Lỗi khi tải sidebar:', error));--%>
<%--        }--%>
<%--    });--%>

<%--</script>--%>
</body>
</html>