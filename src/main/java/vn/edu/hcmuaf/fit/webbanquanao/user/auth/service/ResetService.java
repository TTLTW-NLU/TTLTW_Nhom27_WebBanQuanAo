package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDateTime;
import java.util.Properties;
import java.util.UUID;

public class ResetService {
    private final int LIMIT_MINUS = 10;
    private final String from = "nam784412@gmail.com";
    private final String password = "eqyo xxaf omly mtog";
    public String genarateToken() {
        return UUID.randomUUID().toString();
    }
    public LocalDateTime expiresAt() {
        return LocalDateTime.now().plusMinutes(LIMIT_MINUS);
    }
    public boolean isExpired(LocalDateTime time) {
        return LocalDateTime.now().isAfter(time);
    }
    public boolean sendEmail(String to, String link, String name) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage message = new MimeMessage(session);
        try {
            message.addHeader("Content-Type", "text/html;charset=UTF-8");
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            message.setSubject("🔒 Đặt lại mật khẩu của bạn");

            String content = "<div style=\"font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 10px; background-color: #f9f9f9; text-align: center;\">"
                    + "<h1 style=\"color: #333;\">Xin chào, " + name + "</h1>"
                    + "<p style=\"font-size: 16px; color: #555;\">Bạn đã yêu cầu đặt lại mật khẩu. Nhấn vào nút bên dưới để tiếp tục:</p>"
                    + "<div style=\"margin: 20px 0;\">"
                    + "<a href=\"" + link + "\" style=\"background-color: #335d4a; color: white; text-decoration: none; padding: 12px 24px; border-radius: 5px; font-size: 16px; display: inline-block;\">Đặt lại mật khẩu</a>"
                    + "</div>"
                    + "<p style=\"font-size: 14px; color: #777;\">Nếu bạn không yêu cầu hành động này, vui lòng bỏ qua email này.</p>"
                    + "<hr style=\"border: none; border-top: 1px solid #ddd;\">"
                    + "<p style=\"font-size: 12px; color: #999;\">© 2025 LASMANATE. Mọi quyền được bảo lưu.</p>"
                    + "</div>";

            message.setContent(content, "text/html;charset=UTF-8");
            Transport.send(message);
            System.out.println("Gửi thành công");
            return true;
        } catch (Exception e) {
            System.out.println("Lỗi gửi email: " + e.getMessage());
            return false;
        }
    }
}
