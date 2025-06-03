<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Đổi Mật Khẩu</title>
  <link rel="shortcut icon" href="assets/imgs/Favicon/favicon-32x32.png" type="image/png" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css"
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
        crossorigin="anonymous" referrerpolicy="no-referrer" />
  <link rel="stylesheet" href="./assets/css/base.css" />
  <link rel="stylesheet" href="./assets/css/main.css" />
  <link rel="stylesheet" href="./assets/css/header-footer.css" />
  <link rel="stylesheet" href="./assets/css/reset-password.css" />
  <link rel="stylesheet" href="./assets/bootstrap-4.6.2/css/bootstrap.min.css" />
</head>

<body>
<div>
  <header id="header"><%@ include file="assets/includes/header.jsp" %></header>
</div>

<div class="container active no-padding">
  <div class="form-box">
    <form action="${pageContext.request.contextPath}/change-password" method="POST" id="updatePasswordForm" onsubmit="return validateForm();">
      <h1>Thay Đổi Mật Khẩu</h1>
      <div class="form-group">
        <label for="currentPassword"></label>
        <div class="input-box">
          <input type="password" class="form-control" id="currentPassword" name="currentPassword"
                 placeholder="Nhập mật khẩu cũ." required>
          <button type="button" class="toggle-password" onclick="togglePassword(this, 'currentPassword')">
            <i class="fa-solid fa-eye"></i>
          </button>
        </div>
      </div>

      <div class="form-group">
        <label for="newPassword"></label>
        <div class="input-box">
          <input type="password" class="form-control" id="newPassword" name="newPassword"
                 placeholder="Nhập mật khẩu mới." required>
          <button type="button" class="toggle-password" onclick="togglePassword(this, 'newPassword')">
            <i class="fa-solid fa-eye"></i>
          </button>
        </div>
        <div id="passwordRequirements" style="margin-top: 8px; display: none;">
          <small id="length" class="invalid">Ít nhất 8 ký tự</small><br />
          <small id="uppercase" class="invalid">Chứa chữ in hoa</small><br />
          <small id="lowercase" class="invalid">Chứa chữ thường</small><br />
          <small id="number" class="invalid">Chứa số</small><br />
          <small id="special" class="invalid">Ký tự đặc biệt (!@#$...)</small>
        </div>
        <small id="passwordStrength" style="font-weight: 600; display: block; margin-top: 8px;"></small>
      </div>

      <div class="form-group">
        <label for="confirmPassword"></label>
        <div class="input-box">
          <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                 placeholder="Xác nhận lại mật khẩu." required>
          <button type="button" class="toggle-password" onclick="togglePassword(this, 'confirmPassword')">
            <i class="fa-solid fa-eye"></i>
          </button>
        </div>
        <small id="confirmPasswordMsg" style="font-weight: 600; display: block; margin-top: 8px;"></small>
      </div>

      <!-- Thêm input hidden chứa userId nếu bạn đã set userId vào req attribute khi forward -->
      <input type="hidden" name="userId" value="${userId}">

      <button type="submit" class="btn btn-primary btn-lg">Cập Nhật Mật Khẩu</button>
    </form>
  </div>
</div>


<div>
  <footer id="footer"><%@ include file="assets/includes/footer.jsp" %></footer>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
  function togglePassword(button, inputId) {
    const input = document.getElementById(inputId);
    if (input.type === "password") {
      input.type = "text";
      button.querySelector("i").classList.remove("fa-eye");
      button.querySelector("i").classList.add("fa-eye-slash");
    } else {
      input.type = "password";
      button.querySelector("i").classList.remove("fa-eye-slash");
      button.querySelector("i").classList.add("fa-eye");
    }
  }

  $(document).ready(function () {
    const newPasswordInput = $("#newPassword");
    const confirmPasswordInput = $("#confirmPassword");

    function checkPasswordStrength() {
      const password = newPasswordInput.val();
      const lengthValid = password.length >= 8;
      const uppercaseValid = /[A-Z]/.test(password);
      const lowercaseValid = /[a-z]/.test(password);
      const numberValid = /[0-9]/.test(password);
      const specialValid = /[^A-Za-z0-9]/.test(password);

      $("#length").toggleClass("valid", lengthValid).toggleClass("invalid", !lengthValid);
      $("#uppercase").toggleClass("valid", uppercaseValid).toggleClass("invalid", !uppercaseValid);
      $("#lowercase").toggleClass("valid", lowercaseValid).toggleClass("invalid", !lowercaseValid);
      $("#number").toggleClass("valid", numberValid).toggleClass("invalid", !numberValid);
      $("#special").toggleClass("valid", specialValid).toggleClass("invalid", !specialValid);

      const passed = [lengthValid, uppercaseValid, lowercaseValid, numberValid, specialValid].filter(Boolean).length;
      let strengthText = "";
      let color = "red";
      if (passed === 5) {
        strengthText = "Mật khẩu mạnh!";
        color = "green";
      } else if (passed >= 3) {
        strengthText = "Mật khẩu trung bình!";
        color = "orange";
      } else {
        strengthText = "Mật khẩu yếu!";
      }

      $("#passwordStrength").text(strengthText).css("color", color);
    }

    newPasswordInput.on("input", function () {
      const value = $(this).val();
      if (value.length === 0) {
        $("#passwordRequirements").hide();
        $("#passwordStrength").hide();
      } else {
        $("#passwordRequirements").show();
        $("#passwordStrength").show();
        checkPasswordStrength();
      }
    });

    confirmPasswordInput.on("input", function () {
      const newPassword = newPasswordInput.val();
      const confirmPassword = $(this).val();
      if (confirmPassword.length === 0) {
        $("#confirmPasswordMsg").text("");
      } else if (newPassword !== confirmPassword) {
        $("#confirmPasswordMsg").text("Mật khẩu xác nhận không khớp!").css("color", "red");
      } else {
        $("#confirmPasswordMsg").text("Mật khẩu khớp!").css("color", "green");
      }
    });
  });

  function validateForm() {
    const newPassword = $("#newPassword").val();
    const confirmPassword = $("#confirmPassword").val();
    return newPassword === confirmPassword;
  }

  // =============== Xử lý sự kiện đổi mật khẩu cho user ============//
  $(document).ready(function () {
    $(".update-password").submit(function (event) {
      event.preventDefault(); // Ngăn chặn hành vi submit mặc định

      // Lấy giá trị từ các input field
      let userName = $("#userName").val();
      let currentPassword = $("#currentPassword").val();
      let newPassword = $("#newPassword").val();
      let confirmPassword = $("#confirmPassword").val();

      // Kiểm tra mật khẩu nhập lại
      if (newPassword !== confirmPassword) {
        alert("Mật khẩu mới không khớp, vui lòng nhập lại!");
        return;
      }

      // Gửi AJAX request đến servlet
      $.ajax({
        url: "/WebBanQuanAo/updateProfileServlet",
        type: "PUT",
        contentType: "application/json",
        cache: false,
        data: JSON.stringify({
          userName: userName,
          currentPassword: currentPassword,
          newPassword: newPassword
        }),
        success: function (response) {
          alert(response.message); // Hiển thị thông báo từ server
          if (response.message === "Đổi mật khẩu thành công") {
            $("#popup").hide(); // Đóng popup nếu thành công
          }
        },
        error: function (xhr) {
          alert(xhr.responseJSON ? xhr.responseJSON.message : "Có lỗi xảy ra, vui lòng thử lại!");
        }
      });
    });
  });
</script>

<script src="./assets/js/base.js"></script>
<script src="./assets/js/reset-password.js"></script>
</body>

</html>
