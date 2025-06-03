package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AOrder implements Serializable {
    Integer id;
    String firstName;
    String paymentMethod;
    Integer paymentId;
    String code;
    LocalDateTime orderDate;
    double totalPrice;
    Integer status;

    public AOrder() {
    }

    public AOrder(Integer id, String firstName, String paymentMethod, String code, LocalDateTime orderDate, double totalPrice, Integer status) {
        this.id = id;
        this.firstName = firstName;
        this.paymentMethod = paymentMethod;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public AOrder(Integer id, String paymentMethod, String code, LocalDateTime orderDate, double totalPrice, Integer status) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public AOrder(Integer id, String firstName, Integer paymentId, String code, LocalDateTime orderDate, double totalPrice, Integer status) {
        this.id = id;
        this.firstName = firstName;
        this.paymentId = paymentId;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AOrder{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", paymentId=" + paymentId +
                ", code='" + code + '\'' +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
