<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp" %>
    <title>Thanh Toán</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/checkout.css">
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>
<!-- Check Out -->
<div id="check-out">
    <div class="container">
        <div class="py-5 text-center">
            <h2 style=" color: #335d4a !important"><b>Thanh Toán</b></h2>
            <hr>
        </div>
        <div class="row">
            <%--            <div class="col-md-4 order-md-2 mb-4">--%>
            <%--                <h4 class="d-flex justify-content-between align-items-center mb-3">--%>
            <%--                    <span class="text-muted header-text" style=" color: #335d4a !important">Giỏ Hàng Của Bạn</span>--%>
            <%--                    <span class="badge badge-secondary badge-pill"></span>--%>
            <%--                </h4>--%>
            <%--                <ul class="list-group mb-3">--%>
            <%--                    <li class="list-group-item d-flex justify-content-between lh-condensed">--%>
            <%--                        <div>--%>
            <%--                            <h6 class="my-0 gr-text"></h6>--%>
            <%--                            <small class="text-muted"></small>--%>
            <%--                        </div>--%>
            <%--                        <span class="text-muted"></span>--%>
            <%--                    </li>--%>
            <%--                    <li class="list-group-item d-flex justify-content-between bg-light">--%>
            <%--                        <div class="text-success">--%>
            <%--                            <h6 class="my-0 gr-text text-custom">Giao Hàng</h6>--%>
            <%--                        </div>--%>
            <%--                        <span class="text-success" style=" color: #335d4a !important"></span>--%>
            <%--                    </li>--%>
            <%--                    <li class="list-group-item d-flex justify-content-between bg-light">--%>
            <%--                        <div class="text-success">--%>
            <%--                            <h6 class="my-0 gr-text text-custom">Mã Giảm Giá</h6>--%>
            <%--                        </div>--%>
            <%--                        <span class="text-success" style=" color: #335d4a !important"></span>--%>
            <%--                    </li>--%>
            <%--                    <li class="list-group-item d-flex justify-content-between">--%>
            <%--                        <span class="my-0 gr-text">Tổng Tiền</span>--%>
            <%--                        <strong></strong>--%>
            <%--                    </li>--%>
            <%--                </ul>--%>
            <%--            </div>--%>
            <div class="col-md-8 order-md-1 mx-auto">
                <h4 class="mb-3 header-text" style=" color: #335d4a !important">Địa Chỉ Thanh Toán</h4>
                <form class="needs-validation" novalidate method="post" action="PaymentServlet">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="firstName"><i class="fa-solid fa-id-card"></i> Họ</label>
                            <input type="text" class="form-control" id="firstName" placeholder="" value="" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="lastName" class="label-text"><i class="fa-solid fa-id-card"></i> Tên</label>
                            <input type="text" class="form-control" id="lastName" placeholder="" value="" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="phone"><i class="fa-solid fa-phone"></i> Số Điện Thoại</label>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text">+84</span>
                            </div>
                            <input type="text" class="form-control" id="phone" placeholder="" required>
                            <div class="invalid-feedback" style="width: 100%;">
                                Vui lòng nhập số điện thoại
                            </div>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="email"><i class="fa-solid fa-envelope"></i> Email <span
                                class="text-muted"></span></label>
                        <input type="email" class="form-control" id="email" placeholder="">
                        <div class="invalid-feedback">
                            Vui lòng nhập địa chỉ email hợp lệ để cập nhật thông tin vận chuyển
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="address"><i class="fa-solid fa-location-dot"></i> Địa chỉ</label>
                        <input type="text" class="form-control" id="address" placeholder="" required>
                        <div class="invalid-feedback">
                            Vui lòng nhập địa chỉ của bạn
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="province" class="lb-text">Tỉnh/Thành phố</label>
                            <select class="custom-select d-block w-100 large-select" id="province" name="province" required>
                                <option value="">-- Chọn Tỉnh/Thành --</option>
                            </select>
                            <div class="invalid-feedback">
                                Vui lòng chọn Tỉnh/Thành Phố của bạn
                            </div>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label for="district" class="lb-text">Quận/Huyện</label>
                            <select class="custom-select d-block w-100 large-select" id="district" name="district" required>
                                <option value="">-- Chọn Quận/Huyện --</option>
                            </select>
                            <div class="invalid-feedback">
                                Vui lòng chọn Quận/Huyện của bạn
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="ward" class="lb-text">Phường/Xã</label>
                            <select class="custom-select d-block w-100 large-select" id="ward" name="ward" required>
                                <option value="">-- Chọn Phường/Xã --</option>
                            </select>
                            <div class="invalid-feedback">
                                Vui lòng chọn Phường/Xã của bạn
                            </div>
                        </div>
<%--                        <div class="col-md-6 mb-3">--%>
<%--                            <label for="address2" class="lb-text">Địa chỉ bổ sung <small class="text-muted">(Không--%>
<%--                                bắt--%>
<%--                                buộc)</small></label>--%>
<%--                            <input type="text" class="form-control" id="address2" placeholder="Số căn hộ/phòng ">--%>
<%--                        </div>--%>
                    </div>

                    <%--                    <hr class="mb-4">--%>
                    <%--                    <div class="custom-control custom-checkbox">--%>
                    <%--                        <input type="checkbox" class="custom-control-input" id="same-address">--%>
                    <%--                        <label class="custom-control-label" for="same-address">Dùng địa chỉ thanh toán làm địa chỉ--%>
                    <%--                            giao--%>
                    <%--                            hàng</label>--%>
                    <%--                    </div>--%>
                    <%--                    <div class="custom-control custom-checkbox">--%>
                    <%--                        <input type="checkbox" class="custom-control-input" id="save-info">--%>
                    <%--                        <label class="custom-control-label" for="save-info">Lưu thông tin này cho lần sau</label>--%>
                    <%--                    </div>--%>
                    <%--                    <hr class="mb-4">--%>
                    <%--                    <div>--%>
                    <%--                        <h4 class="mb-3 header-text" style=" color: #335d4a !important">Phương Thức Thanh Toán</h4>--%>
                    <%--                        <div>--%>
                    <%--                            <img src="assets/imgs/CheckOut/logo_1.png" alt="">--%>
                    <%--                            <img src="assets/imgs/CheckOut/logo_2.png" alt="">--%>
                    <%--                            <img src="assets/imgs/CheckOut/logo_3.png" alt="">--%>
                    <%--                            <img src="assets/imgs/CheckOut/logo_6.webp" alt="">--%>
                    <%--                            <img src="assets/imgs/CheckOut/logo_4.png" alt="">--%>
                    <%--                            <img src="assets/imgs/CheckOut/logo_5.jpg" alt="">--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <!-- <div class="d-block my-3">
                        <div class="custom-control custom-radio">
                            <input id="credit" name="paymentMethod" type="radio" class="custom-control-input"
                                checked required>
                            <label class="custom-control-label" for="credit">Thẻ tín dụng</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="debit" name="paymentMethod" type="radio" class="custom-control-input"
                                required>
                            <label class="custom-control-label" for="debit">Thẻ ghi nợ</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="momo" name="paymentMethod" type="radio" class="custom-control-input"
                                required>
                            <label class="custom-control-label" for="momo">Momo</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="zalopay" name="paymentMethod" type="radio" class="custom-control-input"
                                required>
                            <label class="custom-control-label" for="zalopay">Zalo Pay</label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input id="cod" name="paymentMethod" type="radio" class="custom-control-input" required>
                            <label class="custom-control-label" for="cod">Thanh toán khi nhận hàng (COD)</label>
                        </div>
                    </div> -->
                    <%--                    <div class="md-6 mb-3">--%>
                    <%--                        <label for="paymentMethod" class="lb-text"></label>--%>
                    <%--                        <select class="custom-select d-block w-100 large-select" id="paymentMethod" name="paymentMethod"--%>
                    <%--                                required>--%>
                    <%--                            <option value="">Chọn phương thức thanh toán</option>--%>
                    <%--                            <option value="credit">Thẻ tín dụng</option>--%>
                    <%--                            <option value="debit">Thẻ ghi nợ</option>--%>
                    <%--                            <option value="momo">Momo</option>--%>
                    <%--                            <option value="zalopay">Zalo Pay</option>--%>
                    <%--                            <option value="cod">Thanh toán khi nhận hàng (COD)</option>--%>
                    <%--                        </select>--%>
                    <%--                        <div class="invalid-feedback">--%>
                    <%--                            Vui lòng chọn phương thức thanh toán--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>

                    <%--                    <div class="row">--%>
                    <%--                        <div class="col-md-6 mb-3">--%>
                    <%--                            <label for="cc-name">Tên trên thẻ</label>--%>
                    <%--                            <input type="text" class="form-control" id="cc-name" placeholder="" required>--%>
                    <%--                            <small class="text-muted">Họ và tên đầy đủ hiển thị trên thẻ</small>--%>
                    <%--                            <div class="invalid-feedback">--%>
                    <%--                                Tên trên thẻ là bắt buộc--%>
                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                        <div class="col-md-6 mb-3">--%>
                    <%--                            <label for="cc-number">Số thẻ tín dụng</label>--%>
                    <%--                            <input type="text" class="form-control" id="cc-number" placeholder="" required>--%>
                    <%--                            <div class="invalid-feedback">--%>
                    <%--                                Số thẻ tín dụng là bắt buộc--%>
                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <%--                    <div class="row">--%>
                    <%--                        <div class="col-md-3 mb-3">--%>
                    <%--                            <label for="cc-expiration">Ngày hết hạn</label>--%>
                    <%--                            <input type="text" class="form-control" id="cc-expiration" placeholder="MM/YY" required>--%>
                    <%--                            <div class="invalid-feedback">--%>
                    <%--                                Ngày hết hạn là bắt buộc--%>
                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                        <div class="col-md-3 mb-3">--%>
                    <%--                            <label for="cc-cvv">CVV</label>--%>
                    <%--                            <input type="text" class="form-control" id="cc-cvv" placeholder="* * *" required>--%>
                    <%--                            <div class="invalid-feedback">--%>
                    <%--                                Mã bảo mật là bắt buộc--%>
                    <%--                            </div>--%>
                    <%--                        </div>--%>
                    <%--                    </div>--%>
                    <hr class="mb-4">
                    <input type="hidden" name="totalPrice" value="${totalPrice}">
                    <%-- Hiển thị thông báo nếu có --%>
                    <c:if test="${not empty message}">
                        <div style="color: #335d4a; font-weight: bold; margin-top: 20px;" class="text-center">
                                ${message}
                        </div>
                    </c:if>

                    <button class="btn btn-primary btn-lg btn-block" type="submit">THANH TOÁN</button>
                </form>
                <div class="back-to-shop text-muted"><a href="cart">&leftarrow; Trở về giỏ hàng</a>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- END: Check Out -->

<!-- Checkout pop-up-->
<div class="container checkout-container hide" id="checkoutContainer">
    <!-- <div class="progress-bar">
        <div class="half col-md-5">
            <div class="num num_one">1</div>
            <span class="mx-2">Địa chỉ</span>
        </div>
        <div class="half col-md-5">
            <div class="num num_two">2</div>
            <span class="mx-2">Phương thức thanh toán</span>
        </div>
    </div> -->

    <div class="row">
        <div class="col-md-12">
            <h5>Địa chỉ nhận hàng</h5>
            <div class="shipping-box">
                <div class="form-check">
                    <input class="form-check-input" type="radio" checked>
                    <label class="form-check-label">Nhập địa chỉ</label>
                </div>
                <form>
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <input type="text" class="form-control" placeholder="Họ và tên">
                        </div>
                        <div class="col-md-6">
                            <input type="text" class="form-control" placeholder="Số điện thoại">
                        </div>
                    </div>
                    <input type="text" class="form-control mt-2" placeholder="Địa chỉ nơi nhận hàng">
                </form>
            </div>
        </div>

        <div class="col-md-12">
            <h5 class="mt-4">Phương thức thanh toán</h5>
            <div class="shipping-box">
                <form>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod" checked>
                        <label class="form-check-label">Credit/Debit Card</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod">
                        <label class="form-check-label">PayPal</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="paymentMethod">
                        <label class="form-check-label">Cash on Delivery</label>
                    </div>
                </form>
            </div>
        </div>

        <div class="col-md-12">
            <h5 class="mt-4">Thông tin chi tiết thẻ Tín dụng/Ghi nợ</h5>
            <div class="shipping-box">
                <div class="form-check">
                    <input class="form-check-input" type="radio" checked>
                    <label class="form-check-label">Nhập địa chỉ</label>
                </div>
                <form>
                    <input type="text" class="form-control mt-3" placeholder="Số thẻ">
                    <div class="row mt-2">
                        <div class="col-md-7">
                            <input type="text" class="form-control mt-2" placeholder="Ngày hết hạn (MM/YY)">
                        </div>
                        <div class="col-md-5">
                            <input type="text" class="form-control mt-2" placeholder="Mã CVV">
                        </div>
                    </div>
                    <input type="text" class="form-control mt-2" placeholder="Họ và tên chủ thẻ">
                    <input type="text" class="form-control mt-2" placeholder="Địa chỉ đăng ký thẻ">
                    <input type="text" class="form-control mt-2" placeholder="Mã bưu chính">
                </form>
            </div>
        </div>

        <!-- <div class="col-md-5">
            <div class="summary-box">
                <p class="mt-2 text-muted">By placing your order, you agree to our Privacy Policy and Conditions of Use.</p>
                <hr>
                <h6>Order Summary</h6>
                <p>Items (3): <span class="float-end">$56.73</span></p>
                <p>Shipping & Handling: <span class="float-end">$5.50</span></p>
                <p>Before Tax: <span class="float-end">$62.23</span></p>
                <p>Tax Collected: <span class="float-end">$8.21</span></p>
                <hr>
                <h5>Order Total: <span class="float-end">$70.44</span></h5>
            </div>
        </div> -->
    </div>

    <div class="d-flex justify-content-between mt-3">
        <button type="button" class="btn btn-secondary" id="closeCheckout">Huỷ giao dịch</button>
        <button type="submit" class="btn btn-primary">Xác nhận giao dịch</button>
    </div>
</div>

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

<!-- base js -->
<script src="./assets/js/base.js"></script>
<%-- Javascript --%>
<script>
    const GHN_TOKEN = "750c9e8a-3d3f-11f0-9b81-222185cb68c8";

    fetch("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province", {
        method: "GET",
        headers: {
            "Token": GHN_TOKEN
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Không thể gọi API");
            }
            return response.json();
        })
        .then(data => {
            const provinceSelect = document.getElementById("province");
            data.data.forEach(province => {
                const option = document.createElement("option");
                option.value = province.ProvinceID;
                option.textContent = province.ProvinceName;
                provinceSelect.appendChild(option);
            });
        })
        .catch(error => {
            alert("Lỗi khi gọi API GHN: " + error.message);
            console.error(error);
        });

    // After selecting the province, load district
    document.getElementById("province").addEventListener("change", function () {
        const provinceId = this.value;
        const districtSelect = document.getElementById("district");

        // Clear
        districtSelect.innerHTML = '<option value="">-- Chọn Quận/Huyện --</option>';

        if (!provinceId) return;

        fetch("https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Token": GHN_TOKEN
            },
            body: JSON.stringify({
                province_id: parseInt(provinceId)
            })
        })
            .then(response => response.json())
            .then(data => {
                data.data.forEach(district => {
                    const option = document.createElement("option");
                    option.value = district.DistrictID;
                    option.textContent = district.DistrictName;
                    districtSelect.appendChild(option);
                });
            })
            .catch(error => {
                console.error("Lỗi khi load quận/huyện:", error);
                alert("Không thể tải danh sách quận/huyện.");
            });
    });

    // After selecting the district, load ward
    document.getElementById("district").addEventListener("change", function () {
        const districtId = this.value;
        const wardSelect = document.getElementById("ward");

        // Clear
        wardSelect.innerHTML = '<option value="">-- Chọn Phường/Xã --</option>';

        if (!districtId) return;

        fetch(`https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Token": GHN_TOKEN
            },
            body: JSON.stringify({
                district_id: parseInt(districtId)
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Không thể gọi API");
                }
                return response.json();
            })
            .then(data => {
                data.data.forEach(ward => {
                    const option = document.createElement("option");
                    option.value = ward.WardCode;
                    option.textContent = ward.WardName;
                    wardSelect.appendChild(option);
                })
            })
            .catch(error => {
                console.error("Lỗi khi load Phường/Xã:", error);
                alert("Không thể tải danh sách Phường/Xã.");
            });
    });
</script>
</body>

</html>