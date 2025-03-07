// JavaScript để cập nhật nội dung
function updateTransferInfo() {
    const bank = document.getElementById("bankSelect").value;
    const accountInfo = {
        momo: {account: "123456789", holder: "Nguyen Phuc Thanh"},
        bidv: {account: "987654321", holder: "Nguyen Thanh Son"},
        mbbank: {account: "555555555", holder: "Le Quoc Thai"},
        vietcombank: {account: "444444444", holder: "Pham Thi D"},
    };
    document.getElementById("accountNumber").innerText = accountInfo[bank].account;
    document.getElementById("accountHolder").innerText = accountInfo[bank].holder;
}

function copyText(elementId) {
    const text = document.getElementById(elementId).innerText;
    navigator.clipboard.writeText(text).then(() => {
        alert("Đã sao chép: " + text);
    });
}

function confirmPayment() {
    const confirmButton = document.getElementById("confirmText");
    const paymentStatus = document.getElementById("status");
    const editButton = document.getElementById("btn-edit");
    const bankList = document.getElementById("bankSelect");
    const backButton = document.getElementById("btn-back");

    // Đổi nội dung nút thành "Chờ 5s..."
    confirmButton.textContent = "Chờ 5s...";

    // Sau 3 giây (thay vì 5 giây) thực hiện các thay đổi khác
    setTimeout(() => {
        // Đổi lại nội dung nút về "Lịch sử mua hàng"
        confirmButton.textContent = "Lịch sử mua hàng";

        // Cập nhật trạng thái thành "Đã thanh toán" và đổi màu thành xanh lá
        paymentStatus.textContent = "Đã thanh toán";
        paymentStatus.style.color = "green";

        // Vô hiệu hóa danh sách ngân hàng
        bankList.disabled = true;

        // Thay đổi nút "Quay lại" thành "Đánh giá"
        backButton.textContent = "Gửi đánh giá";
        backButton.classList.remove("back-button"); // Xóa lớp cũ
        backButton.classList.add("rating-button"); // Thêm lớp mới

        backButton.onclick = () => {
            window.location.href = "../HTML/hoa_don.html"; // Đường dẫn đến trang đánh giá
        };

        // Thêm sự kiện click cho nút "Lịch sử mua hàng"
        confirmButton.addEventListener("click", function() {
            window.location.href = "../HTML/lichsugiaodich.html"; // Địa chỉ của trang lịch sử mua hàng
        });
    }, 3000); // 3000 milliseconds tương đương với 3 giây
}

function goBack() {
    window.history.back();
}

