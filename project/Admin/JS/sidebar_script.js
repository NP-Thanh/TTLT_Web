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
