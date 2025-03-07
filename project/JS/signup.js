document.getElementById('signupForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Ngăn chặn việc gửi form mặc định

    const emailField = document.getElementById('email');
    const passwordField = document.getElementById('password');
    const password2Field = document.getElementById('password2');

    const email = emailField.value.trim();
    const password = passwordField.value.trim();
    const password2 = password2Field.value.trim();

    // Reset màu nền ô nhập
    emailField.style.borderColor = '';
    passwordField.style.borderColor = '';
    password2Field.style.borderColor = '';

    if (!email) {
        emailField.style.borderColor = 'red'; // Tô đỏ ô nhập email
        showNotification('Vui lòng điền email.'); // Hiện thông báo nếu có trường trống
        return;
    }
    if (!password || !password2) {
        if (!password) passwordField.style.borderColor = 'red'; // Tô đỏ ô mật khẩu
        if (!password2) password2Field.style.borderColor = 'red'; // Tô đỏ ô xác nhận mật khẩu
        showNotification('Vui lòng điền mật khẩu.'); // Hiện thông báo nếu có trường trống
        return;
    }

    if (password !== password2) {
        showNotification('Mật khẩu không khớp.'); // Thông báo nếu mật khẩu không khớp
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