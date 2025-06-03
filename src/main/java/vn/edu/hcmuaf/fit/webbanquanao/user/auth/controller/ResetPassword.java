package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.ResetService;

import java.io.IOException;

@WebServlet(name = "ResetPassword", value = "/ResetPassword")
public class ResetPassword extends HttpServlet {
    UserDao userDao = new UserDao();
    TokenForgotDao tokenForgotDao = new TokenForgotDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        ResetService resetService = new ResetService();
        HttpSession session = request.getSession();
        if (token != null) {
            TokenForgotPassword tokenForgotPassword = tokenForgotDao.getTokenForgot(token);
            if (tokenForgotPassword == null) {
                request.setAttribute("message", "token invalid");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }
            if (resetService.isExpired(tokenForgotPassword.getExpiresAt())) {
                request.setAttribute("message", "token is expiry time");
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
            }
            User user = userDao.getUserById(tokenForgotPassword.getUserId());
            session.setAttribute("email", user.getEmail()); // Lưu email vào session
            session.setAttribute("token", tokenForgotPassword.getToken());
            request.getRequestDispatcher("reset-password.jsp").forward(request, response);
            return;
        } else {
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email"); // Lấy email từ session
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (email == null) {
            request.setAttribute("message", "⚠️ Không xác định được email!");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "⚠️ Mật khẩu không khớp!");
            request.getRequestDispatcher("reset-password.jsp").forward(request, response);
            return;
        }

        // Mã hóa mật khẩu trước khi cập nhật
        String hashedPassword = userDao.hashPassword(newPassword);

        // Cập nhật mật khẩu mới vào database
        TokenForgotPassword tokenForgotPassword = new TokenForgotPassword();
        tokenForgotPassword.setToken((String) session.getAttribute("token"));
        tokenForgotPassword.setIsUser(true);
        userDao.updatePassword(email, hashedPassword);
        userDao.updateStatus(tokenForgotPassword);

        System.out.println("✅ Mật khẩu mới đã được cập nhật cho: " + email);

        response.sendRedirect("homePage");
    }

}