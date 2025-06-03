<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="assets/includes/head.jsp" />
    <title>Search</title>
    <link rel="stylesheet" href="assets/css/search.css">
    <link rel="stylesheet" href="assets/css/responsive_luat.css">
</head>

<body>
<header id="header">
    <jsp:include page="assets/includes/header.jsp"/>
</header>
<!-- Make a space to split the other out of header -->
<div style="height: 12rem;"></div>

<!-- Search Popup -->
<div class="search-popup" id="search-popup">
    <div class="search-box">
        <i class="fas fa-search search-icon"></i>
        <input oninput="searchByName(this)" type="text" placeholder="Nhập từ khoá sản phẩm bạn cần tìm...">
    </div>
</div>

<!-- List products -->
<section id="section_all_products">
    <div class="list_products">
        <div class="container mt-2">
            <div class="row">
                <!-- Product Card 1 -->
                <c:forEach var="p" items="${allProducts}">
                    <div class="col-md-3 mt-4">
                        <a href="${pageContext.request.contextPath}/productDetail?pid=${p.id}" class="product-card">
                            <img src="assets/product-imgs/${p.details[0].image}" alt="${p.productName}" class="product-image img-fluid">
                            <div class="product-title">${p.productName}</div>
                            <div class="product-price" data-price="${p.unitPrice}"></div>
                        </a>
                    </div>
                </c:forEach>

                <c:if test="${empty allProducts}">
                    <h2 class="display-4 mt-5 text-center w-100">Không có sản phẩm nào.</h2>
                </c:if>
            </div>
        </div>
    </div>
</section>

<footer id="footer">
    <jsp:include page="assets/includes/footer.jsp"/>
</footer>

<!-- jQuery, Popper.js, and Bootstrap 4.6.2 JS -->
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@1.16.1/dist/umd/popper.min.js"></script>
<script src="./assets/bootstrap-4.6.2/js/bootstrap.min.js"></script>
<!-- Javascript Native -->
<script src="assets/js/base.js"></script>
<script>
    function searchByName(param) {
        var keyword = param.value;
        $.ajax({
            url: "/WebBanQuanAo/productSearch",
            type: "post",
            data: {
                keyword: keyword
            },
            success: function (data) {
                const productContainer = document.querySelector('.list_products .row');
                productContainer.innerHTML = '';
                productContainer.innerHTML += data;
            },
            error: function (xhr, status, error) {
                console.error("Lỗi khi gửi yêu cầu:", error);
            }
        });
    }
</script>
</body>

</html>