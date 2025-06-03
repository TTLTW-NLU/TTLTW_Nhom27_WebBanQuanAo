<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp" %>
    <title>Product Detail</title>
    <link rel="stylesheet" href="assets/css/products_detail.css">
    <link rel="stylesheet" href="assets/css/responsive_luat.css">
    <style>
        .comment-container {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 0.5rem;
            padding: 15px;
            margin: 0 auto;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        .comment-container label {
            font-size: 14px;
            font-weight: bold;
            margin-top: 10px;
            display: block;
            color: #333;
        }

        .comment-container textarea {
            width: 100%;
            height: 150px;
            border: 1px solid #ccc;
            border-radius: 5px;
            padding: 10px;
            font-size: 14px;
            resize: vertical;
            box-sizing: border-box;
            outline: none;
        }

        .comment-container textarea:focus {
            /*border-color: #ff6600;*/
            box-shadow: 0 0 5px rgba(255, 102, 0, 0.5);
        }

        .comment-container select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            background-color: #fff;
            outline: none;
        }

        .comment-container input[type="file"] {
            display: none;
        }

        .comment-container label[for="image"] {
            display: inline-block;
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            background-color: #fff;
            text-align: center;
            cursor: pointer;
            color: #333;
            transition: background-color 0.3s ease, box-shadow 0.3s ease;
        }

        .comment-container label[for="image"]:hover {
            background-color: var(--lighter1-second-color);
            color: #fff;
            /*box-shadow: 0 0 5px rgba(255, 102, 0, 0.5);*/
        }

        .comment-container label[for="image"]:active {
            background-color: var(--lighter1-second-color);
            box-shadow: inset 0 3px 5px rgba(0, 0, 0, 0.2);
        }

        .comment-container select,
        .comment-container label[for="image"] {
            width: 100%;
            box-sizing: border-box;
        }

        .file-name {
            display: block;
            margin-top: 5px;
            font-size: 14px;
            color: #555;
            font-style: italic;
        }

        .comment-container .comment-button {
            background-color: var(--lighter-second-color);
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        .comment-container .comment-button:hover {
            /*background-color: #e55a00;*/
            transform: translateY(-2px);
        }

        .comment-container .comment-button:active {
            transform: translateY(0);
            box-shadow: none;
        }

        .comment-container .w-100 {
            width: 100%;
        }
    </style>
</head>

<body>
<header id="header">
    <%@ include file="assets/includes/header.jsp" %>
</header>
<!-- Make a space to split the other out of header -->
<div style="height: 12rem;"></div>

<!-- Products -->
<section id="section_products" class="row container mx-auto mt-4">
    <!-- Product Gallery -->
    <div class="col-md-6 product-gallery">
        <div class="thumbnail-list">
            <c:forEach var="i" items="${p.details}" end="2">
                <img src="assets/product-imgs/${i.image}" alt="Thumbnail">
            </c:forEach>
        </div>
        <div id="mainCarousel" class="carousel slide main-image">
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="assets/product-imgs/${p.details[0].image}" class="d-block w-100" alt="Main Image">
                </div>
                <div class="carousel-item">
                    <img src="assets/product-imgs/${p.details[1].image}" class="d-block w-100" alt="Main Image">
                </div>
                <div class="carousel-item">
                    <img src="assets/product-imgs/${p.details[2].image}" class="d-block w-100" alt="Main Image">
                </div>
            </div>
            <a class="carousel-control-prev" href="#mainCarousel" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            </a>
            <a class="carousel-control-next" href="#mainCarousel" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
            </a>
        </div>
    </div>
    <!-- Product Info -->
    <div class="col-md-6 product-info">

        <h1>${p.productName}</h1>
        <div class="price"><f:formatNumber value="${p.unitPrice}"/> đ</div>

        <form id="add-cart-form" action="cart" method="post" class="mt-5">
            <!-- Color options -->
            <div class="color-options">
                <h5>Choose color:</h5>
                <div class="choose_color mt-3">
                    <c:forEach var="color" items="${uniqueColors}">
                        <label>
                            <input class="d-none" type="radio" name="color" value="${color}">
                            <span>${color}</span>
                        </label>
                    </c:forEach>
                </div>
            </div>

            <!-- Size options -->
            <div class="size-options mt-4">
                <h5>Choose size:</h5>
                <div class="choose_size mt-3">
                    <c:forEach var="size" items="${uniqueSizes}">
                        <label>
                            <input class="d-none" type="radio" name="size" value="${size}">
                            <span>${size}</span>
                        </label>
                    </c:forEach>
                </div>
            </div>

            <!-- Quantity -->
            <div class="quantity-selector mt-4">
                <h5>Quantity:</h5>
                <button type="button" class="btn btn-outline-secondary ml-3" id="decrease-btn">-</button>
                <input type="number" name="quantity" value="1" id="quantity-input" required>
                <button type="button" class="btn btn-outline-secondary" id="increase-btn">+</button>
            </div>

            <!-- Alert Message -->
            <h4 id="alert-message" class="d-none mt-3" style="color: red">Vui lòng chọn màu và kích cỡ</h4>

            <!-- Hidden input -->
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="productId" value="${p.id}">
            <input type="hidden" name="unitPrice" value="${p.unitPrice}">
            <input type="hidden" name="couponId" value="1">

            <!-- Buttons -->
            <div class="mt-5">
                <button type="submit" class="btn btn-primary mt-4 add_to_cart_btn">Thêm vào giỏ hàng</button>
            </div>
        </form>

        <%
            String message = (String) session.getAttribute("message");
            if (message != null) {
        %>
        <!-- Popup overlay -->
        <div class="addOk-overlay"></div>
        <!-- Popup content -->
        <div class="addOk" id="addToCartPopup" data-message="<%= message %>">
            <img alt="green-tick" src="assets/imgs/Green-Tick.png">
            <h3 class="mt-3"><%= message %></h3>
        </div>
        <%
                session.removeAttribute("message");
            }
        %>

    </div>
</section>

<!-- Description -->
<section id="section_description" class="container mx-auto">
    <h1 class="mb-5 pb-4 text-center">Thông tin chi tiết sản phẩm</h1>
    <div class="content">
        <p class="text_content">
            ${p.description}
        </p>
    </div>
</section>

<!-- Comments -->
<section id="section_comments" class="container mx-auto">
    <h1 class="mb-5 pb-4 text-center">Đánh giá từ những khách hàng đã mua</h1>
    <!-- Review by customer -->
    <div class="review-container container">
        <c:forEach var="c" items="${comments}">
            <div class="item">
                <div class="user-info">
                    <img src="assets/imgs/products%20detail/1.png" alt="User Avatar" class="avatar">
                    <div class="user-name">
                        <span>${c.authorName}</span>
                    </div>
                </div>
                <div class="rating">
                    <c:forEach var="i" begin="1" end="${c.rating}">
                        <i class="fa fa-star" style="color: gold;"></i>
                    </c:forEach>
                    <c:forEach var="i" begin="${c.rating + 1}" end="5">
                        <i class="fa fa-star" style="color: #ccc;"></i>
                    </c:forEach>
                </div>
                <div class="review-content">
                    <p>${c.content}</p>
                </div>
                <div class="review-media">
                    <img src="assets/imgs/products%20detail/2.webp" alt="Review Image 1">
                    <img src="assets/imgs/products%20detail/3.jpg" alt="Review Image 2">
                </div>
            </div>
        </c:forEach>

        <c:if test="${empty comments}">
            <p class="text_content" style="font-size: 1.5rem">Hiện chưa có đánh giá nào về sản phẩm này.</p>
        </c:if>
    </div>
    <!-- Customer comment here -->
    <c:if test="${not empty sessionScope.auth}">
        <form action="productDetail" method="post" class="comment-container container mt-3">
            <textarea rows="20" cols="20" name="description" style="resize: none"></textarea>

            <label for="rating">Đánh giá</label>
            <select name="rating" id="rating" required>
                <option value="" disabled selected>Chọn số sao</option>
                <option value="1">1 sao</option>
                <option value="2">2 sao</option>
                <option value="3">3 sao</option>
                <option value="4">4 sao</option>
                <option value="5">5 sao</option>
            </select>

                <%--        <span id="file-name" class="file-name">Chưa có tệp nào được chọn</span>--%>
                <%--        <label for="image">Tải lên hình ảnh</label>--%>
                <%--        <input type="file" name="image" id="image" accept="image/*">--%>

            <input type="hidden" name="userId" value="${sessionScope.auth.id}">
            <input type="hidden" name="pid" value="${p.id}">

            <button type="submit" name="comment-button" class="comment-button w-100 mt-3">Gửi đánh giá</button>
        </form>
    </c:if>
</section>

<!-- Similar -->
<section id="section_similar" class="container similar_container">
    <h2 class="position-relative mb-5">Sản phẩm tương tự</h2>
    <div id="carousel_similar" class="carousel slide">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <div class="row">
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/1.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/2.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/3.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/1/4.png" alt=""></div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/1.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/2.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/3.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/2/4.png" alt=""></div>
                </div>
            </div>
            <div class="carousel-item">
                <div class="row">
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/1.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/2.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/3.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/4.png" alt=""></div>
                    <div class="col-md-2 item"><img src="assets/imgs/men/list%20products/3/4.png" alt=""></div>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-target="#carousel_similar" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-target="#carousel_similar" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </button>
    </div>
</section>

<footer id="footer">
    <%@ include file="assets/includes/footer.jsp" %>
</footer>

<!-- jQuery, Popper.js, and Bootstrap 4.6.2 JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/bootstrap-4.6.2/js/bootstrap.min.js"></script>

<!-- Javascript Native -->
<script src="${pageContext.request.contextPath}/assets/js/base.js"></script>
<script>
    // Alert add to cart
    window.addEventListener('DOMContentLoaded', function () {
        const popup = document.getElementById('addToCartPopup');
        const overlay = document.querySelector('.addOk-overlay');

        if (popup && popup.dataset.message && popup.dataset.message.trim() !== "") {
            popup.classList.add('show');
            if (overlay) overlay.classList.add('show');

            setTimeout(() => {
                popup.remove();
                if (overlay) overlay.remove();
            }, 500);
        }
    });

    // Transition for Header
    window.addEventListener("scroll", () => {
        if (window.scrollY > 50) {
            header.classList.add("scrolled");
        } else {
            header.classList.remove("scrolled");
        }
    });

    // // Get the current URL
    // const urlParams = new URLSearchParams(window.location.search);
    // // Check if 'addCart=ok' is in the URL
    // if (urlParams.get('addCart') === 'ok') {
    //     // Show the popup
    //     const popup = document.getElementById('addToCartPopup');
    //     const overlay = document.querySelector('.addOk-overlay');
    //     popup.classList.add('show');
    //     overlay.classList.add('show');
    //
    //     // Auto-close popup after 3 seconds
    //     setTimeout(() => {
    //         popup.classList.remove('show');
    //         overlay.classList.remove('show');
    //     }, 1500);
    // }

    // Alert if user didn't choose color or size
    document.getElementById('add-cart-form').addEventListener('submit', function (event) {
        const colorSelected = document.querySelector('input[name="color"]:checked');
        const sizeSelected = document.querySelector('input[name="size"]:checked');
        const alertMessage = document.getElementById('alert-message');

        if (!colorSelected || !sizeSelected) {
            event.preventDefault();
            alertMessage.classList.remove('d-none');
        } else {
            alertMessage.classList.add('d-none');
        }
    });

    // Quantity
    const decreaseBtn = document.getElementById("decrease-btn");
    const increaseBtn = document.getElementById("increase-btn");
    const quantityInput = document.getElementById("quantity-input");
    increaseBtn.addEventListener("click", () => {
        let currentValue = parseInt(quantityInput.value, 10);
        quantityInput.value = currentValue + 1;
    });
    decreaseBtn.addEventListener("click", () => {
        let currentValue = parseInt(quantityInput.value, 10);
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
        }
    });

    // Alert add file in comment
    document.getElementById('image').addEventListener('change', function () {
        const fileName = this.files[0] ? this.files[0].name : "Chưa có tệp nào được chọn";
        document.getElementById('file-name').textContent = fileName;
    });

    // Alert add to cart
    <%--window.addEventListener('DOMContentLoaded', function () {--%>
    <%--    // Lấy thông báo từ session thông qua JSP EL--%>
    <%--    const message = '<c:out value="${sessionScope.message}" default=""/>';--%>

    <%--    // Nếu có thông báo thì hiển thị popup hoặc alert--%>
    <%--    if (message) {--%>
    <%--        // Hiển thị popup "Sản phẩm đã được thêm vào giỏ hàng"--%>
    <%--        const popup = document.getElementById('addToCartPopup');--%>
    <%--        const overlay = document.querySelector('.addOk-overlay');--%>
    <%--        if (popup && overlay) {--%>
    <%--            popup.classList.add('show'); // bạn cần style .show { display: block; hoặc opacity: 1; }--%>
    <%--            overlay.classList.add('show');--%>

    <%--            // Ẩn sau vài giây--%>
    <%--            setTimeout(() => {--%>
    <%--                popup.classList.remove('show');--%>
    <%--                overlay.classList.remove('show');--%>
    <%--            }, 1500);--%>
    <%--        } else {--%>
    <%--            // Hoặc fallback: alert đơn giản--%>
    <%--            alert(message);--%>
    <%--        }--%>
    <%--    }--%>
    <%--});--%>
</script>
<script src="${pageContext.request.contextPath}/assets/js/products_detail.js"></script>
</body>

</html>