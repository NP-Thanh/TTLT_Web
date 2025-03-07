document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Ngăn chặn việc gửi form mặc định

    const emailField = document.getElementById('email');
    const passwordField = document.getElementById('password');
    const email = emailField.value.trim();
    const password = passwordField.value.trim();

    // Reset màu nền ô nhập
    emailField.style.borderColor = '';
    passwordField.style.borderColor = '';

    // Kiểm tra trường nhập
    let hasError = false;
    if (!email) {
        emailField.style.borderColor = 'red'; // Tô đỏ ô nhập email
        hasError = true;
    }
    if (!password) {
        passwordField.style.borderColor = 'red'; // Tô đỏ ô nhập mật khẩu
        hasError = true;
    }

    if (hasError) {
        showNotification('Vui lòng nhập đầy đủ thông tin đăng nhập.'); // Hiện thông báo nếu có trường trống
        return;
    }
});

function showNotification(message) {
    const notification = document.getElementById('notification');
    notification.textContent = message;
    notification.style.display = 'block'; // Hiện thông báo

    // Ẩn thông báo sau 3 giây
    setTimeout(() => {
        notification.style.display = 'none';
    }, 3000);
}