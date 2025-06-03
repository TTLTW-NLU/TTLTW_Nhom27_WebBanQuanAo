<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="assets/includes/head.jsp"%>
    <title>Quên Mật Khẩu</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/main.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/forgot-password.css">
</head>

<body>
    <div>
        <header id="header"><%@ include file="assets/includes/header.jsp"%></header>
    </div>
    <div class="container active no-padding mx-auto">
        <div class="form-box">
            <form action="forgotPassword" method="POST">
                <h1>Bạn Quên Mật Khẩu?</h1>
                <p>Vui lòng nhập email hoặc số điện thoại đã đăng ký của bạn để khôi phục mật khẩu</p>

                <div class="input-box">
                    <input type="email" name="email" placeholder="Email" required>
                    <i class="fa-solid fa-envelope"></i>
                </div>

                <%-- Hiển thị thông báo nếu có --%>
                <% String message = (String) request.getAttribute("message"); %>
                <% if (message != null) { %>
                <div id="alertMessage"
                     class="alert <%= message.toLowerCase().contains("thành công") ? "alert-success" : "alert-danger" %> mt-3">
                    <i class="fa-solid <%= message.toLowerCase().contains("thành công") ? "fa-check-circle" : "fa-exclamation-circle" %>"></i>
                    <%= message %>
                </div>
                <% } %>

                <button type="submit" class="btn btn-primary btn-lg">Xác nhận</button>
                <p>Quay lại trang <a href="login.jsp" style="color: #335d4a !important;">Đăng Nhập</a></p>
                <p>Hoặc đăng nhập bằng các tài khoản mạng xã hội</p>
                <div class="social-icons">
                    <a href="#"><i class="fa-brands fa-facebook"></i></a>
                    <a href="#"><i class="fa-brands fa-google"></i></a>
                    <a href="#"><i class="fa-brands fa-x-twitter"></i></a>
                    <a href="#"><i class="fa-brands fa-instagram"></i></a>
                </div>
            </form>
        </div>
    </div>

    <script>
        $(document).ready(function () {
            setTimeout(function () {
                $("#alertMessage").fadeOut("slow");
            }, 5000);
        });
    </script>

    <div>
        <footer id="footer"><%@ include file="assets/includes/footer.jsp"%></footer>
    </div>

    <script src="./assets/js/forgot-password.js"></script>

    <!-- base js -->
    <script src="./assets/js/base.js"></script>
</body>

</html>