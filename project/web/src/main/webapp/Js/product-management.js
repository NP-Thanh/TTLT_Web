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
function toggleFind() {
    const form = document.getElementById('findProductForm');
    form.classList.toggle('hidden');
}


// Load product info for editing
function loadProductInfo() {
    const productId = document.getElementById('editProductId').value; // ID của sản phẩm cần chỉnh sửa
    const rows = document.querySelectorAll('#productTable tr');

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        if (cells[0] && cells[0].innerText === productId) {
            document.getElementById('editProductDetails').classList.remove('hidden'); // Hiện form sửa sản phẩm
            // Điền giá trị vào form
            document.getElementById('editIDProduct').value = productId; // Tên sản phẩm
            document.getElementById('editProductType').value = cells[1].innerText; // Loại sản phẩm
            document.getElementById('editProductName').value = cells[2].innerText; // Tên sản phẩm
            document.getElementById('editProductPrice').value = cells[3].innerText; // Giá sản phẩm
            document.getElementById('editProductDuration').value = cells[4].innerText; // Thời hạn
            document.getElementById('editProductImage').value = cells[6].querySelector('img').src; // Hình ảnh (URL của ảnh
            // Lấy dữ liệu từ data-* attributes
            CKEDITOR.instances['editProDes'].setData(rows[i].dataset.description); // Mô tả
            document.getElementById('editProIntro').value = rows[i].dataset.introduction; // Giới thiệu
            document.getElementById('editProManu').value = rows[i].dataset.manufacturer; // Sản xuất
            document.getElementById('editProSupport').value = rows[i].dataset.support; // Hỗ trợ
            return;
        }
    }

    alert('Không tìm thấy sản phẩm với mã này!');
}

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

function loadProductKeyInfo() {
    const productId = document.getElementById('addProductId').value; // ID của sản phẩm cần chỉnh sửa
    const rows = document.querySelectorAll('#productTable tr');

    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        if (cells[0] && cells[0].innerText === productId) {
            document.getElementById('addKey').classList.remove('hidden'); // Hiện form sửa sản phẩm

            // Điền giá trị vào form
            document.getElementById('idProductKey').value = productId; // Tên sản phẩm
            return;
        }
    }

    alert('Không tìm thấy sản phẩm với mã này!');
}

