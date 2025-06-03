<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Bộ sưu tập xuân hè</title>
    <link rel="stylesheet" href="./assets/css/collection-summer.css">
</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>

    <div class="collect">

        <!-- Start Collect intro -->
        <div class="collect_intro">
            <div class="collect_intro-right grid">
                <img class="collect_intro-img" src="assets/imgs/Collection-summer/item0.jpg" alt="Erro">
            </div>
            <div class="collect_intro-left grid">
                <div style="width: 90rem;">
                    <div class="collect-title">XUÂN HÈ CÙNG LASMANATE</div>
                    <p class="collect-text"> Bộ sưu tập mùa hè của LASMANATE năm nay là sự hòa quyện giữa vẻ đẹp tự
                        nhiên và
                        phong cách thời trang hiện đại. Lấy cảm hứng từ sắc màu rực rỡ và không khí phóng khoáng của mùa
                        hè,
                        bộ sưu tập mang đến các thiết kế với gam màu tươi sáng, chất liệu thoáng mát, giúp người mặc cảm
                        nhận sự thoải mái và tự tin trong từng chuyển động.
                    </p>
                </div>
            </div>
        </div>
        <!-- End collect intro -->


        <!-- Start collect marquee -->
        <div class="collect_marquee">
            <div class="collect_marquee-logo">LASMANATE</div>
            <div class="collect_marquee-text">
                <p>LASMANATE XUÂN HÈ<span> KHÁM PHÁ SỰ TƯƠI TẮN CỦA LASMANATE MÙA XUÂN HÈ </span> LASMANATE XUÂN HÈ</p>
            </div>
        </div>
        <!-- End collect marquee -->

        <!-- Start collect winter clothers -->
        <!-- All products -->
        <section id="section_all_products">
            <h2 class="running-clothes__title" style="margin-left: 3rem;">BỘ SƯU TẦM MÙA HÈ</h2>
            <!-- <div class="running_product-btns">
                <div class="running_product-btn">Tất cả</div>
                <div class="running_product-btn">Mặc trong</div>
                <div class="running_product-btn">Mặc ra đường</div>
                <div class="running_product-btn">Mặc vận động</div>
            </div> -->

            <!-- Top -->
            <div class="container-fluid" style="border-bottom: solid 1px #000;  height: 5rem; ">
                <div class="top">
                    <div class="filter">
                        <h2>Lọc sản phẩm</h2>
                        <i class="fa-solid fa-filter custom_icon"></i>
                    </div>
                    <h1>Tất cả</h1>
                    <div class="sort">
                        <label for="sortBy">Sắp xếp theo:</label>
                        <select id="sortBy" class="custom-select">
                            <option value="default">Chọn tiêu chí</option>
                            <option value="priceAsc">Giá: Thấp đến Cao</option>
                            <option value="priceDesc">Giá: Cao đến Thấp</option>
                            <option value="newest">Sản Phẩm Mới Nhất</option>
                            <option value="bestSeller">Bán Chạy Nhất</option>
                            <option value="highestRated">Đánh Giá Cao Nhất</option>
                        </select>
                    </div>
                    <div class="sort-mobile">
                        <label for="sortBy-mobile">Bộ lọc:</label>
                        <select id="sortBy-mobile" class="custom-select-mobile">
                            <option value="default">Áo</option>
                            <option value="priceAsc">Quần dài</option>
                            <option value="priceDesc">Áo dài tay</option>
                            <option value="newest">Sơ mi</option>
                        </select>
                    </div>
                </div>
            </div>
            <!-- Bottom -->
            <div class="bottom container-fluid">
                <!-- Filter -->
                <div class="filter">
                    <!-- Loại Sản Phẩm -->
                    <div id="filter-category">
                        <h4>Loại Sản Phẩm <span class="arrow"><i
                                    class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="category" value="Shirts"> Áo</label>
                            <label><input type="checkbox" name="category" value="Pants"> Quần</label>
                            <label><input type="checkbox" name="category" value="Jackets"> Áo khoác</label>
                            <label><input type="checkbox" name="category" value="Accessories"> Phụ kiện</label>
                            <label><input type="checkbox" name="category" value="Shoes"> Giày</label>
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
                        </div>
                    </div>

                    <!-- Màu Sắc -->
                    <div id="filter-color">
                        <h4>Màu Sắc <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="color" value="Black"> Đen</label>
                            <label><input type="checkbox" name="color" value="White"> Trắng</label>
                            <label><input type="checkbox" name="color" value="Blue"> Xanh</label>
                            <label><input type="checkbox" name="color" value="Gray"> Xám</label>
                        </div>
                    </div>

                    <!-- Giá -->
                    <div id="filter-price">
                        <h4>Giá <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span></h4>
                        <div class="filter-content">
                            <input type="number" name="price-min" placeholder="Giá tối thiểu">
                            <input type="number" name="price-max" placeholder="Giá tối đa">
                            <select name="price-sort">
                                <option value="low-to-high">Thấp đến cao</option>
                                <option value="high-to-low">Cao đến thấp</option>
                            </select>
                        </div>
                    </div>

                    <!-- Thương Hiệu -->
                    <div id="filter-brand">
                        <h4>Thương Hiệu <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="brand" value="Gucci"> Gucci</label>
                            <label><input type="checkbox" name="brand" value="Louis"> Louis Vuitton</label>
                            <label><input type="checkbox" name="brand" value="Balenciaga"> Balenciaga</label>
                            <label><input type="checkbox" name="brand" value="Dior"> Dior</label>
                            <label><input type="checkbox" name="brand" value="Chanel"> Chanel</label>
                        </div>
                    </div>

                    <!-- Chất Liệu -->
                    <div id="filter-material">
                        <h4>Chất Liệu <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="material" value="Cotton"> Cotton</label>
                            <label><input type="checkbox" name="material" value="Wool"> Len</label>
                            <label><input type="checkbox" name="material" value="Leather"> Da</label>
                            <label><input type="checkbox" name="material" value="Polyester"> Polyester</label>
                        </div>
                    </div>

                    <!-- Phong Cách -->
                    <div id="filter-style">
                        <h4>Phong Cách <span class="arrow"><i class="fa-solid fa-chevron-down custom_icon"></i></span>
                        </h4>
                        <div class="filter-content">
                            <label><input type="checkbox" name="style" value="Sporty"> Thể thao</label>
                            <label><input type="checkbox" name="style" value="Business"> Công sở</label>
                            <label><input type="checkbox" name="style" value="Casual"> Thường ngày</label>
                            <label><input type="checkbox" name="style" value="High-fashion"> Thời trang cao
                                cấp</label>
                        </div>
                    </div>
                </div>
                <!-- Right -->
                <div class="list_products">
                    <div class="container" style="margin: 0; padding: 0;">
                        <div class="product-list" style="display: flex; justify-content: center; align-items: center;">
                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/1.webp" alt="">
                                <!-- <div class="clothes-size">
                                    <p>Thêm nhanh vào giỏ hàng.</p>
                                    <div>
                                        <div class="btn-size btn btn-outline-dark">S</div>
                                        <div class="btn-size btn btn-outline-dark">M</div>
                                        <div class="btn-size btn btn-outline-dark">L</div>
                                        <div class="btn-size btn btn-outline-dark">XL</div>
                                        <div class="btn-size btn btn-outline-dark">XXL</div>
                                    </div>
                                </div> -->
                                <div class="product-decrip">
                                    <p>Bộ Sơ Mi Cộc Form Rộng Dáng Màu Xám.</p>
                                    <div data-price="250000"></div>
                                </div>
                            </div>


                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/2.webp" alt="">
                                <div class="product-decrip">
                                    <p>Bộ Sơ Mi Cộc Form Rộng Dáng Màu Xanh.</p>
                                    <div data-price="250000"></div>
                                </div>
                            </div>

                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/3.webp" alt="">
                                <div class="product-decrip">
                                    <p>Áo Thun Ngắn Tay Nam Màu Đen.</p>
                                    <div data-price="200000"></div>
                                </div>
                            </div>

                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/4.webp" alt="">
                                <div class="product-decrip">
                                    <p>Áo Thun Ngắn Tay Nam Màu Trắng.</p>
                                    <div data-price="200000"></div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/5.webp" alt="">
                                <div class="product-decrip">
                                    <p>Áo thể thao Nam Đen.</p>
                                    <div data-price="200000"> </div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/6.webp" alt="">

                                <div class="product-decrip">
                                    <p>Áo thể thao Nam Trắng.</p>
                                    <div data-price="200000"> </div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/7.webp" alt="">

                                <div class="product-decrip">
                                    <p>Bộ Đồ Cổ Tròn Tay Cộc.</p>
                                    <div data-price="150000"></div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-summer/img8.webp" alt="">

                                <div class="product-decrip">
                                    <p>Bộ Đồ Cổ Tròn Tay Cộc.</p>
                                    <div data-price="150000"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End collect winter clothers -->


                </div>
        </section>
        <!-- End collect running policy -->


    </div>



    <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>

    <%@ include file="assets/includes/foot.jsp"%>
</body>

</html>