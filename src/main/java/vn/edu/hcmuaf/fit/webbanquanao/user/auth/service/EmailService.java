package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {
    private static final String EMAIL_FROM = "nam784412@gmail.com";
    private static final String PASSWORD = "kxto gjht fjyd xumr";

    public static void sendEmail(String recipient, String subject, String otp) {
        if (recipient == null || recipient.isEmpty()) {
            System.out.println("Lỗi: Địa chỉ email trống!");
            return;
        }

        String body = "<div style=\"font-family: Arial, sans-serif; font-size: 16px; color: #333; max-width: 600px; margin: auto;\">" +
                "<h2 style=\"color: #0d6efd;\">Xác minh tài khoản</h2>" +
                "<p>Xin chào,</p>" +
                "<p>Cảm ơn bạn đã đăng ký. Đây là mã xác thực (OTP) của bạn:</p>" +
                "<div style=\"font-size: 24px; font-weight: bold; color: #dc3545; margin: 20px 0;\">" + otp + "</div>" +
                "<p>Mã có hiệu lực trong <strong>5 phút</strong>. Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email.</p>" +
                "<br><p>Trân trọng,<br>Đội ngũ hỗ trợ Web Bán Quần Áo</p>" +
                "</div>";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Email đã gửi thành công tới: " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Lỗi gửi email: " + e.getMessage());
        }
    }
}
