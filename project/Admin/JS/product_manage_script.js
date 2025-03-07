let products = [
    {
        id: "SP001",
        type: "Làm việc",
        name: "Windows 11 Pro",
        price: 450000,
        quantity: 5,
        image: "../../Images/Product/Windows11Professional.jpg",
        date: new Date("2024-11-20T19:05:00"),
        status: "Còn hàng",
        features: "",
        support: "",
        duration: "",
        description: "",
    },
    {
        id: "SP002",
        type: "Tiện ích",
        name: "Office 2019",
        price: 500000,
        quantity: 0,
        image: "../../Images/Product/Office2019.jpg",
        date: new Date("2024-03-03T07:00:00"),
        status: "Hết hàng",
        features: "",
        support: "",
        duration: "",
        description: "",
    },
];

function toggleAddForm() {
    document.getElementById("addProductForm").classList.toggle("hidden");
}

function addProduct() {
    const id = document.getElementById("addProductId").value;
    const name = document.getElementById("addProductName").value;
    const type = document.getElementById("addProductType").value;
    const price = document.getElementById("addProductPrice").value;
    const quantity = document.getElementById("addProductQuantity").value;
    const image = document.getElementById("addProductImage").value;
    const status = quantity > 0 ? "Còn hàng" : "Hết hàng";
    const date = new Date();
    products.push({
        id,
        type,
        name,
        price,
        quantity: Number(quantity),
        image,
        date,
        status,
        features: "",
        support: "",
        duration: "",
        description: "",
    });

    updateProductTable();
    document.getElementById("addProductForm").reset();
}

// Tìm kiếm sản phẩm theo mã sản phẩm
function loadProductInfo() {
    const productId = document.getElementById('editProductId').value;
    const product = products.find(p => p.id === productId);

    if (product) {
        document.getElementById('editProductName').value = product.name;
        document.getElementById('editProductType').value = product.type;
        document.getElementById('editProductPrice').value = product.price;
        document.getElementById('editProductQuantity').value = product.quantity;
        document.getElementById('editProductImage').value = product.image;
        document.getElementById('editProductDetails').classList.remove('hidden');
    } else {
        alert('Sản phẩm không tồn tại');
    }
}

// Lưu thông tin sản phẩm đã chỉnh sửa
function saveProduct() {
    const productId = document.getElementById('editProductId').value;
    const product = products.find(p => p.id === productId);

    if (product) {
        product.name = document.getElementById('editProductName').value;
        product.type = document.getElementById('editProductType').value;
        product.price = document.getElementById('editProductPrice').value;
        product.quantity = document.getElementById('editProductQuantity').value;
        product.image = document.getElementById('editProductImage').value;

        alert('Cập nhật sản phẩm thành công');
        updateProductTable();
    } else {
        alert('Sản phẩm không tồn tại');
    }
}

// Xóa sản phẩm
function deleteProduct() {
    const productId = document.getElementById('editProductId').value;
    const productIndex = products.findIndex(p => p.id === productId);

    if (productIndex !== -1) {
        products.splice(productIndex, 1);
        alert('Sản phẩm đã được xóa');
        updateProductTable();
    } else {
        alert('Sản phẩm không tồn tại');
    }
}

// Hiển thị form chỉnh sửa sản phẩm
function toggleEditForm() {
    const form = document.getElementById('editProductForm');
    form.classList.toggle('hidden');
    const details = document.getElementById('editProductDetails');
    details.classList.add('hidden');
}

function updateProductTable(data = products) {
    const productTable = document.getElementById("productTable");
    productTable.innerHTML = ""; // Xóa nội dung cũ

    data.forEach((product) => {
        const row = `
            <tr>
                <td>${product.id}</td>
                <td>${product.type}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td><img src="${product.image}" style="width: 50px; height: 50px;"></td>
                <td>${formatDate(new Date(product.date))}</td>
                <td class="status ${product.quantity > 0 ? 'in-stock' : 'out-of-stock'}">
                    ${product.quantity > 0 ? 'Còn hàng' : 'Hết hàng'}
                </td>
                <td>
                    <button onclick="editProductDetails('${product.id}')">Chi tiết</button>
                </td>
            </tr>
        `;
        productTable.insertAdjacentHTML("beforeend", row);
    });
}

function editProductDetails(productId) {
    const product = products.find((p) => p.id === productId);
    const overlay = document.createElement("div");
    overlay.className = "overlay";
    overlay.innerHTML = `
    <div class="detail-popup">
      <h3>Chi tiết sản phẩm</h3>
      <input type="text" id="featureInput" placeholder="Tính năng" value="${product.features}">
      <input type="text" id="supportInput" placeholder="Hỗ trợ" value="${product.support}">
      <input type="text" id="durationInput" placeholder="Thời hạn" value="${product.duration}">
      <textarea id="descriptionInput" placeholder="Mô tả">${product.description}</textarea>
      <button onclick="saveDetails('${product.id}', this)">Lưu</button>
      <button onclick="closePopup(this)">Đóng</button>
    </div>
  `;
    document.body.appendChild(overlay);
}

function saveDetails(productId, button) {
    const product = products.find((p) => p.id === productId);
    product.features = document.getElementById("featureInput").value;
    product.support = document.getElementById("supportInput").value;
    product.duration = document.getElementById("durationInput").value;
    product.description = document.getElementById("descriptionInput").value;
    closePopup(button);
}

function closePopup(button) {
    document.body.removeChild(button.parentElement.parentElement);
}

function applyFilters() {
    let filteredProducts = [...products];

    // Filter ID
    const filterID = document.getElementById("filterID").value;
    if (filterID === "asc") {
        filteredProducts.sort((a, b) => a.id.localeCompare(b.id));
    } else if (filterID === "desc") {
        filteredProducts.sort((a, b) => b.id.localeCompare(a.id));
    }

    // Filter Type
    const filterType = document.getElementById("filterType").value.trim().toLowerCase();
    if (filterType) {
        filteredProducts = filteredProducts.filter(product =>
            product.type.toLowerCase().includes(filterType === "relax" ? "Giải trí" :
                filterType === "learn" ? "học tập" :
                    filterType === "work" ? "làm việc" :
                        filterType === "utilities" ? "tiện ích" :
                            filterType === "security" ? "bảo mật" :"lưu trữ")
        );
    }

    // Filter Status
    const filterStatus = document.getElementById("filterStatus").value;
    if (filterStatus === "in-stock") {
        filteredProducts = filteredProducts.filter(product => product.quantity > 0);
    } else if (filterStatus === "out-stock") {
        filteredProducts = filteredProducts.filter(product => product.quantity === 0);
    }

    // Filter Price
    const filterPrice = document.getElementById("filterPrice").value;
    if (filterPrice === "asc") {
        filteredProducts.sort((a, b) => a.price - b.price);
    } else if (filterPrice === "desc") {
        filteredProducts.sort((a, b) => b.price - a.price);
    }

    // Filter Date
    const filterDate = document.getElementById("filterDate").value;
    if (filterDate === "asc") {
        filteredProducts.sort((a, b) => new Date(a.date) - new Date(b.date));
    } else if (filterDate === "desc") {
        filteredProducts.sort((a, b) => new Date(b.date) - new Date(a.date));
    }

    // Update the table with the filtered products
    updateProductTable(filteredProducts);
}


function formatDate(date) {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const year = date.getFullYear();

    return `${hours}:${minutes} ${day}/${month}/${year}`;
}


function resetFilters() {
    document.getElementById("filterID").value = "";
    document.getElementById("filterType").value = "";
    document.getElementById("filterPrice").value = "";
    document.getElementById("filterStatus").value = "";
    document.getElementById("filterDate").value = "";
    updateProductTable(products);
}

updateProductTable();
