package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model;

public class CartItem {
    private int id;
    private int cartId;
    private int couponId;
    private int quantity;
    private double unitPrice;

    private int productId;
    private String size;
    private int stock;
    private String image;
    private String color;

    private String name;

    public CartItem() {}

    public CartItem(int id, int cartId, int couponId, int quantity, double unitPrice, int productId, String size, int stock, String image, String color, String name) {
        this.id = id;
        this.cartId = cartId;
        this.couponId = couponId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.productId = productId;
        this.size = size;
        this.stock = stock;
        this.image = image;
        this.color = color;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cartId=" + cartId +
                ", couponId=" + couponId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", productId=" + productId +
                ", size='" + size + '\'' +
                ", stock=" + stock +
                ", image='" + image + '\'' +
                ", color='" + color + '\'' +
                ", name='" + name + '\'' +
                "}\n";
    }
}
