document.addEventListener("DOMContentLoaded", () => {
    const stars = document.querySelectorAll(".star");
    stars.forEach(star => {
        star.addEventListener("click", () => {
            const value = star.getAttribute("data-value");
            stars.forEach(s => {
                s.classList.remove("selected");
                if (s.getAttribute("data-value") <= value) {
                    s.classList.add("selected");
                }
            });
        });
    });

    const submitButton = document.querySelector(".submit-button");
    submitButton.addEventListener("click", () => {
        const selectedStars = document.querySelectorAll(".star.selected").length;
        const reviewText = document.querySelector(".review-box").value.trim();

        if (selectedStars === 0 || !reviewText) {
            alert("Vui lòng đánh giá và nhập nội dung!");
            return;
        }
        alert("Đánh giá của bạn đã được gửi!");
    });
});
