package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;
import java.sql.Date;

public class AProduct implements Serializable {
    private Integer id;
    private Integer typeId;
    private Integer categoryId;
    private Integer supplierId;
    private String name;
    private String description;
    private Date releaseDate;
    private Integer unitSold;
    private double unitPrice;
    private Integer status;
   

    public AProduct() {
    }

    public AProduct(Integer id, Integer typeId, Integer categoryId, Integer supplierId, String name, String description, Date releaseDate, Integer unitSold, double unitPrice, Integer status) {
        this.id = id;
        this.typeId = typeId;
        this.categoryId = categoryId;
        this.supplierId = supplierId;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.unitSold = unitSold;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
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

    public Integer getUnitSold() {
        return unitSold;
    }

    public void setUnitSold(Integer unitSold) {
        this.unitSold = unitSold;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AProduct{" +
                "id=" + id +
                ", typeId=" + typeId +
                ", categoryId=" + categoryId +
                ", supplierId=" + supplierId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", unitSold=" + unitSold +
                ", unitPrice=" + unitPrice +
                ", status=" + status +
                '}';
    }
}
