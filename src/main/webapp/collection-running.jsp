<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Bộ sưu tập chạy bộ</title>
    <link rel="stylesheet" href="./assets/css/collection-running.css">
</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>

    <div class="collect">

        <div class="collect_title-logo container-fluid section-banner">
            <img src="assets/imgs/Collection-running/item0.jpg" alt="">
        </div>
        <!-- Start Collect intro -->
        <div class="collect_intro grid">
            <div class="collect_intro-left">
                <div class="collect-title">LASMANATE <span> CHẠY BỘ</span></div>
                <div class="collect_title-small">Nguồn cảm hứng</div>
                <p class="collect-text">Thấu hiểu sâu sắc những vấn đề các runners gặp phải trên đường chạy,
                    <br> LASMANATE FOR RUNNING với những công nghệ mới nhất #ChafeFree <br>#QuickDry #Ultralight,
                    cùng sự kiểm nghiệm kỹ càng bởi các vận động viên chạy <br> Full Marathon chuyên nghiệp, sẽ mang
                    lại cho các
                    runners những trải nghiệm <br> chưa từng có trong mỗi sải chân.
                </p>
            </div>
            <div class="collect_intro-right">
                <img class="collect_intro-img" src="assets/imgs/Collection-running/Img_title.webp" alt="Erro">
            </div>
        </div>
        <!-- End collect intro -->


        <!-- Start collect marquee -->
        <div class="collect_marquee">
            <div class="collect_marquee-logo">LASMANATE</div>
            <div class="collect_marquee-text">
                <p>LASMANATE FOR RUNNING<span> MỘT NGÀY NĂNG ĐỘNG CÙNG LASMANATE </span> LASMANATE FOR RUNNING</p>
            </div>
        </div>
        <!-- End collect marquee -->

        <!-- All products -->
        <section id="section_all_products">
            <h2 class="running-clothes__title" style="margin-left: 3rem;">BỘ SƯU TẦM ĐỒ CHẠY BỘ</h2>
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
                                <img src="assets/imgs/Collection-running/do1.webp" alt="">
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
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="250000"></div>
                                </div>
                            </div>


                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do2.webp" alt="">
                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="250000"></div>
                                </div>
                            </div>

                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do3.webp" alt="">
                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="200000"></div>
                                </div>
                            </div>

                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do4.webp" alt="">
                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="200000"></div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do5.webp" alt="">
                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="200000"> </div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do6.webp" alt="">

                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="200000"> </div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do7.webp" alt="">

                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="150000"></div>
                                </div>
                            </div>
                            <div class="product-items">
                                <img src="assets/imgs/Collection-running/do8.webp" alt="">

                                <div class="product-decrip">
                                    <p>Bộ Quần Áo Thể Thao.</p>
                                    <div data-price="150000"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End collect winter clothers -->


                </div>
        </section>
        <!-- End collect running policy -->

        <!-- Start collect running policy -->
        <div class="collect_running-policy">
            <div class="container-fluid">
                <div class="collect_running-lists">
                    <div class="running_policy-items">
                        <div> <img src="assets/imgs/Collection-running/ultralight.png" alt=""> </div>
                        <span class="running_policy-orverlay">
                            <div class="top">
                                <h4>Ultralight</h4>
                                <p>Siêu nhệ chỉ 85gsm</p>
                            </div>
                            <div class="bottom">
                                <p>Sử dụng các chất liệu nhẹ, cùng công nghệ Không đường may Seamless mang đến một
                                    thiết
                                    kế tối giản để cực kỳ nhẹ. Để người mặc có cảm giác như bạn không mặc.</p>
                            </div>
                        </span>
                    </div>
                    <div class="running_policy-items">
                        <div> <img src="assets/imgs/Collection-running/chafefree.png" alt=""> </div>
                        <span class="running_policy-orverlay">
                            <div class="top">
                                <h4>Chafe-Free</h4>
                                <p>Giảm tối đa ma sát</p>
                            </div>
                            <div class="bottom">
                                <p>Để bạn tập trung và vận động thoải mái hơn nữa, công nghệ Không đường may Ultrasonic
                                    Bonded Seams sử dụng các tia laser để cắt vải và tối giản hoá các đường may. Từ đó
                                    hạn chế tối đa sự ma sát của các đường may vào cơ thể khi vận động.</p>
                            </div>
                        </span>
                    </div>
                    <div class="running_policy-items">
                        <div> <img src="assets/imgs/Collection-running/quickDry.png" alt=""></div>
                        <span class="running_policy-orverlay">
                            <div class="top">
                                <h4>Quick-Dry</h4>
                                <p>Nhanh khô</p>
                            </div>
                            <div class="bottom">
                                <p>Chất liệu vải được xử lí tính năng thấm hút và khô nhanh. Làm mát cơ thể bằng cách
                                    thấm hút mồ hôi trên bề mặt vải, cùng với khả năng khô nhanh giúp bay hơi nhanh hơn.
                                    Giúp cơ thể khô ráo, mang đến sự thoáng mát và tập trung trong khi vận động.</p>
                            </div>
                        </span>
                    </div>
                </div>
            </div>
        </div>



        <div class="collect_title-logo container-fluid section-banner">
            <img src="assets/imgs/Collection-running/img-big2.png" alt="">
        </div>

        <div class="endpage container-fluid">

            <div class="list-item">
                <div class="item">
                    <img src="assets/imgs/Collection-running/epitem1.webp" alt=""
                         data-name="Quần shorts nam chạy bộ CoolFast 3.5 inch" data-price="299.000đ">
                </div>
                <div class="item">
                    <img src="assets/imgs/Collection-running/epitem2.webp" alt=""
                         data-name="Áo thể thao nam CoolFit 2.0" data-price="199.000đ">
                </div>
                <div class="item">
                    <img src="assets/imgs/Collection-running/epitem3.webp" alt="" data-name="Áo thể thao nam SprintMax"
                         data-price="499.000đ">
                </div>
                <div class="item">
                    <img src="assets/imgs/Collection-running/epitem4.webp" alt=""
                         data-name="Bộ quần áo thể thao FlexFit" data-price="399.000đ">
                </div>
                <div class="item">
                    <img src="assets/imgs/Collection-running/epitem5.webp" alt="" data-name="Áo chạy bộ SwiftRun"
                         data-price="750.000đ">
                </div>
            </div>

        </div>

        <!-- Modal -->
        <div class="modal fade" id="productModal" tabindex="-1" aria-labelledby="productModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header border-0">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body d-flex align-items-center">
                        <!-- Left: Product Image -->
                        <div class="product-image me-4">
                            <img src="assets/imgs/Collection-running/epitem1.webp" alt="Product"
                                 class="img-fluid rounded">
                        </div>
                        <!-- Right: Product Info -->
                        <div class="product-info">
                            <h5>Quần shorts nam chạy bộ CoolFast 3.5 inch</h5>
                            <p class="text-muted">299.000đ</p>
                            <a src="" class="btn btn-primary">
                                Mua Ngay →
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <!-- End collect running policy -->


    </div>



    <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>


    <script>
        document.querySelectorAll('.endpage .item img').forEach((img) => {
            img.addEventListener('click', () => {
                const productName = img.getAttribute('data-name');
                const productPrice = img.getAttribute('data-price');

                document.querySelector('.product-image img').src = img.src;


                document.querySelector('.product-info h5').textContent = productName;
                document.querySelector('.product-info p').textContent = productPrice;


                const modal = new bootstrap.Modal(document.getElementById('productModal'));
                modal.show();
            });
        });
    </script>
    <%@ include file="assets/includes/foot.jsp"%>
</body>

</html>