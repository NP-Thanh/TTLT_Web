// Toggle visibility for add product form
function toggleAddForm() {
    const form = document.getElementById('addProductForm');
    form.classList.toggle('hidden');
}
// Toggle visibility for edit product form
function toggleEditForm() {
    const form = document.getElementById('editProductForm');
    form.classList.toggle('hidden');
}
function toggleAddKeyForm() {
    const form = document.getElementById('editKeyForm');
    form.classList.toggle('hidden');
}
function cancelled() {
    const form = document.getElementById('editProductDetails');
    form.classList.add('hidden');
}
function cancelledKey() {
    const form = document.getElementById('addKey');
    form.classList.add('hidden');
}
function toggleFind() {
    const form = document.getElementById('findProductForm');
    form.classList.toggle('hidden');
}

// form chỉnh sửa chi tiết product
function editProduct(button) {
    const row = button.closest('tr'); // Lấy hàng (tr) chứa nút được bấm

    document.getElementById('editProductDetails').classList.remove('hidden'); // Hiển thị form sửa

    // Gán dữ liệu vào các input
    document.getElementById('editIDProduct').value = row.getAttribute('data-id');
    document.getElementById('editProductType').value = row.getAttribute('data-type');
    document.getElementById('editProductName').value = row.getAttribute('data-name');
    document.getElementById('editProductPrice').value = row.getAttribute('data-price');
    document.getElementById('editProductDuration').value = row.getAttribute('data-duration');
    document.getElementById('editProductImage').value = row.getAttribute('data-image');

    // Gán dữ liệu vào CKEditor
    CKEDITOR.instances['editProDes'].setData(row.getAttribute('data-description'));
    document.getElementById('editProIntro').value = row.getAttribute('data-introduction'); // Giới thiệu
    document.getElementById('editProManu').value = row.getAttribute('data-manufacturer'); // Sản xuất
    document.getElementById('editProSupport').value = row.getAttribute('data-support'); // Hỗ trợ
}

// form nhập key
function loadProductKeyInfo(button) {
    const productId = button.getAttribute('data-id'); // Lấy ID từ button
    document.getElementById('idProductKey').value = productId; // Điền ID vào input
    document.getElementById('addKey').classList.remove('hidden'); // Hiện form nhập key
}

