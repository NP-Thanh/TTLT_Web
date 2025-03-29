// Toggle visibility for add product form
function toggleAddForm() {
    const form = document.getElementById('addKeyForm');
    form.classList.toggle('hidden');
}

function cancelled() {
    const form = document.getElementById('editKeyDetails');
    form.classList.add('hidden');
}

function toggleFind() {
    const form = document.getElementById('findKeyForm');
    form.classList.toggle('hidden');
}

// form chỉnh sửa chi tiết product
function editKey(button) {
    const row = button.closest('tr'); // Lấy hàng (tr) chứa nút được bấm

    document.getElementById('editKeyDetails').classList.remove('hidden'); // Hiển thị form sửa

    // Gán dữ liệu vào các input
    document.getElementById('editIDKey').value = row.getAttribute('data-id');
    document.getElementById('editNameKey').value = row.getAttribute('data-key');
    document.getElementById('editKeyName').value = row.getAttribute('data-name')
    document.getElementById('editKeyType').value = row.getAttribute('data-type');;
    document.getElementById('editKeyImage').value = row.getAttribute('data-image');

}



