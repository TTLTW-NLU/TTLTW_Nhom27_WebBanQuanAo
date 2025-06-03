package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model;

import java.io.Serializable;

public class CartProductDetail implements Serializable {
    private int productDetailId;
    private int quantity;
    private double unitPrice;

    public CartProductDetail(int productDetailId, int quantity, double unitPrice) {
        this.productDetailId = productDetailId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
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
}
