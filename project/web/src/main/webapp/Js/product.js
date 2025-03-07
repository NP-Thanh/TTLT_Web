const radioBtns = document.querySelectorAll('.radio-btn');
function toggleSort() {
    const sortOptions = document.getElementById("sort-options");
    const iconDown = document.getElementById("icon-down");
    const iconUp = document.getElementById("icon-up");

    if (sortOptions.style.display === "none") {
        sortOptions.style.display = "block";
        iconDown.style.display = "none";
        iconUp.style.display = "inline";
    } else {
        sortOptions.style.display = "none";
        iconDown.style.display = "inline";
        iconUp.style.display = "none";
    }
}

// Thêm sự kiện click cho mỗi radio button
radioBtns.forEach(btn => {
    btn.addEventListener('click', function() {
        // Xóa lớp 'checked' từ tất cả các radio button
        radioBtns.forEach(b => b.classList.remove('checked'));

        // Thêm lớp 'checked' vào nút được nhấn
        this.classList.add('checked');
    });
});
const products = document.querySelectorAll('.product'); // Lấy tất cả sản phẩm
const itemsPerPage = 12; // Số sản phẩm mỗi trang
let currentPage = 1;

// Hàm hiển thị các sản phẩm trên trang hiện tại
function displayProducts(page) {
    // Ẩn tất cả sản phẩm
    products.forEach((product, index) => {
        product.style.display = 'none';
    });

    // Tính toán các sản phẩm cần hiển thị
    const start = (page - 1) * itemsPerPage;
    const end = page * itemsPerPage;

    for (let i = start; i < end && i < products.length; i++) {
        products[i].style.display = 'block';
    }
}

// Hàm chuyển trang
function changePage(page) {
    currentPage = page;
    displayProducts(currentPage);

    // Xóa lớp 'active' khỏi tất cả các nút
    document.querySelectorAll('.pagination button').forEach(button => {
        button.classList.remove('active');
    });

    // Thêm lớp 'active' vào nút của trang hiện tại
    document.getElementById(`page${page}`).classList.add('active');
}

// Hiển thị trang đầu tiên và đánh dấu nút đầu tiên khi tải trang
displayProducts(currentPage);
document.getElementById(`page${currentPage}`).classList.add('active');

document.addEventListener('DOMContentLoaded', function () {
    // Chọn tất cả các phần tử danh mục (có class "category-filter")
    const categoryItems = document.querySelectorAll('.category-filter');
    console.log(categoryItems);
    // Lặp qua tất cả các phần tử danh mục và thêm sự kiện click
    categoryItems.forEach(function (item) {
        item.addEventListener('click', function () {
            // Kiểm tra xem danh mục đã được chọn chưa
            if (!item.classList.contains('active-category')) {
                // Loại bỏ class 'active-category' khỏi tất cả các danh mục
                categoryItems.forEach(function (cat) {
                    cat.classList.remove('active-category');
                });

                // Thêm class 'active-category' vào danh mục đã chọn
                item.classList.add('active-category');
            }
        });
    });
});

// ajax
function filterByCategoryAndPrice() {
    const category = document.querySelector('.category-filter.active')?.dataset.category || '';
    const startPrice = document.getElementById('startPrice').value;
    const endPrice = document.getElementById('endPrice').value;

    // Gửi yêu cầu AJAX với cả category và khoảng giá
    $.ajax({
        url: '/products',
        method: 'GET',
        data: {
            category: category,
            startPrice: startPrice,
            endPrice: endPrice,
        },
        success: function (response) {
            const productContainer = document.querySelector('.list-product .row');
            productContainer.innerHTML = '';

            response.forEach(product => {
                productContainer.innerHTML += `
                    <a href="/product/detail/${product.id}" class="product" style="text-decoration: none; color: inherit;">
                        <div class="img">
                            <img src="${product.image}" alt="${product.name}" class="img-p" style="width: 100%;">
                        </div>
                        <div class="infor-p" style="padding: 10px 16px;">
                            <span class="title-p t-none t-black d-block">${product.name}</span>
                            <span class="t-none t-black d-block" style="padding: 10px 0; font-size: 13px;">
                                ${product.status}
                            </span>
                            <div class="price-p d-flex t-between">
                                <span class="t-none t-black" style="font-weight: 550;">${product.price}₫</span>
                            </div>
                        </div>
                    </a>
                `;
            });
        },
        error: function () {
            console.error('Lỗi khi tải sản phẩm');
        },
    });
}










