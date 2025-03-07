document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.add-to-cart');
    buttons.forEach(button => {
        button.addEventListener('click', function () {
            showCartNotification();
        });
    });
});

function showCartNotification() {
    // Tạo thẻ thông báo
    const notification = document.createElement("div");
    notification.className = "cart-notification";
    notification.textContent = "Thêm vào giỏ hàng thành công!";

    // Thêm vào body
    document.body.appendChild(notification);

    // Tự động ẩn sau 3 giây
    setTimeout(() => {
        notification.remove();
    }, 3000);
}
