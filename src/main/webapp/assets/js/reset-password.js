function togglePassword(button) {
    const input = button.previousElementSibling; // Lấy input trước nút
    const icon = button.querySelector('i');

    if (input.type === "password") {
        input.type = "text"; // Hiện mật khẩu
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = "password"; // Ẩn mật khẩu
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}

// Hiển thị popup thông báo
function showConfirmationPopup(event) {
    event.preventDefault();
    document.getElementById("confirmationPopup").style.display = "flex";
    return false; 
}
// Chuyển hướng về trang chủ
function redirectToHome() {
    window.location.href = "index.jsp";
}

// Kiểm tra mật khẩu mới và mật khẩu xác nhận
function checkPasswords(event) {
    event.preventDefault(); 

    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (newPassword !== confirmPassword) {
        alert("Mật khẩu mới và xác nhận mật khẩu không trùng khớp!");
        return false; 
    }

    showConfirmationPopup(event);
    return false;
}


