const form = document.querySelector('form');
const password = document.querySelector('input[name="new-password"]');
const confirmPassword = document.querySelector('input[name="confirm-password"]');

form.addEventListener('submit', (e) => {
    if (password.value !== confirmPassword.value) {
        e.preventDefault(); // Ngăn chặn gửi form
        alert('Mật khẩu và xác nhận mật khẩu không khớp. Vui lòng thử lại.');
    }
});

// Mở popup
function openOtpPopup() {
    document.getElementById("otpPopup").style.display = "flex";
}

// Đóng popup
function closeOtpPopup() {
    document.getElementById("otpPopup").style.display = "none";
}

// Xác minh mã OTP và chuyển hướng nếu hợp lệ
function validateOtp() {
    const otpInputs = document.querySelectorAll('.otp-input');
    const otp = Array.from(otpInputs).map(input => input.value).join('');

    if (otp.length === otpInputs.length && /^\d{6}$/.test(otp)) {
        // Chuyển hướng đến trang reset-password.jsp nếu mã OTP hợp lệ
        window.location.href = 'reset-password.jsp';
    } else {
        alert("Vui lòng nhập đúng mã OTP gồm 6 chữ số!");
    }
}


