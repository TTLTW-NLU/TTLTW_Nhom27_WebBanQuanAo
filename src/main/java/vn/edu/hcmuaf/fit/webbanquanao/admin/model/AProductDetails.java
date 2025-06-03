package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;

public class AProductDetails implements Serializable {
    private Integer id;
    private Integer productId;
    private String size;
    private Integer stock;
    private String color;
    private String image;

    public AProductDetails() {
    }

    public AProductDetails(Integer id, Integer productId, String size, Integer stock, String image, String color) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.stock = stock;
        this.image = image;
        this.color = color;
    }


    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getSize() {
        return size;
    }

    public Integer getStock() {
        return stock;
    }

    public String getColor() {
        return color;
    }

    public String getImage() {
        return image;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "AProductDetails{" + "id=" + id + ", productId=" + productId + ", size='" + size + '\'' + ", stock=" + stock + ", color='" + color + '\'' + ", image='" + image + '\'' + '}';
    }
}
