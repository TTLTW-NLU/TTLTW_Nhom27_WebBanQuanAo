package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartProduct implements Serializable {
    private int id;
    private String name;
    private double unitPrice;
    private List<String> images;
    private int quantity;
    private List<String> sizes;
    private List<String> colors;

    public CartProduct() {
        this.sizes = new ArrayList<String>();
        this.images = new ArrayList<String>();
        this.colors = new ArrayList<String>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

}
