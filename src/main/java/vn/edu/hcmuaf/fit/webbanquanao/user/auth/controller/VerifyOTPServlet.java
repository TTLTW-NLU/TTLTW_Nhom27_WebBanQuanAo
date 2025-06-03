package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.OTPStorage;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;

@WebServlet("/verifyOTP")
public class VerifyOTPServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otpInput = request.getParameter("otp");
        HttpSession session = request.getSession();
        User tempUser = (User) session.getAttribute("tempUser");

        if (tempUser == null) {
            session.setAttribute("otpError", "Phiên đã hết hạn. Vui lòng đăng ký lại.");
            response.sendRedirect("register.jsp");
            return;
        }

        String storedOTP = OTPStorage.getOTP(tempUser.getEmail());

        System.out.println("Stored OTP: " + storedOTP);
        System.out.println("User input OTP: " + otpInput);

        if (storedOTP == null || !storedOTP.equals(otpInput)) {
            session.setAttribute("otpError", "Mã OTP không chính xác!");
            response.sendRedirect("verify.jsp");
            return;
        }

        // OTP đúng → tiến hành đăng ký
        boolean isSuccess = userDao.registerUser(tempUser);
        if (isSuccess) {
            // Cập nhật trạng thái kích hoạt
            userDao.updateUserStatus(tempUser.getEmail(), 1);

            // Xóa OTP
            OTPStorage.removeOTP(tempUser.getEmail());

            // Load lại user đã cập nhật từ DB để có status = 1
            User updatedUser = userDao.findByEmail(tempUser.getEmail());
            System.out.println("User status after update: " + updatedUser.getStatus());

            // Lưu vào session
            session.setAttribute("auth", updatedUser);

            // Chuyển hướng đến trang chủ
            response.sendRedirect("homePage");
        } else {
            session.setAttribute("otpError", "Lỗi đăng ký tài khoản, vui lòng thử lại.");
            response.sendRedirect("login.jsp");
        }
    }
}
