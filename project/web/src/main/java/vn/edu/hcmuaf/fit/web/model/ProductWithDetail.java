package vn.edu.hcmuaf.fit.web.model;

public class ProductWithDetail {
    private Product product;
    private ProductDetail productDetail;

    public ProductWithDetail(Product product, ProductDetail productDetail) {
        this.product = product;
        this.productDetail = productDetail;
    }

    // Getters và setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}

