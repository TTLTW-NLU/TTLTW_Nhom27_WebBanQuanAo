document.getElementById("openPopup").addEventListener("click", function () {
  document.getElementById("popup").style.display = "block";
});

document.getElementById("closePopup").addEventListener("click", function () {
  document.getElementById("popup").style.display = "none";
});

document.getElementById("overlay").addEventListener("click", function () {
  document.getElementById("popup").style.display = "none";
});

// =============== Xử lý sự kiện showMain cho menu============//
document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("profile-info").classList.add("block");
});
function showMain(event, mainId) {
  event.preventDefault(); 


  document.querySelectorAll(".sidebar ul li").forEach((link) => {
    link.classList.remove("active");
  });


  event.currentTarget.classList.add("active");


  document.querySelectorAll("main").forEach((main) => {
    main.classList.remove("block");
  });


  const targetMain = document.getElementById(mainId);
  if (targetMain) {
    targetMain.classList.add("block");
  }
}

// // =============== Xử lý sự kiện đổi mật khẩu cho user ============//
// $(document).ready(function () {
//   $(".update-password").submit(function (event) {
//     event.preventDefault(); // Ngăn chặn hành vi submit mặc định
//
//     // Lấy giá trị từ các input field
//     let userName = $("#userName").val();
//     let currentPassword = $("#currentPassword").val();
//     let newPassword = $("#newPassword").val();
//     let confirmPassword = $("#confirmPassword").val();
//
//     // Kiểm tra mật khẩu nhập lại
//     if (newPassword !== confirmPassword) {
//       alert("Mật khẩu mới không khớp, vui lòng nhập lại!");
//       return;
//     }
//
//     // Gửi AJAX request đến servlet
//     $.ajax({
//       url: "/WebBanQuanAo/updateProfileServlet",
//       type: "PUT",
//       contentType: "application/json",
//       cache: false,
//       data: JSON.stringify({
//         userName: userName,
//         currentPassword: currentPassword,
//         newPassword: newPassword
//       }),
//       success: function (response) {
//         alert(response.message); // Hiển thị thông báo từ server
//         if (response.message === "Đổi mật khẩu thành công") {
//           $("#popup").hide(); // Đóng popup nếu thành công
//         }
//       },
//       error: function (xhr) {
//         alert(xhr.responseJSON ? xhr.responseJSON.message : "Có lỗi xảy ra, vui lòng thử lại!");
//       }
//     });
//   });
//
//   // Xử lý mở/đóng popup
//   $("#openPopup").click(function () {
//     $("#popup").show();
//   });
//
//   $("#closePopup").click(function () {
//     $("#popup").hide();
//   });
// });

