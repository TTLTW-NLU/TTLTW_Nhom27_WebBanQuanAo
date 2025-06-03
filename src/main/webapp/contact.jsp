<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Liên Hệ</title>
    <link rel="stylesheet" href="./assets/css/main.css">
    <link rel="stylesheet" href="./assets/css/contact.css">
</head>

<body>
    <div>
        <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    </div>


    <div class="container no-padding">
        <div class="form-box">
            <form>
                <h2>Liên hệ với chúng tôi</h2>
                <p>Vui lòng điền thông tin để chúng tôi hỗ trợ bạn nhanh chóng nhất!</p>

                <label for="name">Họ và tên</label>
                <input type="text" id="name" name="name" placeholder="Nhập họ và tên" required>

                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="Nhập email của bạn" required>

                <label for="phone">Số điện thoại</label>
                <input type="text" id="phone" name="phone" placeholder="Nhập số điện thoại" required>

                <label for="message">Nội dung</label>
                <textarea id="message" name="message" placeholder="Nhập nội dung..." required></textarea>

                <button type="submit" class="btn btn-primary btn-lg">Gửi</button>
            </form>
        </div>

        <div class="contact-info">
            <h3>Thông tin liên hệ của Lasmanate</h3>
            <p><strong>Địa chỉ:</strong> 123 Đường ABC, Thành phố XYZ</p>
            <p><strong>Email:</strong> lasmanate@gmail.com</p>
            <p><strong>Số điện thoại:</strong> 1900 1234</p>
            <p><strong>Giờ làm việc:</strong> Từ 8:00 AM đến 10:00 PM (Thứ Hai - Chủ Nhật)</p>
        </div>
    </div>




    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>
    </div>

    <!-- base js -->
    <script src="./assets/js/base.js"></script>
</body>

</html>