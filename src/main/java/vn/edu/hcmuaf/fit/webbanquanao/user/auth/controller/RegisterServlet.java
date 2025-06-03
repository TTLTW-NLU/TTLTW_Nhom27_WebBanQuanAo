package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.EmailService;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.OTPStorage;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.user.util.CapchaUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession(true);

        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        if (!CapchaUtil.verifyRecaptcha(gRecaptchaResponse)) {
            session.setAttribute("error", "Xác minh CAPTCHA thất bại!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }

        // Validate dữ liệu nhập vào
        if (username == null || username.trim().isEmpty()) {
            session.setAttribute("error", "Tên tài khoản không hợp lệ!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            session.setAttribute("error", "Họ không được để trống!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            session.setAttribute("error", "Tên không được để trống!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }
        if (email == null || email.trim().isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            session.setAttribute("error", "Email không hợp lệ!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            session.setAttribute("error", "Mật khẩu không được để trống!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }

        // Kiểm tra trùng lặp username hoặc email
        if (userDao.isUsernameExists(username)) {
            session.setAttribute("error", "Tên tài khoản đã tồn tại!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }
        if (userDao.isEmailExists(email)) {
            session.setAttribute("error", "Email đã được sử dụng!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }

        // Lấy role name của roleId 2 (USER)
        String roleName = userDao.getRoleNameById(2);
        if (roleName == null) {
            session.setAttribute("error", "Không tìm thấy vai trò USER!");
            response.sendRedirect("login.jsp#signup-form");
            return;
        }

        // Mã hóa mật khẩu
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        // Tạo và lưu OTP
        String otp = generateOTP();
        OTPStorage.storeOTP(email, otp);
        EmailService.sendEmail(email, "🔒 Xác thực tài khoản - Mã OTP của bạn", otp);

        // Tạo user tạm thời
        User tempUser = new User(username, firstName, lastName, email, hashedPassword);
        ArrayList<String> roles = new ArrayList<>();
        roles.add(roleName);
        tempUser.setRoles(roles);

        // Lưu user tạm vào session
        session.setAttribute("emailVerify", email);
        session.setAttribute("tempUser", tempUser);

        // Chuyển hướng sang trang xác thực OTP
        String redirect = request.getParameter("redirect");
        if (redirect != null && !redirect.isEmpty()) {
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect(request.getContextPath() + "/verify.jsp");
        }
    }
    private String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
}
