document.getElementById('saveInfoButton').addEventListener('click', function() {
    const usernameField = document.getElementById('username');
    const phoneField = document.getElementById('phone');

    const username = usernameField.value.trim();
    const phone = phoneField.value.trim();

    // Reset màu nền ô nhập
    usernameField.style.borderColor = '';
    phoneField.style.borderColor = '';

    if (!username) {
        usernameField.style.borderColor = 'red'; // Tô đỏ ô nhập tên người dùng
        showNotification('Vui lòng điền tên người dùng.');
        return;
    }

    if (!phone) {
        phoneField.style.borderColor = 'red'; // Tô đỏ ô nhập số điện thoại
        showNotification('Vui lòng điền số điện thoại người dùng.');
        return;
    }
});

// Đổi mật khẩu
document.getElementById('changePasswordButton').addEventListener('click', function() {
    const oldPasswordField = document.getElementById('old-password');
    const newPasswordField = document.getElementById('new-password');
    const confirmPasswordField = document.getElementById('confirm-password');

    const oldPassword = oldPasswordField.value.trim();
    const newPassword = newPasswordField.value.trim();
    const confirmPassword = confirmPasswordField.value.trim();

    // Reset màu nền ô nhập
    oldPasswordField.style.borderColor = '';
    newPasswordField.style.borderColor = '';
    confirmPasswordField.style.borderColor = '';

    if (!oldPassword || !newPassword || !confirmPassword) {
        if (!oldPassword) oldPasswordField.style.borderColor = 'red'; // Tô đỏ ô mật khẩu cũ
        if (!newPassword) newPasswordField.style.borderColor = 'red'; // Tô đỏ ô mật khẩu mới
        if (!confirmPassword) confirmPasswordField.style.borderColor = 'red'; // Tô đỏ ô xác nhận mật khẩu
        showNotification('Vui lòng điền tất cả thông tin.');
        return;
    }

    if (newPassword !== confirmPassword) {
        showNotification('Mật khẩu mới không khớp.');
        return;
    }
});

// Hàm hiển thị thông báo
function showNotification(message) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.style.display = 'block'; // Hiện thông báo

    // Ẩn thông báo sau 3 giây
    setTimeout(() => {
        notification.style.display = 'none';
    }, 3000);
}