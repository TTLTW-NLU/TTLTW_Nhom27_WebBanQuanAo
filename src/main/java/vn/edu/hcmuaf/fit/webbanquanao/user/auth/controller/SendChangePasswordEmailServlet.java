package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.dao.TokenForgotDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.util.EmailUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/sendChangePasswordEmail")
public class SendChangePasswordEmailServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private TokenForgotDao tokenDao = new TokenForgotDao();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getReader().lines().collect(Collectors.joining());
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();
        String userName = jsonObj.get("userName").getAsString();

        User user = userDao.getUserByUserName(userName);
        JsonObject result = new JsonObject();

        if (user != null && user.getEmail() != null) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiresAt = LocalDateTime.now().plusHours(1); // token hết hạn sau 1 giờ

            // Lưu token và thời gian hết hạn vào DB
            TokenForgotPassword tokenData = new TokenForgotPassword(user.getId(), token, expiresAt);
            tokenDao.insertTokenForgot(tokenData);

            String link = "http://localhost:8080/WebBanQuanAo/change-password?token=" + token;

            String subject = "Xác nhận đổi mật khẩu";
            String content = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9; text-align: center;\">"
                    + "<p style=\"font-size: 16px; color: #555;\">Bạn đã yêu cầu thay đổi mật khẩu. Nhấn vào nút bên dưới để tiếp tục:</p>"
                    + "<div style=\"margin: 20px 0;\">"
                    + "<a href=\"" + link + "\" style=\"background-color: #335d4a; color: white; text-decoration: none; padding: 12px 24px; border-radius: 5px; font-size: 16px; display: inline-block;\">Đổi mật khẩu</a>"
                    + "</div>"
                    + "<p style=\"font-size: 14px; color: #777;\">Nếu bạn không yêu cầu hành động này, vui lòng bỏ qua email này.</p>"
                    + "<hr style=\"border: none; border-top: 1px solid #ddd;\">"
                    + "<p style=\"font-size: 12px; color: #999;\">© 2025 LASMANATE. Mọi quyền được bảo lưu.</p>"
                    + "</div>";

            try {
                EmailUtil.sendEmail(user.getEmail(), subject, content);
                result.addProperty("success", true);
            } catch (Exception e) {
                result.addProperty("success", false);
            }
        } else {
            result.addProperty("success", false);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(result.toString());
    }
}
