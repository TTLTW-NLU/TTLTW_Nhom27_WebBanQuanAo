package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/change-password")
public class ConfirmChangePasswordServlet extends HttpServlet {
    private final TokenForgotDao tokenDao = new TokenForgotDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        TokenForgotPassword tokenData = tokenDao.getTokenForgot(token);

        if (tokenData != null && tokenData.getExpiresAt().isAfter(LocalDateTime.now())) {
            // Token hợp lệ và chưa hết hạn
            req.setAttribute("userId", tokenData.getUserId());
            req.getRequestDispatcher("/change-password.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Link xác nhận không hợp lệ hoặc đã hết hạn.");
            req.getRequestDispatcher("/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("userId");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (userIdParam == null || newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu không hợp lệ hoặc không khớp.");
            req.getRequestDispatcher("/change-password.jsp").forward(req, resp);
            return;
        }

        int userId = Integer.parseInt(userIdParam);
        UserDao userDao = new UserDao();

        String hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(newPassword, org.mindrot.jbcrypt.BCrypt.gensalt(12));
        boolean updated = userDao.changePasswordByUserId(userId, hashedPassword);

        if (updated) {
            // Xóa token sau khi dùng xong
            tokenDao.deleteTokenByUserId(userId);

            req.setAttribute("message", "Thay đổi mật khẩu thành công!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Đổi mật khẩu thất bại, vui lòng thử lại.");
            req.getRequestDispatcher("/change-password.jsp").forward(req, resp);
        }
    }
}
