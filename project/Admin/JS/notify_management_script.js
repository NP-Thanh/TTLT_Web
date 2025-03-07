const notify = [
    {
        id: "TB001",
        type: "Sản phẩm mới",
        title: "Về hàng key Office 2021",
        content: "Key office 2021 vừa cập nhật số lượng, hãy nhanh tay mua!",
        user: "Tất cả",
        date: new Date("2024-11-20T17:05:00")
    },
    {
        id: "TB002",
        type: "Bảo mật tài khoản",
        title: "Xác thực tài khoản",
        content: "Hãy xác thực email và số điện thoại để sử dụng dịch vụ tốt nhất!",
        user: "Hạn chế",
        date: new Date("2024-09-10T16:45:00")
    }
];

// Hiển thị hoặc ẩn form thông báo
function toggleNotificationForm() {
    const form = document.getElementById("notificationForm");
    form.classList.toggle("hidden");
}

// Gửi thông báo mới
function sendNotification() {
    const customer = document.getElementById("customerSelect").value;
    const type = document.getElementById("notificationType").value;
    const title = document.getElementById("notificationTitle").value.trim();
    const content = document.getElementById("notificationContent").value.trim();

    if (!title || !content) {
        alert("Vui lòng điền đầy đủ tiêu đề và nội dung.");
        return;
    }

    const newNotification = {
        id: `TB${String(notify.length + 1).padStart(3, '0')}`,
        type: type === "order" ? "Đơn hàng" :
            type === "newProduct" ? "Sản phẩm mới" :
                type === "accountSecurity" ? "Bảo mật tài khoản" : "Khuyến mãi",
        title: title,
        content: content,
        user: customer === "all" ? "Tất cả" : customer === "active" ? "Hoạt động" : "Hạn chế",
        date: new Date()
    };

    notify.push(newNotification);
    renderTable();
    alert("Thông báo đã gửi thành công!");
    document.getElementById("notificationForm").reset();
    toggleNotificationForm();
}

// Lọc và sắp xếp thông báo
function applyFilters() {
    let filteredNotifications = [...notify];

    // Bộ lọc ID thông báo (tăng dần hoặc giảm dần)
    const filterID = document.getElementById("filterID").value;
    if (filterID === "asc") {
        filteredNotifications.sort((a, b) => a.id.localeCompare(b.id));
    } else if (filterID === "desc") {
        filteredNotifications.sort((a, b) => b.id.localeCompare(a.id));
    }

    // Bộ lọc Loại thông báo (type)
    const filterType = document.getElementById("filterType").value.trim().toLowerCase();
    if (filterType) {
        filteredNotifications = filteredNotifications.filter(notify =>
            // So sánh với các giá trị trong notify.type
            notify.type.toLowerCase().includes(filterType === "order" ? "đơn hàng" :
                filterType === "product" ? "sản phẩm mới" :
                    filterType === "account" ? "bảo mật tài khoản" : "khuyến mãi")
        );
    }

    // Bộ lọc Đối tượng nhận thông báo (user)
    const filterUser = document.getElementById("filterUser").value.trim().toLowerCase();
    if (filterUser) {
        filteredNotifications = filteredNotifications.filter(notification =>
            notification.user.toLowerCase().includes(filterUser === "all" ? "tất cả" :
                filterUser === "active" ? "hoạt động" : "hạn chế")
        );
    }

    // Bộ lọc theo ngày thông báo (tăng dần hoặc giảm dần)
    const filterDate = document.getElementById("filterDate").value;
    if (filterDate === "asc") {
        filteredNotifications.sort((a, b) => a.date - b.date);
    } else if (filterDate === "desc") {
        filteredNotifications.sort((a, b) => b.date - a.date);
    }

    // Hiển thị lại bảng thông báo đã lọc
    renderTable(filteredNotifications);
}




// Reset bộ lọc
function resetFilters() {
    document.getElementById("filterType").value = "";
    document.getElementById("filterUser").value = "";
    document.getElementById("filterDate").value = "";
    renderTable();
}

// Hiển thị bảng dữ liệu
function renderTable(data = notify) {
    const tableBody = document.getElementById("userTable");
    tableBody.innerHTML = "";

    data.forEach(n => {
        const row = `
            <tr>
                <td>${n.id}</td>
                <td>${n.type}</td>
                <td>${n.title}</td>
                <td>${n.content}</td>
                <td>${n.user}</td>
                <td>${n.date.toLocaleString("vi-VN", {
            hour: '2-digit', minute: '2-digit',
            day: '2-digit', month: '2-digit', year: 'numeric'
        })}</td>
            </tr>
        `;
        tableBody.insertAdjacentHTML("beforeend", row);
    });
}

// Render dữ liệu ban đầu
renderTable();
