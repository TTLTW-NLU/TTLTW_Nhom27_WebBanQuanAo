package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model;

import java.io.Serializable;
import java.util.Date;

public class SaleProduct implements Serializable {
    private int id;
    private String name;
    private String description;
    private Date releaseDate;
    private int unitSold;
    private double unitPrice;
    private boolean status;
    private String size;
    private int stock;
    private String img;
    private String color;
    private String saleName;
    private String saleDescription;
    private Date saleStart;
    private Date saleEnd;

    public SaleProduct() {
    }

    public SaleProduct(int id, String name, String description, Date releaseDate, int unitSold, double unitPrice, boolean status, String size, int stock, String img, String color, String saleName, String saleDescription, Date saleStart, Date saleEnd) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.unitSold = unitSold;
        this.unitPrice = unitPrice;
        this.status = status;
        this.size = size;
        this.stock = stock;
        this.img = img;
        this.color = color;
        this.saleName = saleName;
        this.saleDescription = saleDescription;
        this.saleStart = saleStart;
        this.saleEnd = saleEnd;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getUnitSold() {
        return unitSold;
    }

    public void setUnitSold(int unitSold) {
        this.unitSold = unitSold;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getSaleDescription() {
        return saleDescription;
    }

    public void setSaleDescription(String saleDescription) {
        this.saleDescription = saleDescription;
    }

    public Date getSaleStart() {
        return saleStart;
    }

    public void setSaleStart(Date saleStart) {
        this.saleStart = saleStart;
    }

    public Date getSaleEnd() {
        return saleEnd;
    }

    public void setSaleEnd(Date saleEnd) {
        this.saleEnd = saleEnd;
    }

    @Override
    public String toString() {
        return "SaleProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", unitSold=" + unitSold +
                ", unitPrice=" + unitPrice +
                ", status=" + status +
                ", size='" + size + '\'' +
                ", stock=" + stock +
                ", img='" + img + '\'' +
                ", color='" + color + '\'' +
                ", saleName='" + saleName + '\'' +
                ", saleDescription='" + saleDescription + '\'' +
                ", saleStart=" + saleStart +
                ", saleEnd=" + saleEnd +
                '}';
    }
}
