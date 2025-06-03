package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.UserLogsDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.UserLogs;

import java.util.Collections;
import java.util.List;

public class UserLogsService {
    private static final Logger logger = LoggerFactory.getLogger(UserLogsService.class);
    private static final UserLogsService instance = new UserLogsService();

    private final UserLogsDao userLogsDao;

    private boolean enableDatabaseLogging = true;

    private UserLogsService() {
        this.userLogsDao = new UserLogsDao();
    }

    public static UserLogsService getInstance() {
        return instance;
    }

    /**
     * Ghi log action đồng thời ghi vào logger và database
     * @param level cấp độ log: DEBUG, INFO, WARN, ERROR, FATAL
     * @param username tên người thực hiện
     * @param roles danh sách roles
     * @param action chi tiết hành động
     * @param ipAddress IP của người dùng
     * @return true nếu ghi database thành công
     */
    public boolean logAction(String level,
                             String username,
                             List<String> roles,
                             String action,
                             String ipAddress) {
        if (!isValidLogLevel(level)) {
            logger.error("Cấp độ log không hợp lệ: {}", level);
            return false;
        }
        if (roles == null) {
            roles = Collections.emptyList();
        }
        // Log ra console/file
        switch (level) {
            case "DEBUG":
                logger.debug("User: {}, Roles: {}, Action: {}, IP: {}", username, roles, action, ipAddress);
                break;
            case "INFO":
                logger.info("User: {}, Roles: {}, Action: {}, IP: {}", username, roles, action, ipAddress);
                break;
            case "WARN":
                logger.warn("User: {}, Roles: {}, Action: {}, IP: {}", username, roles, action, ipAddress);
                break;
            case "ERROR":
            case "FATAL":
                logger.error("User: {}, Roles: {}, Action: {}, IP: {}", username, roles, action, ipAddress);
                break;
        }

        // Tạo object và lưu database
        UserLogs log = new UserLogs();
        log.setUsername(username);
        log.setLevel(level);
        log.setAction(action);
        log.setIpAddress(ipAddress);
        log.setRoles(roles);

        if (!enableDatabaseLogging) {
            return true;
        }
        return userLogsDao.logUserAction(log);
    }

    public void setEnableDatabaseLogging(boolean enable) {
        this.enableDatabaseLogging = enable;
    }

    private boolean isValidLogLevel(String level) {
        return List.of("DEBUG", "INFO", "WARN", "ERROR", "FATAL").contains(level);
    }

    // Các phương thức tiện ích khác chỉ gọi logAction mà không cần logger ở mức gọi ngoài

    public boolean logAnonymousAccessAttempt(String path, String ipAddress) {
        return logAction("WARN", "ANONYMOUS", Collections.emptyList(),
                "User ẩn danh cố truy cập đường dẫn: " + path, ipAddress);
    }

    public boolean logLoadedUserInfo(String username, List<String> roles, String ipAddress) {
        return logAction("INFO", username, roles,
                "Đã load thông tin user với roles: " + roles, ipAddress);
    }

    public boolean logBlockedLogin(String username, String ipAddress) {
        return logAction("WARN", username, Collections.emptyList(),
                "Chặn đăng nhập do tài khoản chưa kích hoạt hoặc thiếu xác thực", ipAddress);
    }

    public boolean logUnauthorizedAccess(String username, String path, String resource, Integer permission,
                                         String ipAddress, List<String> roles) {
        String msg = String.format("Không có quyền truy cập: đường dẫn=%s, resource=%s, currentRolesUser=%s", path, resource, permission);
        return logAction("WARN", username, roles, msg, ipAddress);
    }

    public boolean logAccessGranted(String username, String path, String resource, Integer permission,
                                    String ipAddress, List<String> roles) {
        String msg = String.format("Truy cập thành công: đường dẫn=%s, resource=%s, currentRolesUser=%s", path, resource, permission);
        return logAction("INFO", username, roles, msg, ipAddress);
    }

    public boolean logLoginSuccess(String username, String ipAddress, List<String> roles) {
        return logAction("INFO", username, roles, "Đăng nhập thành công", ipAddress);
    }

    public boolean logLogoutSuccess(String username, String ipAddress, List<String> roles) {
        return logAction("INFO", username, roles, "Đăng xuất thành công", ipAddress);
    }

    public boolean logLoginFailure(String username, String ipAddress) {
        return logAction("ERROR", username, Collections.emptyList(), "Đăng nhập thất bại", ipAddress);
    }

    public boolean logCreateEntity(String username, String entityName, String entityId,
                                   String ipAddress, List<String> roles) {
        String msg = String.format("Tạo mới %s thành công: %s", entityName, entityId);
        return logAction("INFO", username, roles, msg, ipAddress);
    }

    public boolean logUpdateEntity(String username, String entityName, String entityId,
                                   String ipAddress, List<String> roles) {
        String msg = String.format("Cập nhật thành công %s: Id = %s", entityName, entityId);
        return logAction("INFO", username, roles, msg, ipAddress);
    }

    public boolean logDeleteEntity(String username, String entityName, String entityId,
                                   String ipAddress, List<String> roles) {
        String msg = String.format("Xóa thành công %s: Id = %s", entityName, entityId);
        return logAction("WARN", username, roles, msg, ipAddress);
    }

    public boolean logAssignRoles(String username, String targetUser, List<String> newRoles,
                                  String ipAddress, List<String> roles) {
        String msg = String.format("Phân roles %s cho user: %s", newRoles, targetUser);
        return logAction("INFO", username, roles, msg, ipAddress);
    }

    public boolean logCustom(String username, String level, String action,
                             String ipAddress, List<String> roles) {
        return logAction(level, username, roles, action, ipAddress);
    }

    public boolean logAccessDenied(String username, String path, String resource,
                                   String ipAddress, List<String> roles) {
        String msg = String.format("Truy cập bị từ chối: đường dẫn=%s, resource=%s", path, resource);
        return logAction("ERROR", username, roles, msg, ipAddress);
    }

    public boolean logForbiddenAction(String username, String path, String resource, Integer permission,
                                      String ipAddress, List<String> roles) {
        String msg = String.format("Không đủ quyền thực hiện chức năng: đường dẫn=%s, resource=%s, currentRoles=%s", path, resource, permission);
        return logAction("WARN", username, roles, msg, ipAddress);
    }

}
