<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Quên Mật Khẩu</title>
    <!-- Favicon -->
    <link rel="shortcut icon" href="assets/imgs/Favicon/favicon-32x32.png" type="image/png" />

    <!-- Reset CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
          integrity="sha512-NhSC1YmyruXifcj/KFRWoC561YpHpc5Jtzgvbuzx5VozKpWvQ+4nXhPdFgmx8xqexRcpAglTj9sIBWINXa8x5w=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer" />

    <link rel="stylesheet" href="./assets/css/base.css" />
    <link rel="stylesheet" href="./assets/css/main.css" />
    <link rel="stylesheet" href="./assets/css/header-footer.css" />
    <link rel="stylesheet" href="./assets/css/reset-password.css" />

    <!-- Bootstrap 4.6.2 CSS -->
    <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css" />
</head>

<body>
<div>
    <header id="header"><%@ include file="assets/includes/header.jsp" %></header>
</div>

<div class="container active no-padding">
    <div class="form-box">
        <form action="ResetPassword" method="POST" onsubmit="return validateForm();">
            <h1>Xác Nhận Lại Mật Khẩu</h1>

            <div class="input-box">
                <input type="email" name="email" value="${email}" readonly />
                <i class="fa-solid fa-envelope"></i>
            </div>

            <div class="input-box">
                <input type="password" name="newPassword" id="newPassword" placeholder="Mật Khẩu Mới" required />
                <button type="button" class="toggle-password" onclick="togglePassword(this)">
                    <i class="fa-solid fa-eye"></i>
                </button>
            </div>

            <small id="passwordStrength" style="font-weight: 600; display: block; margin-bottom: 8px;"></small>

            <div id="passwordRequirements" style="margin-bottom: 15px;">
                <small id="length" class="invalid">Ít nhất 8 ký tự</small><br />
                <small id="uppercase" class="invalid">Chứa chữ in hoa</small><br />
                <small id="lowercase" class="invalid">Chứa chữ thường</small><br />
                <small id="number" class="invalid">Chứa số</small><br />
                <small id="special" class="invalid">Ký tự đặc biệt (!@#$...)</small>
            </div>

            <div class="input-box">
                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Xác Nhận Lại Mật Khẩu" required />
                <button type="button" class="toggle-password" onclick="togglePassword(this)">
                    <i class="fa-solid fa-eye"></i>
                </button>
            </div>

            <small id="confirmPasswordMsg" style="font-weight: 600; display: block; margin-bottom: 15px;"></small>

            <button type="submit" class="btn btn-primary btn-lg">Xác Nhận</button>

            <div class="info-box" style="margin-top: 20px;">
                <p>Bạn có thể liên hệ với chúng tôi qua email để nhận sự hỗ trợ
                    <a href="mailto:lasmanate@gmail.com">lasmanate@gmail.com</a>
                </p>
            </div>
        </form>
    </div>
</div>

<div>
    <footer id="footer"><%@ include file="assets/includes/footer.jsp" %></footer>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script>
    $(document).ready(function () {
        $("#newPassword").on("input", function () {
            var password = $(this).val();

            if (password.length === 0) {
                // Nếu chưa nhập mật khẩu, ẩn đánh giá độ mạnh và yêu cầu mật khẩu
                $("#passwordStrength").text("").hide();
                $("#passwordRequirements").hide();
            } else {
                // Hiện các phần khi có dữ liệu
                $("#passwordStrength").show();
                $("#passwordRequirements").show();

                // Kiểm tra điều kiện mật khẩu
                var lengthValid = password.length >= 8;
                var uppercaseValid = /[A-Z]/.test(password);
                var lowercaseValid = /[a-z]/.test(password);
                var numberValid = /[0-9]/.test(password);
                var specialValid = /[^A-Za-z0-9]/.test(password);

                // Cập nhật màu cho từng điều kiện
                $("#length").toggleClass("valid", lengthValid).toggleClass("invalid", !lengthValid);
                $("#uppercase").toggleClass("valid", uppercaseValid).toggleClass("invalid", !uppercaseValid);
                $("#lowercase").toggleClass("valid", lowercaseValid).toggleClass("invalid", !lowercaseValid);
                $("#number").toggleClass("valid", numberValid).toggleClass("invalid", !numberValid);
                $("#special").toggleClass("valid", specialValid).toggleClass("invalid", !specialValid);

                // Đánh giá tổng thể độ mạnh mật khẩu
                var passedConditions = [lengthValid, uppercaseValid, lowercaseValid, numberValid, specialValid].filter(Boolean).length;

                var strengthMsg = "";
                var strengthColor = "red";

                if (passedConditions === 5) {
                    strengthMsg = "Mật khẩu mạnh!";
                    strengthColor = "green";
                } else if (passedConditions >= 3) {
                    strengthMsg = "Mật khẩu trung bình!";
                    strengthColor = "orange";
                } else {
                    strengthMsg = "Mật khẩu yếu!";
                    strengthColor = "red";
                }

                $("#passwordStrength").text(strengthMsg).css("color", strengthColor);
            }
        });

        // Ẩn ban đầu nếu chưa nhập mật khẩu
        if ($("#newPassword").val().length === 0) {
            $("#passwordStrength").hide();
            $("#passwordRequirements").hide();
        } else {
            $("#passwordStrength").show();
            $("#passwordRequirements").show();
        }

        $("#confirmPassword").on("input", function () {
            validateConfirmPassword();
        });
    });
    function validateConfirmPassword() {
        var newPassword = $("#newPassword").val();
        var confirmPassword = $("#confirmPassword").val();
        var msg = "";
        var color = "red";

        if (confirmPassword.length === 0) {
            // Nếu chưa nhập mật khẩu xác nhận thì ẩn thông báo
            msg = "";
        } else {
            if (newPassword !== confirmPassword) {
                msg = "Mật khẩu xác nhận không khớp!";
            } else {
                msg = "Mật khẩu khớp!";
                color = "green";
            }
        }

        $("#confirmPasswordMsg").text(msg).css("color", color);
    }

    // Ngăn copy/dán/kéo-thả vào ô xác nhận mật khẩu
    const confirmPasswordInput = document.getElementById("confirmPassword");

    confirmPasswordInput.addEventListener("paste", function (e) {
        e.preventDefault();
    });

    confirmPasswordInput.addEventListener("copy", function (e) {
        e.preventDefault();
    });

    confirmPasswordInput.addEventListener("cut", function (e) {
        e.preventDefault();
    });

    confirmPasswordInput.addEventListener("dragover", function (e) {
        e.preventDefault();
    });

    confirmPasswordInput.addEventListener("drop", function (e) {
        e.preventDefault();
    });
    const newPasswordInput = document.getElementById("newPassword");

    newPasswordInput.addEventListener("copy", function (e) {
        e.preventDefault();
    });
</script>

<!-- base js -->
<script src="./assets/js/base.js"></script>
<script src="./assets/js/reset-password.js"></script>
</body>

</html>
