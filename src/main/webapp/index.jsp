<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService" %>
<%@ page import="vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/responsive_luat.css">
</head>

<body>

<c:if test="${not empty sessionScope.errorMessage}">
    <script>
        alert('${sessionScope.errorMessage}');
    </script>
    <%-- Xóa message sau khi hiển thị để không show lại khi reload --%>
    <c:remove var="errorMessage" scope="session" />
</c:if>

    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    <!-- Make a space to split the other out of header -->
    <div style="height: 12rem;"></div>

    <!-- Carousel -->
    <div id="customCarousel" class="carousel slide" data-ride="carousel">
        <!-- Carousel Inner -->
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="${pageContext.request.contextPath}/assets/imgs/HomePage/carousel/5.png" class="d-block w-100" alt="Slide 1">
            </div>
            <div class="carousel-item">
                <img src="${pageContext.request.contextPath}/assets/imgs/HomePage/carousel/6.png" class="d-block w-100" alt="Slide 2">
            </div>
        </div>

        <!-- Carousel captions -->
        <div class="carousel_caption">
            <div class="carousel_caption">
                <h5>
                    <c:if test="${sessionScope.auth != null}">
                        <c:choose>
                            <c:when test="${sessionScope.auth['class'].simpleName eq 'User'}">
                                <h1>Chào mừng ${sessionScope.auth.firstName} đến với trang web của chúng tôi</h1>
                            </c:when>
                            <c:otherwise>
                                <%-- Xử lý khi auth không phải là User object --%>
                                <h1>Chào mừng đến với trang web của chúng tôi</h1>
                                <%
                                    // Log lỗi hoặc xóa session attribute không hợp lệ
                                    session.removeAttribute("auth");
                                %>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </h5>
                <p></p>
                <a href="#" class="btn btn-link">Discover</a>
            </div>

        <!-- Controls -->
        <a class="carousel-control-prev" href="#customCarousel" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#customCarousel" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!-- Sale products -->
    <section id="section_sale">
        <div class="container">
            <h2>Giảm giá lên đến 70% cho ngày Black Friday</h2>
            <div class="row">
                <c:forEach var="p" items="${saleProducts}" end="3">
                    <!-- Item 1 -->
                    <div class="col-md-3">
                        <a href="productDetail?pid=${p.id}" class="item-link">
                            <div class="item">
                                <img src="${pageContext.request.contextPath}/assets/product-imgs/${p.details[0].image}" alt="${p.productName}">
                                <h4>${p.productName}</h4>
                                <div class="product-price" data-price="${p.unitPrice}"></div>
                            </div>
                        </a>
                    </div>
                </c:forEach>

                <c:if test="${empty saleProducts}">
                    <h1>không có sản phẩm.</h1>
                </c:if>
            </div>
        </div>
    </section>

    <!-- Collections -->
    <section id="section_banner_collection" class="container-fluid p-0">
        <img src="${pageContext.request.contextPath}/assets/imgs/HomePage/collections/banner/1.jpg" alt="" class="w-100">
    </section>
    <section id="section_collection">
        <div class="container">
            <!-- Collection Header -->
            <div class="collection-header text-left mb-4">
                <h2>Bộ sưu tập theo mùa</h2>
                <h3>Sản phẩm theo mùa thời trang nhất năm 2024</h3>
                <a href="" class="see-more">Xem thêm</a>
            </div>
            <!-- Product Grid -->
            <div class="row product-grid mt-5">
                <div class="col-md-4 col-lg-3 mb-4">
                    <div class="product-item">
                        <img src="${pageContext.request.contextPath}/assets/imgs/HomePage/collections/1.png" alt="Product 1">
                        <div class="product-price" data-price="1086207"></div>
                    </div>
                </div>
                <div class="col-md-4 col-lg-3 mb-4">
                    <div class="product-item">
                        <img src="${pageContext.request.contextPath}/assets/imgs/HomePage/collections/2.png" alt="Product 2">
                        <div class="product-price" data-price="2240896"></div>
                    </div>
                </div>
                <div class="col-md-4 col-lg-3 mb-4">
                    <div class="product-item">
                        <img src="${pageContext.request.contextPath}/assets/imgs/HomePage/collections/3.png" alt="Product 3">
                        <div class="product-price" data-price="318302"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- New arrivals -->
    <section id="section_new_arrivals">
        <div class="container">
            <h2>Sản phẩm bán chạy và được yêu thích nhiều nhất</h2>
            <div class="row">
                <c:forEach var="p" items="${bestSellingProducts}" end="2">
                    <!-- Item 1 -->
                    <div class="col-md-4">
                        <a href="productDetail?pid=${p.id}" class="item">
                            <img src="${pageContext.request.contextPath}/assets/product-imgs/${p.details[0].image}" alt="Image 1">
                            <h4>${p.productName}</h4>
                            <p><f:formatNumber value="${p.unitPrice}"/> đ</p>
                        </a>
                    </div>
                </c:forEach>

                <c:if test="${empty bestSellingProducts}">
                    <h1>không có sản phẩm.</h1>
                </c:if>
            </div>
        </div>
    </section>

    <footer id="footer"><%@ include file="assets/includes/footer.jsp" %></footer>

    <%@ include file="assets/includes/foot.jsp"%>
    <!-- Javascript Native -->
    <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>

</html>