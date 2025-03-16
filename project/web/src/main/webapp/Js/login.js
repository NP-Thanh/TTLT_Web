function generateCaptcha() {
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    let captcha = '';
    for (let i = 0; i < 6; i++) {
        captcha += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    document.getElementById('captchaText').textContent = captcha;
    applyRandomStyles();
    return captcha;
}

function applyRandomStyles() {
    const captchaElement = document.getElementById('captchaText');
    const randomColor = '#' + Math.floor(Math.random()*16777215).toString(16);
    captchaElement.style.color = randomColor;
    captchaElement.style.transform = 'rotate(' + (Math.random() * 10 - 5) + 'deg)';
    captchaElement.style.filter = 'blur(' + (Math.random() * 2) + 'px)';
}

let captchaValue = generateCaptcha();

document.getElementById('refreshCaptcha').addEventListener('click', function() {
    captchaValue = generateCaptcha();
    document.getElementById('captchaInput').value = '';
});

document.getElementById('loginForm').addEventListener('submit', function(event) {
    const userCaptcha = document.getElementById('captchaInput').value;

    if (userCaptcha !== captchaValue) {
        event.preventDefault();
        alert('Mã captcha không đúng. Vui lòng thử lại.');
        captchaValue = generateCaptcha();
        document.getElementById('captchaInput').value = '';
    }
});