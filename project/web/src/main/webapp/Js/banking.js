
// Hiển thị hoặc ẩn form thêm ngân hàng
function toggleAddForm() {
    const form = document.getElementById("addBankForm");
    form.classList.toggle("hidden");
}

function editBank(button) {
    const row = button.closest('tr'); // Lấy hàng (tr) chứa nút được bấm

    document.getElementById('editBankDetails').classList.remove('hidden'); // Hiển thị form sửa

    // Gán dữ liệu vào các input
    document.getElementById('editBID').value = row.getAttribute('data-id');
    document.getElementById('editBankName').value = row.getAttribute('data-name');
    document.getElementById('editBankNum').value = row.getAttribute('data-num')
    document.getElementById('editBankOwn').value = row.getAttribute('data-own');
    document.getElementById('editBankImage').value = row.getAttribute('data-image');
    console.log("Form cập nhật đã hiện và dữ liệu đã gán.");
}

function cancelled() {
    const form = document.getElementById('editBankDetails');
    form.classList.add('hidden');
}