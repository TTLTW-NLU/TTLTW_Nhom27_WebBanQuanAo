<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="assets/includes/head.jsp"/>
    <title>Quần áo phụ nữ</title>
    <link rel="stylesheet" href="./assets/css/women.css">
    <link rel="stylesheet" href="./assets/css/responsive_luat.css">
</head>

<body>
<header id="header">
    <jsp:include page="assets/includes/header.jsp"/>
</header>
<!-- Make a space to split the other out of header -->
<div style="height: 12rem;"></div>

<!-- Banner -->
<section id="section_banner" class="container-fluid p-0">
    <img src="assets/imgs/women/banner/1.jpg" alt="" class="w-100">
</section>

<!-- Featured -->
<section id="section_featured" class="container text-center">
    <h2>Phong cách và sự thoải mái trong thời trang</h2>
    <p>Sản phẩm nổi bật được lựa chọn kỹ lưỡng, mang đến chất lượng và phong cách tốt nhất. Khám phá các sản phẩm được
        yêu thích nhất hiện giờ.</p>
    <a href="">Xem thêm</a>
    <div class="row mt-5">
        <div class="col-md-4"><img src="assets/imgs/women/featured/z6055383830362_23952c1c984be2fc022c0e59a65dd401.jpg"
                                   alt="" class="w-100"></div>
        <div class="col-md-4"><img src="assets/imgs/women/featured/z6055383815724_271cdb9c51b42106fb22c930c2f1ef24.jpg"
                                   alt="" class="w-100"></div>
        <div class="col-md-4"><img src="assets/imgs/women/featured/z6055383801137_92c75292a967b448dedc42affc14e3ea.jpg"
                                   alt="" class="w-100"></div>
    </div>
</section>

<!-- All products -->
<section id="section_all_products">
    <!-- Top -->
    <div class="container-fluid" style="border-bottom: solid 1px #000;    height: 5rem;">
        <div class="top">
            <div class="filter">
                <h2>Lọc sản phẩm</h2>
                <i class="fa-solid fa-filter custom_icon"></i>
            </div>
            <h1>Tất cả sản phẩm</h1>
        </div>
    </div>
    <!-- Bottom -->
    <div class="container-fluid">
        <div class="bottom">
            <!-- Filter -->
            <form action="productFilter" method="get" class="filter">
                <!-- Loại Sản Phẩm -->
                <div id="filter-category">
                    <h4>Loại Sản Phẩm <span class="arrow"><i
                            class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                    <div class="filter-content">
                        <label><input type="checkbox" name="type" value="Áo"> Áo</label>
                        <label><input type="checkbox" name="type" value="Quần"> Quần</label>
                    </div>
                </div>

                <!-- Kích Thước -->
                <div id="filter-size">
                    <h4>Kích Thước <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                    </h4>
                    <div class="filter-content">
                        <label><input type="checkbox" name="size" value="S"> S</label>
                        <label><input type="checkbox" name="size" value="M"> M</label>
                        <label><input type="checkbox" name="size" value="L"> L</label>
                        <label><input type="checkbox" name="size" value="XL"> XL</label>
                        <label><input type="checkbox" name="size" value="XXL"> XXL</label>
                        <label><input type="checkbox" name="size" value="3XL"> 3XL</label>
                        <label><input type="checkbox" name="size" value="4XL"> 4XL</label>
                    </div>
                </div>

                <!-- Giá -->
                <div id="filter-price">
                    <h4>Giá <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                    <div class="filter-content">
                        <input type="number" name="price-min" placeholder="Giá tối thiểu">
                        <input type="number" name="price-max" placeholder="Giá tối đa">
                        <select name="sortPrice" class="form-control">
                            <option value="">sắp xếp theo giá</option>
                            <option value="asc">Tăng dần</option>
                            <option value="desc">Giảm dần</option>
                        </select>
                    </div>
                </div>

                <input type="hidden" name="category" value="${category}">
                <!-- Button Submit -->
                <div class="filter-submit">
                    <button type="submit" class="btn btn-primary btn-block">Lọc sản phẩm</button>
                </div>
            </form>
            <!-- Right -->
            <div class="list_products">
                <div class="container mt-2">
                    <div class="row">
                        <!-- Product Card 1 -->
                        <c:forEach var="p" items="${allProducts}">
                            <div class="col-md-3 mt-4">
                                <a href="${pageContext.request.contextPath}/productDetail?pid=${p.id}"
                                   class="product-card">
                                    <img src="assets/product-imgs/${p.details[0].image}" alt="${p.productName}"
                                         class="product-image img-fluid">
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
                <!-- Pagination -->
                <div id="pagination" class="container mt-5">
                    <nav aria-label="Page navigation example">
                        <ul class="pagination custom-pagination justify-content-center">
                            <!-- Previous button -->
                            <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>">
                                <a class="page-link"
                                   href="navController?category=men&page=${currentPage - 1}"
                                   aria-label="Previous"
                                   id="previous-page">
                                    <i class="fa-solid fa-chevron-left"></i>
                                </a>
                            </li>

                            <!-- Page numbers -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item <c:if test="${i == currentPage}">active</c:if>">
                                    <a class="page-link"
                                       href="navController?category=men&page=${i}"
                                       type="button"
                                       data-page="${i}">
                                            ${i}
                                    </a>
                                </li>
                            </c:forEach>

                            <!-- Next button -->
                            <li class="page-item <c:if test="${currentPage == totalPages}">disabled</c:if>">
                                <a class="page-link"
                                   href="navController?category=men&page=${currentPage + 1}"
                                   aria-label="Next"
                                   id="next-page">
                                    <i class="fa-solid fa-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</section>

<footer id="footer">
    <jsp:include page="assets/includes/footer.jsp"/>
</footer>

<jsp:include page="assets/includes/foot.jsp"/>

</body>

</html>