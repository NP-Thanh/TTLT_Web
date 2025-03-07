const products = document.querySelectorAll('.product-card');
const prevBtn = document.getElementById('prev-btn');
const nextBtn = document.getElementById('next-btn');
let currentPage = 0;
const productsPerPage = 4;

function showProducts(page) {
    products.forEach((product, index) => {
        product.style.display = (index >= page * productsPerPage && index < (page + 1) * productsPerPage) ? 'block' : 'none';
    });
}

prevBtn.addEventListener('click', () => {
    if (currentPage > 0) {
        currentPage--;
        showProducts(currentPage);
    }
});

nextBtn.addEventListener('click', () => {
    if (currentPage < Math.ceil(products.length / productsPerPage) - 1) {
        currentPage++;
        showProducts(currentPage);
    }
});

showProducts(currentPage);