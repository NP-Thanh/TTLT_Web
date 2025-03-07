
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

// Hiển thị thông tin ngân hàng cần sửa
function loadBankInfo() {
    const id = document.getElementById("editBankId").value;
    const rows = document.querySelectorAll('#bankTable tr');

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        if (cells[0] && cells[0].innerText === id) {
            document.getElementById('editBankDetails').classList.remove('hidden');
            document.getElementById('editBID').value = id; // Product ID
            document.getElementById('editBankName').value = cells[1].innerText; // Product Name
            document.getElementById('editBankNum').value = cells[2].innerText; // Product Type
            document.getElementById('editBankOwn').value = cells[3].innerText; // Product Price
            document.getElementById('editBankImage').value = cells[4].querySelector('img').src; // Product Image

            return;
        }

    }
    alert('Không tìm thấy ngân hàng với mã này!');
}

