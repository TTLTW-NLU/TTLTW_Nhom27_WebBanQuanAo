<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp" %>
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/cart.css">
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>

<!-- Shopping Cart -->
<div id="card">
    <div class="row">
        <div class="col-md-8 cart">
            <div class="title">
                <div class="row align-items-center">
                    <div class="col">
                        <h4>Giỏ Hàng</h4>
                    </div>
                </div>
            </div>
            <!-- Kiểm tra nếu giỏ hàng trống -->
            <c:if test="${empty cart}">
                <div class="row mt-4" style="height: 300px;">
                    <div class="col d-flex justify-content-center align-items-center text-muted">
                        Giỏ hàng của bạn đang không có sản phẩm nào.
                    </div>
                </div>
            </c:if>

            <c:forEach items="${cart}" var="c">
                <div class="row border-top-bottom">
                    <div class="row main align-items-center">
                        <div class="col"><img class="img-fluid" src="assets/product-imgs/${c.productDetail.image}" alt=""></div>
                        <div class="col">
                            <div class="row text-muted">${c.productName}</div>
                            <div class="row text-muted">
                                ${c.productDetail.size}
                                <div class="col text-muted">${c.productDetail.color}</div>
                            </div>
                        </div>
                        <div class="col">
                            <div class="quantity-controls">
<%--                                <a href="updateCart?pid=${c.productDetail.productId}&action=down" class="btn-decrease" data-id="${c.productDetail.productId}">-</a>--%>
<%--                                <span id="quantity-${c.productDetail.productId}" class="quantity">${c.quantity}</span>--%>
<%--                                <a href="updateCart?pid=${c.productDetail.productId}&action=up" class="btn-increase" data-id="${c.productDetail.productId}">+</a>--%>
<%--                                <form method="post" action="cart" class="inline-form">--%>
<%--                                    <input type="hidden" name="action" value="update">--%>
<%--                                    <input type="hidden" name="productDetailId" value="${c.productDetail.id}">--%>
<%--                                    <input type="hidden" name="quantity" value="${c.quantity - 1}">--%>
<%--                                    <button type="submit" class="btn-decrease">-</button>--%>
<%--                                </form>--%>

<%--                                <span class="quantity">${c.quantity}</span>--%>

<%--                                <form method="post" action="cart" class="inline-form">--%>
<%--                                    <input type="hidden" name="action" value="update">--%>
<%--                                    <input type="hidden" name="productDetailId" value="${c.productDetail.id}">--%>
<%--                                    <input type="hidden" name="quantity" value="${c.quantity + 1}">--%>
<%--                                    <button type="submit" class="btn-increase">+</button>--%>
<%--                                </form>--%>
                                <a href="#"
                                   class="btn-decrease"
                                   data-id="${c.productDetail.id}"
                                   data-quantity="${c.quantity - 1}">-</a>

                                <span class="quantity" id="quantity-${c.productDetail.id}">${c.quantity}</span>

                                <a href="#"
                                   class="btn-increase"
                                   data-id="${c.productDetail.id}"
                                   data-quantity="${c.quantity + 1}">+</a>
                            </div>
                        </div>
                        <div class="product-price col" data-price="${c.unitPrice}"></div>
                        <a>
                            <button class="btn-remove close col" data-cart-detail-id="${c.id}">&#10005;</button>
                        </a>
                    </div>
                </div>
            </c:forEach>

            <div class="back-to-shop text-muted"><a href="homePage">&leftarrow; Trở về trang chủ</a>
            </div>
        </div>
        <div class="col-md-4 summary">
            <c:if test="${empty cart}">
            </c:if>
            <c:if test="${not empty cart}">
                <div>
                    <h5>Hóa Đơn</h5>
                </div>
                <hr>
                <div class="row">
                    <div class="col" style="padding-left: 12px;">${totalPrice} SẢN PHẨM</div>
                </div>
                <form>
                    <p>VẬN CHUYỂN</p>
                    <select>
                        <option class="text-muted" value="35000">Miễn phí vận chuyển</option>
                    </select>
                    <p>MÃ GIẢM GIÁ</p>
                    <input id="code" placeholder="Nhập mã">
                </form>
                <div class="row" style="border-top: 1px solid rgba(0,0,0,.1); padding: 2vh 0;">
                    <div class="col">
                        <p>TỔNG TIỀN:</p>
                    </div>
                    <div class="col text-right">
                        <p id="total-price" data-price="${totalPrice}}">
                                ${totalPrice}
                        </p>
                    </div>
                </div>
<%--                <c:set var="totalPrice" value="0" />--%>
<%--                <c:forEach items="${cart}" var="cp">--%>
<%--                    <c:set var="totalPrice" value="${totalPrice + cp.unitPrice}" />--%>
<%--                </c:forEach>--%>
                <!-- Thông báo yêu cầu đăng nhập -->

                <!-- Thông báo yêu cầu đăng nhập -->
                <c:if test="${empty sessionScope.auth}">
                    <div style="display: flex; justify-content: center; align-items: center; height: 70px;">
                        <span>Bạn cần <a href="login.jsp" style="text-decoration: none; color: #f6f1eb;">đăng nhập</a> để thanh toán.</span>
                    </div>
                </c:if>



                <c:if test="${empty sessionScope.auth}">
                    <button class="btn btn-lg btn-block mt-4" type="button" disabled>THANH TOÁN</button>
                </c:if>
                <c:if test="${not empty sessionScope.auth}">
                    <a href="PaymentServlet">
                        <button class="btn btn-lg btn-block mt-4">THANH TOÁN</button>
                    </a>
                </c:if>


            </c:if>
        </div>
    </div>
</div>
<!-- END: Shopping Cart -->

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

<!-- JS Cart -->
<script>
    // Function to format price in VND currency
    function formatPrice(price) {
        return new Intl.NumberFormat("vi-VN", {
            style: "currency",
            currency: "VND",
            maximumFractionDigits: 0
        }).format(price);
    }

    // // Function to get cart data from localStorage
    // function getCartData() {
    //     let cartData = localStorage.getItem("cart");
    //     return cartData ? JSON.parse(cartData) : [];
    // }
    //
    // // Function to update cart data in localStorage
    // function saveCartData(cartData) {
    //     console.log('Saving cart data:', cartData); // Debugging log
    //     localStorage.setItem("cart", JSON.stringify(cartData));
    // }


    // // Function to update the total price
    // function updateTotalPrice() {
    //     let totalPrice = 0;
    //
    //     // Loop through each product to calculate total
    //     document.querySelectorAll('.product-price').forEach((priceElement) => {
    //         let productPrice = parseFloat(priceElement.getAttribute('data-price'));
    //         totalPrice += productPrice;
    //     });
    //
    //     // Update the total price display
    //     const totalPriceElement = document.getElementById('total-price');
    //     totalPriceElement.setAttribute('data-price', totalPrice);
    //     totalPriceElement.textContent = formatPrice(totalPrice);
    // }

    // Function to load cart data from localStorage and populate quantities
    <%--function loadCartData() {--%>
    <%--    const cartData = getCartData(); // Get the cart data from localStorage--%>
    <%--    console.log('Loading cart data:', cartData); // Debugging log--%>

    <%--    cartData.forEach((product) => {--%>
    <%--        const quantityDisplay = document.getElementById(`quantity-${product.id}`);--%>
    <%--        const priceDisplay = document.getElementById(`price-${product.id}`);--%>
    <%--        if (quantityDisplay && priceDisplay) {--%>
    <%--            quantityDisplay.textContent = product.quantity;--%>
    <%--            priceDisplay.setAttribute('data-price', product.unitPrice * product.quantity);--%>
    <%--            priceDisplay.textContent = formatPrice(product.unitPrice * product.quantity);--%>
    <%--        }--%>
    <%--    });--%>

    <%--    // Update the total price--%>
    <%--    updateTotalPrice();--%>
    <%--}--%>


    // // Event listeners for quantity change
    // const decreaseBtns = document.querySelectorAll('.btn-decrease');
    // const increaseBtns = document.querySelectorAll('.btn-increase');
    // const quantityDisplays = document.querySelectorAll('.quantity');
    // const priceDisplays = document.querySelectorAll('.product-price');

    <%--decreaseBtns.forEach((btn, index) => {--%>
    <%--    btn.addEventListener('click', () => {--%>
    <%--        let productId = btn.getAttribute('data-id');--%>
    <%--        // let currentQuantity = parseInt(quantityDisplays[index].textContent);--%>
    <%--        // if (currentQuantity > 1) {--%>
    <%--        //     currentQuantity--;--%>
    <%--        //     quantityDisplays[index].textContent = currentQuantity;--%>
    <%--        //--%>
    <%--        //     // Update the price for the product--%>
    <%--        //     let unitPrice = parseFloat(priceDisplays[index].getAttribute('data-price')) / (currentQuantity + 1);--%>
    <%--        //     let newPrice = unitPrice * currentQuantity;--%>
    <%--        //     priceDisplays[index].setAttribute('data-price', newPrice);--%>
    <%--        //     priceDisplays[index].textContent = formatPrice(newPrice);--%>
    <%--        //--%>
    <%--        //     // Update the cart data--%>
    <%--        //     const cartData = getCartData();--%>
    <%--        //     const productIndex = cartData.findIndex(p => p.id === productId);--%>
    <%--        //     if (productIndex !== -1) {--%>
    <%--        //         cartData[productIndex].quantity = currentQuantity;--%>
    <%--        //     }--%>
    <%--        //     saveCartData(cartData);--%>
    <%--        //--%>
    <%--        //     // Update the total price--%>
    <%--        //     updateTotalPrice();--%>
    <%--        // }--%>
    <%--        fetch(`updateCart?pid=${productId}&action=up`, {--%>
    <%--            method: 'POST',--%>
    <%--        });--%>
    <%--    });--%>
    <%--});--%>

    // increaseBtns.forEach((btn, index) => {
    //     btn.addEventListener('click', () => {
    //         let productId = btn.getAttribute('data-id');
    //         let currentQuantity = parseInt(quantityDisplays[index].textContent);
    //         currentQuantity++;
    //         quantityDisplays[index].textContent = currentQuantity;
    //
    //         // Update the price for the product
    //         let unitPrice = parseFloat(priceDisplays[index].getAttribute('data-price')) / (currentQuantity - 1);
    //         let newPrice = unitPrice * currentQuantity;
    //         priceDisplays[index].setAttribute('data-price', newPrice);
    //         priceDisplays[index].textContent = formatPrice(newPrice);
    //
    //         // Update the cart data
    //         const cartData = getCartData();
    //         const productIndex = cartData.findIndex(p => p.id === productId);
    //         if (productIndex !== -1) {
    //             cartData[productIndex].quantity = currentQuantity;
    //         }
    //         saveCartData(cartData);
    //
    //         // Update the total price
    //         updateTotalPrice();
    //     });
    // });

    // Load cart data when the page is loaded
    // document.addEventListener("DOMContentLoaded", loadCartData);
</script>
<script>
    document.querySelectorAll('.btn-increase, .btn-decrease').forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();

            const productDetailId = this.getAttribute('data-id');
            const quantity = this.getAttribute('data-quantity');

            console.log("Gửi:", productDetailId, quantity); // Debug client-side

            if (!productDetailId || !quantity) {
                console.error("Thiếu dữ liệu!");
                return;
            }

            // Tạo đối tượng URLSearchParams để mã hóa đúng định dạng
            const formData = new URLSearchParams();
            formData.append("action", "update");
            formData.append("productDetailId", productDetailId);
            formData.append("quantity", quantity);

            fetch('cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
                .then(res => {
                    if (!res.ok) throw new Error("Lỗi phản hồi từ server");
                    return res.text();
                })
                .then(data => {
                    console.log("Server trả về:", data);
                    // Reload lại trang để cập nhật giỏ hàng
                    location.reload();
                })
                .catch(err => console.error("Lỗi:", err));
        });
    });
</script>
<script>
    document.querySelectorAll('.btn-remove').forEach(btn => {
        btn.addEventListener('click', function (e) {
            e.preventDefault();

            const cartDetailId = this.getAttribute('data-cart-detail-id');

            if (!cartDetailId) {
                console.error("Thiếu cartDetailId!");
                return;
            }

            const formData = new URLSearchParams();
            formData.append("action", "remove");
            formData.append("cartDetailId", cartDetailId);

            fetch('cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            })
                .then(res => {
                    if (!res.ok) throw new Error("Server trả lỗi");
                    return res.text();
                })
                .then(data => {
                    console.log("Đã xóa khỏi giỏ hàng");
                    location.reload();
                })
                .catch(err => console.error("Lỗi xóa sản phẩm:", err));
        });
    });
</script>

<!-- base js -->
<script src="./assets/js/base.js"></script>
</body>

</html>