package vn.edu.hcmuaf.fit.webbanquanao.user.service;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.util.EmailUtil;

public class UserService {
    private final UserDao userDao = new UserDao();

    public boolean changePasswordUser(String userName, String currentPassWord, String newPassWord) {
        String storedPassword = userDao.getPassWordByUserName(userName);
        if (storedPassword == null) {
            System.out.println("User not found!");
            return false;
        }

        boolean isOldPasswordHashed = storedPassword.startsWith("$2a$") || storedPassword.startsWith("$2b$");

        if (isOldPasswordHashed) {
            if (!BCrypt.checkpw(currentPassWord, storedPassword)) {
                System.out.println("Incorrect current password!");
                return false;
            }
        } else {
            if (!storedPassword.equals(currentPassWord)) {
                System.out.println("Incorrect current password (not hashed)!");
                return false;
            }
            System.out.println("Updating old password to hashed version...");
            storedPassword = BCrypt.hashpw(currentPassWord, BCrypt.gensalt(12));
            userDao.changePassword(userName, storedPassword);
        }

        String hashedNewPassword = BCrypt.hashpw(newPassWord, BCrypt.gensalt(12));
        boolean changed = userDao.changePassword(userName, hashedNewPassword);

        if (changed) {
            // Lấy user theo email hoặc username
            User user = userDao.findByEmail(userDao.getEmailByUserName(userName));

            if (user != null && user.getEmail() != null) {
                String subject = "Xác nhận thay đổi mật khẩu";
                String content = "Bạn vừa thay đổi mật khẩu cho tài khoản của mình. Nếu không phải bạn, vui lòng liên hệ với bộ phận hỗ trợ.";

                try {
                    EmailUtil.sendEmail(user.getEmail(), subject, content);
                } catch (Exception e) {
                    System.err.println("Gửi email thất bại: " + e.getMessage());
                }
            }
        }

        return changed;
    }

}