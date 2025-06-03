package vn.edu.hcmuaf.fit.webbanquanao.admin.dao;

import vn.edu.hcmuaf.fit.webbanquanao.admin.model.UserLogs;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserLogsDao {

    public boolean logUserAction(UserLogs userLog) {
        // Chuyển List roles thành chuỗi ngăn cách bằng dấu phẩy
        String roles = userLog.getRoles().stream().collect(Collectors.joining(","));

        String spl = "INSERT INTO userLogs (level, username, roles, action, ipAddress) VALUES (?, ?, ?, ?, ?)";
        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(spl)) {
                ps.setString(1, userLog.getLevel());
                ps.setString(2, userLog.getUsername());
                ps.setString(3, roles);  // Lưu roles dưới dạng chuỗi
                ps.setString(4, userLog.getAction());
                ps.setString(5, userLog.getIpAddress());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }
}
