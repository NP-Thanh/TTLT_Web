// Dữ liệu ngân hàng (dùng để minh họa)
let banks = [
    { id: "NH001", name: "Momo", num: "0123456789", owner: "Nguyễn Phúc Thạnh", qr: "../../Images/QRCode.png" },
    { id: "NH002", name: "Bidv", num: "1237894560", owner: "Lê Quốc Thái", qr: "../../Images/QRCode.png" },
    { id: "NH003", name: "Sacombank", num: "22130237111", owner: "Nguyễn Thanh Sơn", qr: "../../Images/QRCode.png" }
];

// Hiển thị hoặc ẩn form thêm ngân hàng
function toggleAddForm() {
    const form = document.getElementById("addBankForm");
    form.classList.toggle("hidden");
}

// Hiển thị hoặc ẩn form sửa thông tin
function toggleEditForm() {
    const form = document.getElementById("editBankForm");
    form.classList.toggle("hidden");
    document.getElementById("editBankDetails").classList.add("hidden");
}

// Thêm ngân hàng mới vào danh sách
function addBank() {
    const id = document.getElementById("addBankId").value.trim();
    const name = document.getElementById("addBankName").value.trim();
    const num = document.getElementById("addBankNum").value.trim();
    const owner = document.getElementById("addBankOwn").value.trim();
    const qr = document.getElementById("addBankImage").value.trim();

    if (banks.find(bank => bank.id === id)) {
        alert("Mã ngân hàng đã tồn tại!");
        return;
    }

    banks.push({ id, name, num, owner, qr });
    renderTable();
    document.getElementById("addBankForm").reset();
    toggleAddForm();
}

// Hiển thị thông tin ngân hàng cần sửa
function loadBankInfo() {
    const id = document.getElementById("editBankId").value.trim();
    const bank = banks.find(bank => bank.id === id);

    if (!bank) {
        alert("Không tìm thấy ngân hàng với mã đã nhập!");
        return;
    }

    document.getElementById("editBankName").value = bank.name;
    document.getElementById("editBankNum").value = bank.num;
    document.getElementById("editBankOwn").value = bank.owner;
    document.getElementById("editBankImage").value = bank.qr;

    document.getElementById("editBankDetails").classList.remove("hidden");
}

// Lưu thay đổi thông tin ngân hàng
function saveBank() {
    const id = document.getElementById("editBankId").value.trim();
    const bank = banks.find(bank => bank.id === id);

    if (bank) {
        bank.name = document.getElementById("editBankName").value.trim();
        bank.num = document.getElementById("editBankNum").value.trim();
        bank.owner = document.getElementById("editBankOwn").value.trim();
        bank.qr = document.getElementById("editBankImage").value.trim();

        alert("Thông tin ngân hàng đã được cập nhật!");
        renderTable();
    } else {
        alert("Không tìm thấy ngân hàng với mã đã nhập!");
    }
}

// Xóa ngân hàng khỏi danh sách
function deleteBank() {
    const id = document.getElementById("editBankId").value.trim();
    const index = banks.findIndex(bank => bank.id === id);

    if (index > -1) {
        banks.splice(index, 1);
        alert("Ngân hàng đã được xóa!");
        renderTable();
        toggleEditForm();
    } else {
        alert("Không tìm thấy ngân hàng với mã đã nhập!");
    }
}

// Cập nhật lại bảng thông tin ngân hàng
function renderTable() {
    const tableBody = document.getElementById("bankTable");
    tableBody.innerHTML = "";

    banks.forEach(bank => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${bank.id}</td>
            <td>${bank.name}</td>
            <td>${bank.num}</td>
            <td>${bank.owner}</td>
            <td><img src="${bank.qr}" style="width: 50px; height: 50px;"></td>
        `;
        tableBody.appendChild(row);
    });
}

// Khởi tạo bảng khi tải trang
renderTable();
