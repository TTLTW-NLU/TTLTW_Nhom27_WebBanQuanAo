<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Trang chủ</title>
    <link rel="stylesheet" href="./assets/css/term.css">
    <link rel="stylesheet" href="./assets/css/responsive_luat.css">
</head>

<body>
    <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    <!-- Make a space to split the other out of header -->
    <div style="height: 12rem;"></div>

    <!-- Banner -->
    <section id="section_banner" class="container-fluid p-0">
        <img src="assets/imgs/term/banner/1.jpg" alt="" class="w-100">
    </section>

    <!-- Commitment -->
    <section id="section_commitment">
        <div class="container mx-auto">
            <h2 class="display-4 w-100 text-center">Cam kết của chúng tôi</h2>
            <div class="card__container">
                <article class="card__article">
                    <img src="assets/imgs/term/commitment/1.jpg" alt="image" class="card__img">
    
                    <div class="card__data">
                        <h5 class="text-center">Phong cách bạn chọn - chất lượng chúng tôi cam kết!</h5>
                    </div>
                </article>
    
                <article class="card__article">
                    <img src="assets/imgs/term/commitment/2.jpg" alt="image" class="card__img">
    
                    <div class="card__data">
                        <h5 class="text-center">Đồng hành cùng bạn - nâng tầm phong cách!</h5>
                    </div>
                </article>
    
                <article class="card__article">
                    <img src="assets/imgs/term/commitment/3.jpg" alt="image" class="card__img">
    
                    <div class="card__data">
                        <h5 class="text-center">Sang trọng từng đường may - tiết kiệm từng đồng chi!</h5>
                    </div>
                </article>
            </div>
        </div>
    </section>

    <!-- Location -->
    <section id="section_location" class="container mx-auto">
        <img src="assets/imgs/term/banner/2.jpg" alt="" class="w-100">
    </section>

    <!-- Quality -->
    <section id="section_quality" class="container-fluid p-0">
        <div class="line_top"></div>
        <div class="container_bottom d-flex w-100">
            <div class="line_left"></div>
            <div class="content">
                <h1>Lasmanate</h1>
                <p class="my-3">Chất lượng là cam kết hàng đầu của chúng tôi. Mỗi sản phẩm tại cửa hàng đều được lựa chọn kỹ lưỡng từ chất liệu đến từng đường may, đảm bảo sự bền bỉ và thoải mái cho người mặc. Chúng tôi tự hào mang đến các thiết kế không chỉ thời thượng mà còn phù hợp với nhiều phong cách và nhu cầu khác nhau. Từ vải cao cấp, thoáng mát đến màu sắc bền đẹp theo thời gian, mỗi chiếc áo, chiếc quần đều thể hiện sự tinh tế và chú trọng đến từng chi tiết. Hãy để chúng tôi đồng hành cùng bạn trong hành trình nâng tầm phong cách với chất lượng vượt mong đợi.</p>
                <a href="homePage" class="button">Khám phá ngay</a>
            </div>
            <div class="picture h-100">
                <img src="assets/imgs/term/quality/1.jpg" alt="">
            </div>
        </div>
    </section>

    <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>

    <!-- Javascript Native -->
    <script src="./assets/js/main.js"></script>
    <%@ include file="assets/includes/foot.jsp"%>
</body>

</html>