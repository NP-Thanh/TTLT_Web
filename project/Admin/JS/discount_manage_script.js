// Hàm mở/đóng form thêm mã
function toggleAddForm() {
    const form = document.getElementById("addDiscountForm");
    form.classList.toggle("hidden");
}

// Hàm mở/đóng form sửa thông tin
function toggleEditForm() {
    const form = document.getElementById("editDiscountForm");
    form.classList.toggle("hidden");
}

// Hàm thêm mã giảm giá
function addDiscount() {
    const discountId = document.getElementById("addDiscountId").value;
    const discountNum = document.getElementById("addDiscountNum").value;
    const discountContent = document.getElementById("addDiscountContent").value;
    const discountDays = document.getElementById("addDiscountDate").value;

    if (!discountId || !discountNum || !discountContent || !discountDays) {
        alert("Vui lòng điền đầy đủ thông tin.");
        return;
    }

    // Kiểm tra mã giảm giá có trùng không
    const tables = document.getElementById("discountTable");
    const rows = tables.getElementsByTagName("tr");
    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        if (cells[0] && cells[0].textContent === discountId) {
            alert("Mã giảm giá đã tồn tại. Vui lòng nhập mã khác.");
            return;
        }
    }

    // Lấy ngày giờ hiện tại
    const currentDate = new Date();
    const formattedCreationDate = formatDate(currentDate);

    // Tính toán ngày hạn dựa trên số ngày được chọn
    currentDate.setDate(currentDate.getDate() + parseInt(discountDays));
    const formattedExpiryDate = formatDate(currentDate);

    // Thêm hàng vào bảng
    const table = document.getElementById("discountTable");
    const row = table.insertRow();
    row.innerHTML = `
        <td>${discountId}</td>
        <td>${discountNum}</td>
        <td>${discountContent}</td>
        <td>${formattedCreationDate}</td>
        <td>${formattedExpiryDate}</td>
        <td class="${discountNum > 0 ? 'green' : 'red'} font-600">${discountNum > 0 ? 'Còn mã' : 'Hết'}</td>
    `;

    // Ẩn form sau khi thêm mã
    document.getElementById("addDiscountForm").classList.add("hidden");
    clearForm();
}

// Hàm định dạng ngày theo "HH:mm dd/MM/yyyy"
function formatDate(date) {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();
    return `${hours}:${minutes} ${day}/${month}/${year}`;
}

// Hàm xóa dữ liệu trong form sau khi lưu
function clearForm() {
    document.getElementById("addDiscountId").value = '';
    document.getElementById("addDiscountNum").value = '';
    document.getElementById("addDiscountContent").value = '';
    document.getElementById("addDiscountDate").value = '';
}

// Hàm tải thông tin mã giảm giá để sửa (giả sử tìm theo mã)
function loadDiscountInfo() {
    const discountId = document.getElementById("editDiscountId").value;
    const table = document.getElementById("discountTable");
    const rows = table.getElementsByTagName("tr");
    let found = false;

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        if (cells[0] && cells[0].textContent === discountId) {
            document.getElementById("editDiscountNum").value = cells[1].textContent;
            document.getElementById("editDiscountDetails").classList.remove("hidden");
            found = true;
            break;
        }
    }

    if (!found) {
        alert("Không tìm thấy mã giảm giá.");
    }
}

// Hàm lưu thông tin sửa đổi
function saveDiscount() {
    const discountId = document.getElementById("editDiscountId").value;
    const newNum = document.getElementById("editDiscountNum").value;

    const table = document.getElementById("discountTable");
    const rows = table.getElementsByTagName("tr");

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        if (cells[0] && cells[0].textContent === discountId) {
            cells[1].textContent = newNum;
            cells[5].textContent = newNum > 0 ? 'Còn mã' : 'Hết';
            cells[5].className = newNum > 0 ? 'green font-600' : 'red font-600';
            alert("Cập nhật thành công.");
            document.getElementById("editDiscountDetails").classList.add("hidden");
            return;
        }
    }

    alert("Không tìm thấy mã để sửa.");
}

// Hàm xóa mã giảm giá
function deleteDiscount() {
    const discountId = document.getElementById("editDiscountId").value;
    const table = document.getElementById("discountTable");
    const rows = table.getElementsByTagName("tr");

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName("td");
        if (cells[0] && cells[0].textContent === discountId) {
            table.deleteRow(i);
            alert("Xóa mã thành công.");
            document.getElementById("editDiscountDetails").classList.add("hidden");
            return;
        }
    }

    alert("Không tìm thấy mã để xóa.");
}
