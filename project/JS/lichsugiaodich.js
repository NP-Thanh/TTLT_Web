// Đảm bảo mã JavaScript chạy sau khi DOM đã tải xong
document.addEventListener('DOMContentLoaded', function () {
    const filterButton = document.getElementById('filterButton');
    const noResultRow = document.getElementById('noResultRow');

    filterButton.addEventListener('click', function () {
        const orderCodeInput = document.getElementById('orderCode');
        const orderCode = orderCodeInput.value.trim();

        if (!orderCode) {
            noResultRow.style.display = ''; // Hiện thông báo không tìm thấy sản phẩm
            noResultRow.children[0].textContent = 'Vui lòng nhập mã đơn hàng để lọc.';
        } else {
            // Giả lập kiểm tra giao dịch (thay bằng logic thực tế của bạn)
            const transactionsFound = false; // Thay đổi giá trị này dựa trên kết quả thực tế

            if (!transactionsFound) {
                noResultRow.style.display = ''; // Hiện thông báo không tìm thấy sản phẩm
                noResultRow.children[0].textContent = 'Không tìm thấy sản phẩm.';
            } else {
                noResultRow.style.display = ''; // Hiện thông báo đã tìm thấy sản phẩm
                noResultRow.children[0].textContent = 'Đã tìm thấy sản phẩm với mã: ' + orderCode;
            }
        }
    });
});
