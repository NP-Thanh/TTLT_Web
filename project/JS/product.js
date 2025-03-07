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

function filterProducts(event) {
  event.preventDefault();
  
  // Lấy giá trị category từ liên kết
  const category = event.target.getAttribute("data-category");
  
  // Lấy tất cả các sản phẩm
  const products = document.querySelectorAll(".product");
  
  // Duyệt qua các sản phẩm và hiển thị sản phẩm có cùng category
  products.forEach((product) => {
    if (product.getAttribute("data-category") === category) {
      product.style.display = "block";  // Hiển thị sản phẩm
    } else {
      product.style.display = "none";   // Ẩn sản phẩm không thuộc danh mục
    }
  });

  // Xóa lớp active khỏi tất cả các danh mục
  const categories = document.querySelectorAll("#list-categoty a");
  categories.forEach((cat) => {
    cat.classList.remove("active-category");
  });

  // Thêm lớp active vào danh mục được chọn
  event.target.classList.add("active-category");
}





