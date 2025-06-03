package vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model;

public class ShippingFeeResponse {
    private int total;
    private int serviceFee;
    private int insuranceFee;
    private int pickStationFee;
    private int couponValue;
    private int r2sFee;
    private int documentReturn;
    private int doubleCheck;
    private int codFee;
    private int pickRemoteAreasFee;
    private int deliverRemoteAreasFee;
    private int codFailedFee;

    public ShippingFeeResponse() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(int insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public int getPickStationFee() {
        return pickStationFee;
    }

    public void setPickStationFee(int pickStationFee) {
        this.pickStationFee = pickStationFee;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public int getR2sFee() {
        return r2sFee;
    }

    public void setR2sFee(int r2sFee) {
        this.r2sFee = r2sFee;
    }

    public int getDocumentReturn() {
        return documentReturn;
    }

    public void setDocumentReturn(int documentReturn) {
        this.documentReturn = documentReturn;
    }

    public int getDoubleCheck() {
        return doubleCheck;
    }

    public void setDoubleCheck(int doubleCheck) {
        this.doubleCheck = doubleCheck;
    }

    public int getCodFee() {
        return codFee;
    }

    public void setCodFee(int codFee) {
        this.codFee = codFee;
    }

    public int getPickRemoteAreasFee() {
        return pickRemoteAreasFee;
    }

    public void setPickRemoteAreasFee(int pickRemoteAreasFee) {
        this.pickRemoteAreasFee = pickRemoteAreasFee;
    }

    public int getDeliverRemoteAreasFee() {
        return deliverRemoteAreasFee;
    }

    public void setDeliverRemoteAreasFee(int deliverRemoteAreasFee) {
        this.deliverRemoteAreasFee = deliverRemoteAreasFee;
    }

    public int getCodFailedFee() {
        return codFailedFee;
    }

    public void setCodFailedFee(int codFailedFee) {
        this.codFailedFee = codFailedFee;
    }

    @Override
    public String toString() {
        return "ShippingFeeResponse{" +
                "total=" + total +
                ", serviceFee=" + serviceFee +
                ", insuranceFee=" + insuranceFee +
                ", pickStationFee=" + pickStationFee +
                ", couponValue=" + couponValue +
                ", r2sFee=" + r2sFee +
                ", documentReturn=" + documentReturn +
                ", doubleCheck=" + doubleCheck +
                ", codFee=" + codFee +
                ", pickRemoteAreasFee=" + pickRemoteAreasFee +
                ", deliverRemoteAreasFee=" + deliverRemoteAreasFee +
                ", codFailedFee=" + codFailedFee +
                '}';
    }
}
