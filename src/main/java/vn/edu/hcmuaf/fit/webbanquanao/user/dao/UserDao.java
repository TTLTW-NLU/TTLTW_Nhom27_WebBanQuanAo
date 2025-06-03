package vn.edu.hcmuaf.fit.webbanquanao.user.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.TokenForgotPassword;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class UserDao {

    JDBIConnector dbConnect;

    public Map<String, User> listUser;

    public UserDao() {
        dbConnect = new JDBIConnector();
        listUser = getAllUser();
    }

    public Map<String, User> getAllUser() {
        Map<String, User> users = new LinkedHashMap<>();
        String sql = "SELECT u.id, u.userName, u.passWord, u.firstName, u.lastName, u.email, " +
                "       u.avatar, u.address, u.phone, u.createdAt, u.status, " +
                "       GROUP_CONCAT(DISTINCT r.roleName ORDER BY r.roleName ASC) AS roles, " +
                "       GROUP_CONCAT(DISTINCT CONCAT(res.resourceName, ':', rr.permission) ORDER BY res.resourceName ASC) AS permissions " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.id = ur.userId " +
                "LEFT JOIN roles r ON ur.roleId = r.id " +
                "LEFT JOIN role_resource rr ON r.id = rr.roleId " +
                "LEFT JOIN resource res ON rr.resourceId = res.id " +
                "GROUP BY u.id " +
                "ORDER BY u.id DESC;";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String userName = rs.getString("userName");

                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(userName);
                    user.setPassWord(rs.getString("passWord"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getInt("phone"));
                    user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    user.setStatus(rs.getInt("status"));

                    // Xử lý danh sách role
                    user.setRoles(rs.getString("roles") != null ?
                            new ArrayList<>(Arrays.asList(rs.getString("roles").split(","))) :
                            new ArrayList<>());

                    // Xử lý quyền theo dạng Map<Resource, Permission>
                    Map<String, Integer> permissionsMap = new HashMap<>();
                    if (rs.getString("permissions") != null) {
                        String[] permissionsArray = rs.getString("permissions").split(",");
                        for (String perm : permissionsArray) {
                            String[] parts = perm.split(":");
                            if (parts.length == 2) {
                                String resource = parts[0];
                                int permissionValue = Integer.parseInt(parts[1]);
                                permissionsMap.put(resource, permissionValue);
                            }
                        }
                    }
                    user.setPermissions(permissionsMap);

                    users.put(userName, user);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi lấy danh sách user: " + e.getMessage());
            }
            return users;
        });
    }

    public boolean registerUser(User user) {
        String userSql = "INSERT INTO users (userName, avatar, password, firstName, lastName, email, phone, address, status, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String checkRoleSql = "SELECT COUNT(*) FROM user_roles WHERE userId = ? AND roleId = ?";
        String insertRoleSql = "INSERT INTO user_roles (userId, roleId) VALUES (?, ?)";

        String hashedPassword = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt());

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection()) {
                conn.setAutoCommit(false);

                try (PreparedStatement userPs = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                    // Thiết lập các tham số cho user
                    userPs.setString(1, user.getUserName());
                    userPs.setString(2, user.getAvatar());
                    userPs.setString(3, hashedPassword);
                    userPs.setString(4, user.getFirstName());
                    userPs.setString(5, user.getLastName());
                    userPs.setString(6, user.getEmail());

                    if (user.getPhone() != null) {
                        userPs.setInt(7, user.getPhone());
                    } else {
                        userPs.setNull(7, java.sql.Types.INTEGER);
                    }

                    userPs.setString(8, user.getAddress());
                    int status = (user.getStatus() != null) ? user.getStatus() : 0;
                    userPs.setInt(9, status);

                    Timestamp createdAt = (user.getCreatedAt() != null) ?
                            Timestamp.valueOf(user.getCreatedAt()) :
                            new Timestamp(System.currentTimeMillis());
                    userPs.setTimestamp(10, createdAt);

                    int rowsAffected = userPs.executeUpdate();
                    if (rowsAffected == 0) throw new SQLException("Tạo người dùng thất bại!");

                    try (ResultSet generatedKeys = userPs.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int userId = generatedKeys.getInt(1);

                            // Sử dụng phiên bản getRoleId mới với Connection
                            int roleId = getRoleId(conn, "USER"); // Thay đổi ở đây
                            if (roleId == -1) {
                                throw new SQLException("Không tìm thấy role 'USER'!");
                            }

                            // Kiểm tra và gán vai trò
                            try (PreparedStatement checkRolePs = conn.prepareStatement(checkRoleSql)) {
                                checkRolePs.setInt(1, userId);
                                checkRolePs.setInt(2, roleId);
                                try (ResultSet rs = checkRolePs.executeQuery()) {
                                    if (rs.next() && rs.getInt(1) > 0) {
                                        System.out.println("User đã có vai trò này, không cần thêm!");
                                    } else {
                                        try (PreparedStatement rolePs = conn.prepareStatement(insertRoleSql)) {
                                            rolePs.setInt(1, userId);
                                            rolePs.setInt(2, roleId);
                                            rolePs.executeUpdate();
                                        }
                                    }
                                }
                            }
                        } else {
                            throw new SQLException("Tạo người dùng thất bại, không có ID!");
                        }
                    }

                    conn.commit();
                    return true;
                } catch (SQLException e) {
                    conn.rollback();
                    System.err.println("Lỗi khi đăng ký người dùng: " + e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("Lỗi kết nối database: " + e.getMessage());
                return false;
            }
        });
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, email = ?, phone = ?, address = ? WHERE id = ?";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setInt(4, user.getPhone());
                ps.setString(5, user.getAddress());
                ps.setInt(6, user.getId());

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật user: " + e.getMessage());
                return false;
            }
        });
    }

    public String getRoleNameById(int roleId) {
        String sql = "SELECT roleName FROM roles WHERE id = ?";
        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, roleId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("roleName");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null; // Return null if role not found
        });
    }

    private int getRoleId(Connection conn, String roleName) throws SQLException {
        String sql = "SELECT id FROM roles WHERE roleName = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id") : -1;
            }
        }
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE userName = ?";
        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public boolean createUser(User user) {
        String sql = "INSERT INTO users (userName, firstName, lastName, email, password, status, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?)";

        String defaultPassword = BCrypt.hashpw("defaultPassword123", BCrypt.gensalt());

        return dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection()) {
                conn.setAutoCommit(false);

                try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, user.getUserName());
                    ps.setString(2, user.getFirstName());
                    ps.setString(3, user.getLastName());
                    ps.setString(4, user.getEmail());
                    ps.setString(5, defaultPassword);
                    ps.setInt(6, 1); // Mặc định status = 1 (active)
                    ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));

                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        try (ResultSet rs = ps.getGeneratedKeys()) {
                            if (rs.next()) {
                                int userId = rs.getInt(1);

                                // Gán vai trò USER cho người dùng mới
                                if (!assignUserRole(conn, userId)) {
                                    throw new SQLException("Không thể gán vai trò cho user!");
                                }

                                conn.commit();
                                return true;
                            }
                        }
                    }
                    conn.rollback();
                } catch (SQLException e) {
                    conn.rollback();
                    throw new RuntimeException("Lỗi khi tạo user: " + e.getMessage(), e);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Lỗi kết nối database: " + e.getMessage(), e);
            }
            return false;
        });
    }

    private boolean assignUserRole(Connection conn, int userId) throws SQLException {
        // Tìm ID của role USER
        String findRoleSql = "SELECT id FROM roles WHERE roleName = 'USER'";
        try (PreparedStatement ps = conn.prepareStatement(findRoleSql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int roleId = rs.getInt("id");

                // Kiểm tra xem đã gán role chưa
                String checkSql = "SELECT 1 FROM user_roles WHERE userId = ? AND roleId = ?";
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
                    checkPs.setInt(1, userId);
                    checkPs.setInt(2, roleId);
                    try (ResultSet checkRs = checkPs.executeQuery()) {
                        if (checkRs.next()) {
                            return true; // Đã tồn tại
                        }
                    }
                }

                // Nếu chưa tồn tại thì thêm mới
                String insertSql = "INSERT INTO user_roles (userId, roleId) VALUES (?, ?)";
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setInt(1, userId);
                    insertPs.setInt(2, roleId);
                    return insertPs.executeUpdate() > 0;
                }
            } else {
                throw new SQLException("Không tìm thấy role USER trong hệ thống");
            }
        }
    }

    public boolean updatePassword(String email, String hashedPassword) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, hashedPassword);
                ps.setString(2, email);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public String getEmailByUserName(String userName) {
        String sql = "SELECT email FROM users WHERE userName = ?";
        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("email");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy email theo username: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        });
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUserName(rs.getString("userName"));
                        user.setPassWord(rs.getString("passWord"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        user.setAvatar(rs.getString("avatar"));
                        user.setPhone(rs.getInt("phone"));
                        user.setAddress(rs.getString("address"));
                        user.setStatus(rs.getInt("status"));
//                        user.setRoleId(rs.getInt("roleId"));
                        user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                        return user;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        });
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id); // Sử dụng ID để tìm người dùng
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUserName(rs.getString("userName"));
                        user.setPassWord(rs.getString("passWord"));
                        user.setFirstName(rs.getString("firstName")); // Thêm thông tin nếu cần
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        return user;
                    }

                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
                e.printStackTrace(); // In chi tiết lỗi để debug
            }
            return null;
        });
    }
    public User getUserByUserName(String userName) {
        String sql = "SELECT * FROM users WHERE userName = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUserName(rs.getString("userName"));
                        user.setPassWord(rs.getString("passWord"));
                        user.setFirstName(rs.getString("firstName"));
                        user.setLastName(rs.getString("lastName"));
                        user.setEmail(rs.getString("email"));
                        user.setAvatar(rs.getString("avatar"));
                        user.setPhone(rs.getInt("phone"));
                        user.setAddress(rs.getString("address"));
                        user.setStatus(rs.getInt("status"));
                        // user.setRoleId(rs.getInt("roleId")); // nếu có trường roleId
                        user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                        return user;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi lấy thông tin người dùng theo userName: " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        });
    }

    public boolean updateUserStatus(String email, int status) {
        String sql = "UPDATE users SET status = ? WHERE email = ?";

        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setInt(1, status);
                ps.setString(2, email);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Lỗi khi cập nhật trạng thái người dùng: " + e.getMessage());
                return false;
            }
        });
    }

    public User findByEmail(String email) {
        String sql = "SELECT u.id, u.userName, u.passWord, u.firstName, u.lastName, u.email, " +
                "       u.avatar, u.address, u.phone, u.createdAt, u.status, " +
                "       GROUP_CONCAT(DISTINCT r.roleName ORDER BY r.roleName ASC) AS roles, " +
                "       GROUP_CONCAT(DISTINCT CONCAT(res.resourceName, ':', rr.permission) ORDER BY res.resourceName ASC) AS permissions " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.id = ur.userId " +
                "LEFT JOIN roles r ON ur.roleId = r.id " +
                "LEFT JOIN role_resource rr ON r.id = rr.roleId " +
                "LEFT JOIN resource res ON rr.resourceId = res.id " +
                "WHERE u.email = ? " +
                "GROUP BY u.id " +
                "LIMIT 1";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("userName"));
                    user.setPassWord(rs.getString("passWord"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getInt("phone"));
                    user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    user.setStatus(rs.getInt("status"));

                    // Roles
                    user.setRoles(rs.getString("roles") != null ?
                            new ArrayList<>(Arrays.asList(rs.getString("roles").split(","))) :
                            new ArrayList<>());

                    // Permissions
                    Map<String, Integer> permissionsMap = new HashMap<>();
                    if (rs.getString("permissions") != null) {
                        String[] permissionsArray = rs.getString("permissions").split(",");
                        for (String perm : permissionsArray) {
                            String[] parts = perm.split(":");
                            if (parts.length == 2) {
                                permissionsMap.put(parts[0], Integer.parseInt(parts[1]));
                            }
                        }
                    }
                    user.setPermissions(permissionsMap);

                    return user;
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi tìm user theo email: " + e.getMessage());
            }
            return null;
        });
    }


    // Cập nhật trạng thái của token
    public void updateStatus(TokenForgotPassword token) {
        System.out.println("token=" + token);
        String sql = "UPDATE users SET isUser = ? WHERE token = ?";
        dbConnect.get().withHandle(handle -> {
            try (Connection conn = handle.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBoolean(1, token.isIsUsed());
                ps.setString(2, token.getToken());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e);
            }
            return null;
        });
    }

    // Đổi mật khẩu trong trang cá nhân
    public boolean changePassword(String userName, String hashedPassword) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, hashedPassword); // Lưu mật khẩu đã mã hóa
                ps.setString(2, userName);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public boolean changePasswordByUserId(int userId, String hashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        return dbConnect.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, hashedPassword);
                ps.setInt(2, userId);
                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.err.println("Lỗi khi đổi mật khẩu người dùng: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        });
    }

    public String getPassWordByUserName(String userName) {
        String sql = "SELECT password FROM users WHERE userName = ?";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("passWord");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String inputPassword, String hashedPassword) {
        return BCrypt.checkpw(inputPassword, hashedPassword);
    }

    public List<String> getRoleNameByUserName(String userName) {
        String sql = """
                    SELECT r.roleName 
                    FROM users u 
                    JOIN user_roles ur ON u.id = ur.userId 
                    JOIN roles r ON ur.roleId = r.id 
                    WHERE u.userName = ?
                """;

        List<String> roles = new ArrayList<>();
        try (Connection conn = JDBIConnector.get().open().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getString("roleName"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).severe("Lỗi truy vấn roles: " + e.getMessage());
        }
        return roles;
    }

    public Map<String, Integer> getPermissionByUserName(String userName) {
        String sql = """
                    SELECT res.resourceName, SUM(rr.permission) as permission
                    FROM users u
                    JOIN user_roles ur ON u.id = ur.userId
                    JOIN roles r ON ur.roleId = r.id
                    JOIN role_resource rr ON r.id = rr.roleId
                    JOIN resource res ON rr.resourceId = res.id
                    WHERE u.userName = ?
                    GROUP BY res.resourceName
                """;

        Map<String, Integer> permissions = new HashMap<>();
        try (Connection conn = JDBIConnector.get().open().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    permissions.put(rs.getString("resourceName"), rs.getInt("permission"));
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).severe("Lỗi truy vấn permissions: " + e.getMessage());
        }
        return permissions;
    }
}
