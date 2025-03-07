// Tệp danh_gia_script.js
document.querySelectorAll('.dropdown-toggle').forEach(button => {
    button.addEventListener('click', function() {
        this.nextElementSibling.style.display =
            this.nextElementSibling.style.display === 'block' ? 'none' : 'block';
    });
});

document.querySelectorAll('.dropdown-menu li').forEach(item => {
    item.addEventListener('click', function() {
        const dropdown = this.closest('.dropdown');
        const selectedValue = this.innerHTML; // Lấy toàn bộ nội dung HTML, bao gồm ngôi sao
        dropdown.querySelector('.dropdown-toggle').innerHTML = selectedValue; // Đặt HTML vào nút
        dropdown.querySelector('.dropdown-menu').style.display = 'none';
    });
});

document.addEventListener('click', function(e) {
    document.querySelectorAll('.dropdown-menu').forEach(menu => {
        if (!menu.contains(e.target) && !menu.previousElementSibling.contains(e.target)) {
            menu.style.display = 'none';
        }
    });
});

function goBack() {
    window.history.back();
}
