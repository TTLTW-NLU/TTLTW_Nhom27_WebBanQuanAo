package vn.edu.hcmuaf.fit.webbanquanao.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class Order implements Serializable {
    Integer id;
    String userName;
    String firstName;
    String paymentMethod;
    String code;
    LocalDateTime orderDate;
    double totalPrice;
    Integer status;
    String cancelReason;
    List<OrderDetail> orderDetails;

    public Order() {
    }

    public Order(Integer id,String userName, String firstName, String paymentMethod, String code, LocalDateTime orderDate, double totalPrice, Integer status,String cancelReason, List<OrderDetail> orderDetails) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.paymentMethod = paymentMethod;
        this.code = code;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.cancelReason = cancelReason;
        this.orderDetails = orderDetails;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", code='" + code + '\'' +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", cancelReason='" + cancelReason + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
