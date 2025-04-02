<%--
  Created by IntelliJ IDEA.
  User: THAI
  Date: 1/14/2025
  Time: 1:35 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Side bar</title>
    <style>
        /* Sidebar */
        html, body, p {
            width: 100%;
            margin: 0;
            padding: 0;
            font-size: 13px;
            color: #404040;
            font-family: Arial, Helvetica, sans-serif, Inter;
            height: 100%;
        }

        .sidebar {
            width: 200px;
            background-color: #333;
            color: #fff;
            padding: 15px;
            height: 100vh;
            position: fixed;
        }

        .sidebar h2 {
            font-size: 20px;
            text-align: center;
            margin: 0px;
        }

        .sidebar ul {
            list-style: none;
            padding: 0;
        }

        .sidebar ul li {
            margin-bottom: 15px;
        }

        .sidebar ul li a {
            color: #fff;
            text-decoration: none;
            font-size: 16px;
            display: block;
            padding: 10px;
            border-radius: 4px;
        }

        .sidebar ul li a.active{
            background-color: #575757 !important;
        }

        .sidebar ul li a:hover {
            background-color: #575757;
        }

    </style>
</head>
<body>
<div class="sidebar">
    <h2>Admin</h2>
    <ul>
        <li><a href="dashboard">Dashboard</a></li>
        <li><a href="ProductManagement">Quản lý sản phẩm</a></li>
        <li><a href="orderManagement">Quản lý đơn hàng</a></li>
        <li><a href="user">Quản lý khách hàng</a></li>
        <li><a href="bank">Quản lý thông tin chuyển khoản</a></li>
        <li><a href="discounts">Quản lý mã giảm giá</a></li>
        <li><a href="logs">Lịch sử log</a></li>
        <li><a href="KeyManagement">Quản lý kho Key</a></li>
        <li><a href="logout" style="color: #ff2c2c">Đăng xuất</a></li>
    </ul>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const sidebarPlaceholder = document.getElementById("sidebar-placeholder");

        if (sidebarPlaceholder) {
            // Tải sidebar từ file sidebar.html
            fetch('../HTML/sidebar.html')
                .then(response => response.text())
                .then(data => {
                    // Chèn nội dung vào placeholder
                    sidebarPlaceholder.innerHTML = data;

                    // Lấy tất cả liên kết trong sidebar
                    const links = sidebarPlaceholder.querySelectorAll(".sidebar ul li a");

                    // Đặt active dựa trên URL hiện tại
                    const currentPath = window.location.pathname.split("/").pop(); // Lấy tên file hiện tại
                    links.forEach(link => {
                        const linkPath = link.getAttribute("href").split("/").pop(); // Lấy tên file trong href
                        if (linkPath === currentPath) {
                            link.classList.add("active");
                        }
                    });

                    // Lắng nghe sự kiện click để đổi màu khi bấm
                    links.forEach(link => {
                        link.addEventListener("click", function () {
                            // Xóa lớp active khỏi tất cả các liên kết
                            links.forEach(l => l.classList.remove("active"));

                            // Thêm lớp active vào liên kết được bấm
                            this.classList.add("active");
                        });
                    });
                })
                .catch(error => console.error('Lỗi khi tải sidebar:', error));
        }
    });

</script>
</body>
</html>
