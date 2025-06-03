<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Giới Thiệu</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/about.css">
</head>

<body>
    <div>
        <header id="header"><%@ include file="assets/includes/header.jsp" %></header>
    </div>

    <!-- Phần Hero -->
    <section class="hero">
        <div class="hero-container">
            <div class="hero-main">
                <div class="hero-left">
                    <!-- Phần trên -->
                    <div class="hero-top">
                        <div class="hero-intro">
                            <p>Chào mừng đến với tủ đồ của Lasmanate</p>
                        </div>
                    </div>

                    <h1 class="hero-title">LASMANATE</h1>
                    <p>Thời trang tinh tế, đẳng cấp, và luôn theo xu hướng</p>
                    <button class="hero-button" onclick="scrollToContent()">Khám phá ngay</button>

                    <!-- Phần thông tin bên dưới -->
                    <div class="hero-info">
                        <div class="hero-year">Est. 2024</div>
                        <div class="hero-slogan">Thời trang đẳng cấp cho mọi phong cách</div>
                    </div>
                </div>
                <div class="hero-right">
                    <img src="assets/imgs/About/hero.jpg" alt="Fashion Collection" class="hero-image">
                </div>
            </div>
        </div>
    </section>

    <!-- Phần Giới Thiệu 1 -->
    <section class="intro" id="intro1">
        <div class="intro-content">
            <div class="intro-text">
                <span>Est. 2024 | Thành Phố Hồ Chí Minh, Việt Nam</span>
                <h2>Về Chúng Tôi</h2>
                <p> Được thành lập với mục tiêu mang đến cho mọi khách hàng
                    những sản phẩm thời trang không chỉ đẹp mắt mà còn chất lượng vượt trội, Lasmanate không ngừng
                    phát triển và sáng tạo để đáp ứng nhu cầu ngày càng cao của thị trường.
                </p>
                <div class="images-left">
                    <img src="assets/imgs/About/about-4.webp" alt="Fashion Image 4" class="image-small">
                </div>
            </div>

            <div class="intro-images">
                <div class="images-right">
                    <img src="assets/imgs/About/about-2.webp" alt="Fashion Image 2" class="image-small">
                    <img src="assets/imgs/About/about-3.webp" alt="Fashion Image 3" class="image-small">
                </div>

            </div>
        </div>
    </section>



    <!-- Phần Giới Thiệu 2 -->
    <section class="intro" id="intro2">
        <!-- Phần Trên -->
        <div class="intro-top">
            <div class="intro-image-large">
                <img src="assets/imgs/About/5.png" alt="Fashion Image 5">
            </div>
            <div class="intro-text-2">
                <h2>Chất Lượng Sản Phẩm Phù Hợp Với Người Việt Nam</h2>
                <p>Mỗi sản phẩm đều được chăm chút tỉ mỉ, từ việc lựa chọn chất liệu vải nhẹ nhàng, thoáng mát phù hợp
                    với khí hậu nhiệt đới của Việt Nam, đến từng đường may tinh tế mang lại sự thoải mái trong từng bước
                    di chuyển.</p>
            </div>
        </div>

        <!-- Phần Dưới -->
        <div class="intro-bottom">
            <img src="assets/imgs/About/7.jpg" alt="Fashion Image 7" class="image-small">
            <img src="assets/imgs/About/8.jpg" alt="Fashion Image 8" class="image-small">
            <img src="assets/imgs/About/6.jpg" alt="Fashion Image 6" class="image-small">
        </div>
    </section>

    <!-- Phần Giới Thiệu 3 - Thành Viên Sáng Lập -->
    <section class="intro" id="intro3">
        <h2 class="intro-heading">Thành Viên Sáng Lập <br> Lasmanate</h2>
        <div class="founder-cards">
            <!-- Thành viên 1 -->
            <div class="founder-card">
                <div class="avatar">
                    <img src="assets/imgs/About/M.jpg">
                </div>
                <h3>Hoàng Lê Nguyên Mạnh</h3>
                <p>22130163</p>
            </div>
            <!-- Thành viên 2 -->
            <div class="founder-card">
                <div class="avatar">
                    <img src="assets/imgs/About/L.jpg">
                </div>
                <h3>Hoàng Quốc Luật</h3>
                <p>22130154</p>
            </div>
            <!-- Thành viên 3 -->
            <div class="founder-card">
                <div class="avatar">
                    <img src="assets/imgs/About/N.jpg">
                </div>
                <h3>Phạm Quốc Phương Nam</h3>
                <p>22130175</p>
            </div>
        </div>
    </section>

    <!-- Phần Chốt Lại -->
    <section class="intro" id="closing">
        <div class="closing-content">
            <h2>Khám Phá Cùng Lasmanate</h2>
            <p>Với Lasmanate, chúng tôi không chỉ tạo ra thời trang mà còn mang đến phong cách và câu chuyện riêng cho
                bạn.<br>
                Hãy đồng hành cùng chúng tôi và tỏa sáng theo cách của bạn.</p>
            <a href="index.jsp"><button class="btn-discover">Khám Phá Ngay</button></a>

            <img src="assets/imgs/About/sign.png" alt="">
        </div>
    </section>

    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp" %></footer>
    </div>

    <script>
        function scrollToContent() {
            window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });
        }
    </script>
    <%@ include file="assets/includes/foot.jsp"%>
</body>

</html>