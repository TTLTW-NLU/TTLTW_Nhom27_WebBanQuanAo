const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');
const popup = document.getElementById('popup');


registerBtn.addEventListener('click' , () => {
    container.classList.add('active');
});

loginBtn.addEventListener('click' , () => {
    container.classList.remove('active');
});

// Không cho copy mật khẩu
const passwordInput = document.getElementById("password");
passwordInput.addEventListener("copy", function (e) {
    e.preventDefault();
});

// Không cho paste/copy/dán vào ô xác nhận mật khẩu
const confirmPasswordInput = document.getElementById("confirmPassword");
["paste", "copy", "cut", "dragover", "drop"].forEach(evt => {
    confirmPasswordInput.addEventListener(evt, function (e) {
        e.preventDefault();
    });
});

// Kiểm tra mật khẩu xác nhận khớp
function validateConfirmPassword() {
    const password = document.getElementById("password").value;
    const confirmPassword = confirmPasswordInput.value;
    const msg = document.getElementById("confirm-password-text");

    if (confirmPassword !== password) {
        msg.textContent = "Mật khẩu xác nhận không khớp";
    } else {
        msg.textContent = "";
    }
}


function togglePassword(icon) {
    const input = icon.previousElementSibling;
    if (input.type === "password") {
        input.type = "text";
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        input.type = "password";
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
}



