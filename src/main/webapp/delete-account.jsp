<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 12/04/2025
  Time: 8:31 CH
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Yêu Cầu Xóa Dữ Liệu Người Dùng</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <!-- Favicon -->
  <link rel="shortcut icon" href="assets/imgs/Favicon/favicon-32x32.png" type="image/png">

  <!-- CSS Frameworks -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
        integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />

  <!-- Custom CSS -->
  <link rel="stylesheet" href="./assets/css/base.css">
  <link rel="stylesheet" href="./assets/css/main.css">
  <link rel="stylesheet" href="./assets/css/header-footer.css">
  <link rel="stylesheet" href="./assets/css/privacy-policy.css">
</head>
<body>

<!-- Header -->
<div>
  <header id="header"><%@ include file="assets/includes/header.jsp" %></header>
</div>

<!-- Banner -->
<section id="section_banner" class="container-fluid p-0">
  <img src="assets/imgs/Privacy%20Policy/1.png" alt="Banner" class="w-100">
</section>

<!-- Content -->
<section class="policy-section">
  <div class="grid">
    <h2 class="text-danger mb-4">Yêu Cầu Xóa Dữ Liệu Người Dùng</h2>
    <p>Nếu bạn đã đăng nhập vào hệ thống của chúng tôi thông qua Facebook và muốn xóa tài khoản cùng toàn bộ dữ liệu cá nhân của mình, vui lòng liên hệ theo địa chỉ email dưới đây:</p>

    <p><strong>Email hỗ trợ:</strong> <a href="mailto:support@yourwebsite.com">support@yourwebsite.com</a></p>

    <p>Chúng tôi sẽ xử lý yêu cầu của bạn trong vòng <strong>7 ngày làm việc</strong> kể từ khi nhận được.</p>

    <p>Xin cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!</p>
  </div>

  <div class="policy-button">
    <a href="contact.jsp"><button class="btn btn-outline-primary">Liên Hệ Chúng Tôi</button></a>
  </div>
</section>

<!-- Footer -->
<div>
  <footer id="footer"><%@ include file="assets/includes/footer.jsp" %></footer>
</div>

<!-- Scripts -->
<script src="./assets/js/base.js"></script>
</body>
</html>
