function generateCaptcha() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let captcha = '';
    for (let i = 0; i < 6; i++) {
        captcha += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    document.getElementById('captchaText').textContent = captcha;
    sessionStorage.setItem('captcha', captcha); // Lưu captcha trên trình duyệt
    applyRandomStyles();
}

function applyRandomStyles() {
    const captchaElement = document.getElementById('captchaText');
    captchaElement.style.color = '#' + Math.floor(Math.random() * 16777215).toString(16);
    captchaElement.style.transform = 'rotate(' + (Math.random() * 10 - 5) + 'deg)';
    captchaElement.style.filter = 'blur(' + (Math.random() * 2) + 'px)';
}

document.getElementById('refreshCaptcha').addEventListener('click', function() {
    generateCaptcha();
    document.getElementById('captchaInput').value = '';
});

document.getElementById('loginForm').addEventListener('submit', function(event) {
    const userCaptcha = document.getElementById('captchaInput').value;
    const storedCaptcha = sessionStorage.getItem('captcha');

    if (userCaptcha !== storedCaptcha) {
        event.preventDefault();
        alert('Mã captcha không đúng. Vui lòng thử lại.');
        generateCaptcha();
        document.getElementById('captchaInput').value = '';
    }
});

// Tạo captcha khi trang tải
window.onload = generateCaptcha;
