package vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model;

public class ShippingFeeRequest {
    private int fromDistrictId;
    private String fromWardCode;
    private int toDistrictId;
    private String toWardCode;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int serviceId;
    private int insuranceValue;
    private String coupon;

    public ShippingFeeRequest(int fromDistrictId, String fromWardCode, int toDistrictId, String toWardCode, int weight, int length, int width, int height, int serviceId, int insuranceValue, String coupon) {
        this.fromDistrictId = fromDistrictId;
        this.fromWardCode = fromWardCode;
        this.toDistrictId = toDistrictId;
        this.toWardCode = toWardCode;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.height = height;
        this.serviceId = serviceId;
        this.insuranceValue = insuranceValue;
        this.coupon = coupon;
    }

    public ShippingFeeRequest() {
    }

    public int getFromDistrictId() {
        return fromDistrictId;
    }

    public void setFromDistrictId(int fromDistrictId) {
        this.fromDistrictId = fromDistrictId;
    }

    public String getFromWardCode() {
        return fromWardCode;
    }

    public void setFromWardCode(String fromWardCode) {
        this.fromWardCode = fromWardCode;
    }

    public int getToDistrictId() {
        return toDistrictId;
    }

    public void setToDistrictId(int toDistrictId) {
        this.toDistrictId = toDistrictId;
    }

    public String getToWardCode() {
        return toWardCode;
    }

    public void setToWardCode(String toWardCode) {
        this.toWardCode = toWardCode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(int insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }
}
