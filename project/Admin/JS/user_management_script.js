// Dữ liệu mẫu
const users = [
    {
        id: "KH001",
        name: "Phúc Thạnh",
        email: "22130259@st.hcmuaf.edu.vn",
        phone: "0826661039",
        orders: 5,
        status: "active",
        emailVerified: true,
        phoneVerified: true
    },
    {
        id: "KH002",
        name: "Quốc Thái",
        email: "22130250@st.hcmuaf.edu.vn",
        phone: "",
        orders: 8,
        status: "active",
        emailVerified: true,
        phoneVerified: false
    },
    {
        id: "KH003",
        name: "Thanh Sơn",
        email: "22130237@st.hcmùa.edu.vn",
        phone: "0123456789",
        orders: 6,
        status: "restricted",
        emailVerified: true,
        phoneVerified: true
    },
];

// Render bảng dữ liệu
function renderTable(data) {
    const tableBody = document.getElementById("userTable");
    tableBody.innerHTML = "";

    data.forEach(user => {
        const row = `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.phone || "N/A"}</td>
                <td class="font-600" style="color: green">${user.orders}</td>
                <td class="font-600" style="color: ${user.status === 'active' ? 'green' : 'red'}">${user.status === "active" ? "Hoạt động" : "Hạn chế"}</td>
            </tr>
        `;
        tableBody.insertAdjacentHTML("beforeend", row);
    });
}

// Áp dụng bộ lọc
function applyFilters() {
    let filteredUsers = [...users];

    // Bộ lọc Mã khách hàng
    const filterID = document.getElementById("filterID").value;
    if (filterID === "asc") {
        filteredUsers.sort((a, b) => a.id.localeCompare(b.id));
    } else if (filterID === "desc") {
        filteredUsers.sort((a, b) => b.id.localeCompare(a.id));
    }

    // Bộ lọc Email
    const filterEmail = document.getElementById("filterEmail").value;
    if (filterEmail === "verified") {
        filteredUsers = filteredUsers.filter(user => user.emailVerified);
    } else if (filterEmail === "unverified") {
        filteredUsers = filteredUsers.filter(user => !user.emailVerified);
    }

    // Bộ lọc SĐT
    const filterPhone = document.getElementById("filterPhone").value;
    if (filterPhone === "verified") {
        filteredUsers = filteredUsers.filter(user => user.phoneVerified);
    } else if (filterPhone === "unverified") {
        filteredUsers = filteredUsers.filter(user => !user.phoneVerified);
    }

    // Bộ lọc Đơn hàng
    const filterOrders = document.getElementById("filterOrders").value;
    if (filterOrders === "asc") {
        filteredUsers.sort((a, b) => a.orders - b.orders);
    } else if (filterOrders === "desc") {
        filteredUsers.sort((a, b) => b.orders - a.orders);
    }

    // Bộ lọc Trạng thái
    const filterStatus = document.getElementById("filterStatus").value;
    if (filterStatus) {
        filteredUsers = filteredUsers.filter(user => user.status === filterStatus);
    }

    // Hiển thị lại bảng
    renderTable(filteredUsers);
}

function resetFilters() {
    let filteredUsers = [...users];
    document.getElementById("filterID").value = "";
    document.getElementById("filterEmail").value = "";
    document.getElementById("filterPhone").value = "";
    document.getElementById("filterOrders").value = "";
    document.getElementById("filterStatus").value = "";

    renderTable(filteredUsers);
}

// Khởi tạo
renderTable(users);
