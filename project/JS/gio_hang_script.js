document.addEventListener("DOMContentLoaded", () => {
    // Button add, sub
    document.querySelectorAll(".btn-add-sub").forEach(container => {
        const btnAdd = container.querySelector(".btn:nth-child(3)");
        const btnSub = container.querySelector(".btn:nth-child(1)");
        const countSpan = container.querySelector(".count span");

        btnAdd.addEventListener("click", () => {
            let count = parseInt(countSpan.textContent);
            count++;
            countSpan.textContent = count;
        });

        btnSub.addEventListener("click", () => {
            let count = parseInt(countSpan.textContent);
            if (count > 1) {
                count--;
                countSpan.textContent = count;
            } else {
                handleDelete(container.closest(".item"));
            }
        });
    });

    // Button delete
    document.querySelectorAll(".btn-delete").forEach(btnDelete => {
        btnDelete.addEventListener("click", () => {
            const item = btnDelete.closest(".item");
            handleDelete(item);
        });
    });

    function handleDelete(item) {
        const countProduct= document.getElementById("countProduct");
        const confirmDelete = confirm("Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng không?");
        if (confirmDelete) {
            let count = parseInt(countProduct.textContent);
            count--;
            countProduct.textContent = count;
            item.remove();
        }
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const omegaContainer = document.querySelector(".omega .box-shadow-payment"); // Container bên Omega

    // Xử lý sự kiện khi bấm checkbox
    document.querySelectorAll(".select-checkbox").forEach((checkbox) => {
        checkbox.addEventListener("change", () => {
            const item = checkbox.closest(".item"); // Lấy item chứa checkbox
            const nameElement = item.querySelector(".text-title"); // Lấy tên sản phẩm
            const quantityElement = item.querySelector(".count span"); // Lấy số lượng
            const priceElement = item.querySelector(".checkbox-cost"); // Lấy giá sản phẩm

            const productName = truncateText(nameElement.textContent, 28);
            const quantity = `X ${quantityElement.textContent}`;
            const price = priceElement.textContent;

            if (checkbox.checked) {
                // Kiểm tra sản phẩm đã tồn tại chưa trước khi thêm
                if (!isProductInOmega(productName)) {
                    addProductToOmega(productName, quantity, price);
                }
            } else {
                removeProductFromOmega(productName);
            }

            updateSubtotal(); // Cập nhật tổng tiền
        });
    });

    function truncateText(text, maxLength) {
        return text.length > maxLength ? text.slice(0, maxLength - 3) + "..." : text;
    }

    function isProductInOmega(productName) {
        return [...omegaContainer.querySelectorAll(".omega-product .text-gray")].some(
            (span) => span.textContent === productName
        );
    }

    function addProductToOmega(name, quantity, price) {
        const quantityNumber = parseInt(quantity.replace("X ", "").trim(), 10); // Lấy số lượng
        const priceNumber = parseInt(price.replace(/[^\d]/g, ""), 10); // Chuyển giá thành số

        const totalPrice = priceNumber * quantityNumber; // Tính tổng giá

        const productDiv = document.createElement("div");
        productDiv.className = "d-flex items-center justify-between margin-top-bot omega-product";
        productDiv.innerHTML = `
        <div>
            <span class="text-gray">${name}</span>
            <span class="font-sz16">${quantity}</span>
        </div>
        <div class="font-sz16 cost-add">${totalPrice.toLocaleString()}đ</div>
    `;
        omegaContainer.insertBefore(productDiv, omegaContainer.querySelector(".checkbox-content"));
    }


    function removeProductFromOmega(productName) {
        const productDivs = omegaContainer.querySelectorAll(".omega-product");
        productDivs.forEach((div) => {
            const span = div.querySelector("span.text-gray");
            if (span && span.textContent === productName) {
                div.remove();
            }
        });
    }

    function updateSubtotal() {
        const products = omegaContainer.querySelectorAll(".omega-product");
        let subtotal = 0;

        products.forEach((product) => {
            const priceText = product.querySelector(".cost-add").textContent.replace(/[^\d]/g, "");
            subtotal += parseInt(priceText, 10);
        });


        // Cập nhật giá trị của phần tử có class cost-all
        const costAllElement = document.querySelector(".cost-all");
        if (costAllElement) {
            costAllElement.textContent = subtotal.toLocaleString() + "đ";
        }
        console.log("Subtotal:", subtotal); // Kiểm tra giá trị subtotal
        console.log("Cost All Element:", costAllElement); // Kiểm tra sự tồn tại của cost-all

    }

});


