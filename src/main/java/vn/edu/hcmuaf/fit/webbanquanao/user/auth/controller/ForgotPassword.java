package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.ResetService;

import java.io.IOException;
import java.net.URLEncoder;


@WebServlet(name = "forgotPassword", value = "/forgotPassword")
public class ForgotPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();

        String email = request.getParameter("email");
        User user = userDao.getUserByEmail(email);


        if (user == null || user.getId() == 0) {
            request.setAttribute("message", "⚠️ Email không tồn tại hoặc người dùng không hợp lệ!");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        ResetService resetService = new ResetService();
        String token = resetService.genarateToken();
        String linkReset = "http://localhost:8080/WebBanQuanAo/ResetPassword?email=" + URLEncoder.encode(email, "UTF-8") + "&token=" + token;

        TokenForgotPassword newTokenForgot = new TokenForgotPassword(user.getId(), token, resetService.expiresAt());

        // Tiến hành chèn vào cơ sở dữ liệu
        TokenForgotDao tokenForgotDao = new TokenForgotDao();
        boolean isInsert = tokenForgotDao.insertTokenForgot(newTokenForgot);
        if (!isInsert) {
            request.setAttribute("message", "Đã có lỗi xảy ra trên máy chủ. Vui lòng thử lại sau!");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        boolean isSend = resetService.sendEmail(email, linkReset, user.getUserName());
        if (!isSend) {
            request.setAttribute("message", " Không thể gửi email yêu cầu. Vui lòng kiểm tra lại!");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        request.setAttribute("message", "Yêu cầu đặt lại mật khẩu đã được gửi thành công. Vui lòng kiểm tra email!");
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }
}
