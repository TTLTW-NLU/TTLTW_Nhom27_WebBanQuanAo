<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="./assets/css/sale.css">
    <link rel="stylesheet" href="./assets/css/responsive_luat.css">
</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    <!-- Make a space to split the other out of header -->
    <div style="height: 12rem;"></div>

    <!-- Banner -->
    <section id="section_banner" class="container-fluid p-0">
        <img src="assets/imgs/sale/banner/1.png" alt="" class="w-100">
    </section>

    <!-- Collection -->
    <div class="collect">
        <div class="clothers">
            <div class="container-fluid grid">
                <div class="tw-container-full collections-head">
                    <h2 class="clothes__title">Sản phẩm khuyến mãi</h2>
                    <div class="tab-header tab-collection-get-header collection-filter__header">
                        <a href="#" rel-script="filter-item" data-param-value="all" id="all"
                            class="tab-header__item collection-filter__item is-active">Tất cả</a>
                        <a href="#" rel-script="filter-item" data-param-value="ao-moi" id="ao-moi"
                            class="tab-header__item collection-filter__item">Áo</a>
                        <a href="#" rel-script="filter-item" data-param-value="quan-moi" id="quan-moi"
                            class="tab-header__item collection-filter__item">Quần</a>
                        <a href="#" rel-script="filter-item" data-param-value="phu-kien-moi" id="phu-kien-moi"
                            class="tab-header__item collection-filter__item">Phụ kiện</a>
                    </div>
                    <div class="collections-head__divider"></div>
                </div>

                <div class="top">
                    <div class="filter">
                        <h2>8 Kết Quả</h2>
                    </div>
                    <div class="sort">
                        <span class="text-sort">SẮP XẾP
                            THEO</span>

                        <div rel-script="dropdown" id="sorting" class="">
                            <select id="sortBy" class="custom-select">
                                <option value="default">Mặc Định</option>
                                <option value="priceAsc">Giá Thấp Đến Cao</option>
                                <option value="priceDesc">Giá Cao Đến Thấp</option>
                                <option value="newest">Mới Nhất</option>
                                <option value="bestSeller">Bán Chạy</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="container ">
                    <div class="row g-3">
                        <!-- Product Item 1 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-1/24CMCW.SM008_-_Xanh_Blue_Night_1.webp"
                                            alt="Áo sơ mi dài tay cổ tàu Premium Poplin" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-1/24CMCW.SM008_-_Xanh_Blue_Night_6.webp"
                                            alt="Áo sơ mi dài tay cổ tàu Premium Poplin">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Áo sơ mi dài tay cổ tàu Premium Poplin</p>
                                    </a>

                                    <div data-price="250000"></div>

                                </div>
                            </div>
                        </div>

                        <!-- Product Item 2 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-2/24CMCW.KM005_-_Navy_1.webp"
                                            alt="Áo phao dày Ultrawarm Puffer có mũ" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-2/24CMCW.KM005_-_Navy_7.webp"
                                            alt="Áo phao dày Ultrawarm Puffer có mũ">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p> Áo phao Ultrawarm Puffer có mũ</p>
                                    </a>
                                    <div data-price="300000"></div>
                                </div>
                            </div>
                        </div>

                        <!-- Product Item 3 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-3/24CMAW.QD006_-_IMG_3727-_BE_KAKI.webp"
                                            alt="Quần Jogger thể thao Fleece Track Pants Premium" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-3/24CMAW.QD006_-_IMG_3775-_BE_KAKI.webp"
                                            alt="Quần Jogger thể thao Fleece Track Pants Premium">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Quần Jogger thể thao Fleece Track Pants Premium</p>
                                    </a>
                                    <div data-price="200000"></div>
                                </div>
                            </div>
                        </div>

                        <!-- Product Item 4 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-4/24CMCW.QJ001_-_Reu_1.webp"
                                            alt="Quần Jogger ECC Warp Knitting" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-4/24CMCW.QJ001_-_Reu_3.webp"
                                            alt="Quần Jogger ECC Warp Knitting">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Quần Jogger ECC Warp Knitting</p>
                                    </a>
                                    <div data-price="200000"></div>
                                </div>
                            </div>
                        </div>
                        <!-- Product Item 5 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-5/24CMCW.SM007_-_Xam_3.webp"
                                            alt="Áo sơ mi dài tay Modal Essential" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-5/24CMCW.SM007_-_Xam_7.webp"
                                            alt="Áo sơ mi dài tay Modal Essential">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Áo sơ mi dài tay Modal Essential</p>
                                    </a>
                                    <div data-price="225000"></div>
                                </div>
                            </div>
                        </div>

                        <!-- Product Item 6 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-6/23CMCW.JE006.XAW.1.webp"
                                            alt="Quần Jeans Nam siêu nhẹ" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-6/Quan_Jeans_Nam_sieu_nhe.xanh_dam.jpg"
                                            alt="Quần Jeans Nam siêu nhẹ">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Quần Jeans Nam siêu nhẹ</p>
                                    </a>
                                    <div data-price="340000"></div>
                                </div>
                            </div>
                        </div>

                        <!-- Product Item 7 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-7/eccnaubrownUntitled-1.webp"
                                            alt="Quần Dài Nam ECC Warp Pants dáng Slim" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-7/tappered1_47.jpg"
                                            alt="Quần Dài Nam ECC Warp Pants dáng Slim">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Quần Dài Nam ECC Warp Pants dáng Slim</p>
                                    </a>
                                    <div data-price="289000"></div>
                                </div>
                            </div>
                        </div>
                        <!-- Product Item 8 -->
                        <div class="col-12 col-sm-6 col-md-4 col-lg-3">
                            <div class="space-items">
                                <div class="product-grid__image" rel-script="product-grid-thumbnails">
                                    <a href="products_detail.jsp">
                                        <img loading="lazy"
                                            src="assets/imgs/New-Product/Product-8/TCTCRIBCM_3_31.webp"
                                            alt="Combo 2 đôi Tất cổ trung Cotton Ribbed Coolmate" class="home-banner">
                                        <img class="hover" loading="lazy"
                                            src="assets/imgs/New-Product/Product-8/TCTCRIBCM_IMG_4298_XAM_MEL_7.webp"
                                            alt="Combo 2 đôi Tất cổ trung Cotton Ribbed Coolmate">
                                    </a>
                                </div>

                                <div class="product-decrip">
                                    <a href="products_detail.jsp">
                                        <p>Combo 2 đôi Tất cổ trung Cotton Ribbed Coolmate</p>
                                    </a>
                                    <div data-price="20000"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="collections-head__divider"></div>
                </div>
                <div class="collection-section-loadmore" style>

                    <a href="index.jsp" rel-script="product-loadmore" data-current-page="1" data-last-page="2"
                       class="collection-section-loadmore__btn btn">Xem thêm </a>
                    <!-- <p class="collection-section-loadmore__page" style="text-align: center;">
                        Hiển thị <span>1</span> - <span class="loadmore-total">8</span>
                        trên tổng số <span class="total-products">33</span> sản phẩm
                    </p> -->
                </div>
                <div class="collection-section__foot">
                    <section class="homepage-collections tw-container-full">
                        <div
                            class="homepage-collections__wrapper grid grid--four-columns tablet-grid--four-columns mobile-grid--two-columns">
                            <!-- item 1 -->
                            <div class="homepage-collections__item grid__column">
                                <a href="collection-winter.jsp" ga-tracking-value="bannercat-vitri1"
                                   ga-tracking-label="Đồ thu đông" class="bannercat-vitri1">
                                    <div class="tw-overflow-hidden tw-rounded-lg md:tw-rounded-2xl xl:tw-rounded-3xl">
                                        <picture>
                                            <source media="(max-width: 768px)"
                                                srcset="assets/imgs/New-Product/Collection/mceclip2_10.webp">
                                            <img loading="lazy"
                                                src="assets/imgs/New-Product/Collection/mceclip2_10.webp"
                                                alt="Đồ thu đông"
                                                class="tw-transform tw-transition tw-duration-500 hover:tw-scale-105"
                                                style="width: 100%;">

                                        </picture>
                                    </div>
                                </a>
                            </div>
                            <!-- item 2 -->
                            <div class="homepage-collections__item grid__column">
                                <a href="collection-running.jsp" ga-tracking-value="bannercat-vitri1"
                                   ga-tracking-label="Đồ thể thao" class="bannercat-vitri1">
                                    <div class="tw-overflow-hidden tw-rounded-lg md:tw-rounded-2xl xl:tw-rounded-3xl">
                                        <picture>
                                            <source media="(max-width: 768px)"
                                                srcset="assets/imgs/New-Product/Collection/mceclip1_30.webp">
                                            <img loading="lazy"
                                                src="assets/imgs/New-Product/Collection/mceclip1_30.webp"
                                                alt="Đồ thể thao"
                                                class="tw-transform tw-transition tw-duration-500 hover:tw-scale-105"
                                                style="width: 100%;">

                                        </picture>
                                    </div>
                                </a>
                            </div>
                            <!-- item 3 -->
                            <div class="homepage-collections__item grid__column">
                                <a href="collection-summer.jsp" ga-tracking-value="bannercat-vitri1"
                                   ga-tracking-label="Mặc hằng ngày" class="bannercat-vitri1">
                                    <div class="tw-overflow-hidden tw-rounded-lg md:tw-rounded-2xl xl:tw-rounded-3xl">
                                        <picture>
                                            <source media="(max-width: 768px)"
                                                srcset="assets/imgs/New-Product/Collection/mceclip0_55.webp">
                                            <img loading="lazy"
                                                src="assets/imgs/New-Product/Collection/mceclip0_55.webp"
                                                alt="Mặc hằng ngày"
                                                class="tw-transform tw-transition tw-duration-500 hover:tw-scale-105"
                                                style="width: 100%;">

                                        </picture>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>

    <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>

    <!-- Javascript Native -->
    <script src="./assets/js/main.js"></script>
    <%@ include file="assets/includes/foot.jsp"%>
</body>

</html>