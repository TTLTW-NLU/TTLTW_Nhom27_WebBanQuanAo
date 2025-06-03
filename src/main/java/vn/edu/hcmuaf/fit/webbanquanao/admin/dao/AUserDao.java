package vn.edu.hcmuaf.fit.webbanquanao.admin.dao;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;

import java.sql.*;
import java.util.*;

public class AUserDao {

    public Map<String, AUser> listUser;

    public AUserDao() {
        listUser = getAllUser();
    }

    public Map<String, AUser> getAllUser() {
        Map<String, AUser> users = new LinkedHashMap<>();

        // Lấy thông tin cơ bản của user
        String sql = "SELECT u.id, u.userName, u.passWord, u.firstName, u.lastName, u.email, " +
                "       u.avatar, u.address, u.phone, u.createdAt, u.status " +
                "FROM users u " +
                "ORDER BY u.id DESC";


        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String userName = rs.getString("userName");
                    int userId = rs.getInt("id");

                    AUser user = new AUser();
                    user.setId(userId);
                    user.setUserName(userName);
                    user.setPassWord(rs.getString("passWord"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    user.setEmail(rs.getString("email"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setAddress(rs.getString("address"));
                    user.setPhone(rs.getString("phone"));
                    user.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
                    user.setStatus(rs.getInt("status"));

                    users.put(userName, user);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi lấy danh sách user: " + e.getMessage());
            }
            return users;
        });
    }

    public int getUserIdByUserName(String userName) {
        String sql = "SELECT id FROM users WHERE userName = ?";
        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("id");
                    }
                }
            } catch (SQLException e) {
                System.out.println("Lỗi khi lấy ID của user: " + e.getMessage());
            }
            return -1; // Trả về -1 nếu không tìm thấy
        });
    }

    public boolean create(Object obj) {
        AUser user = (AUser) obj;
        listUser.put(user.getUserName(), user);

        String sql = "INSERT INTO users (userName, password, firstName, lastName, email, avatar, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        return JDBIConnector.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // Mã hóa mật khẩu trước khi lưu
                String hashedPassword = BCrypt.hashpw(user.getPassWord(), BCrypt.gensalt());

                ps.setString(1, user.getUserName());
                ps.setString(2, hashedPassword);  // Lưu mật khẩu đã mã hóa
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getAvatar());
                ps.setString(7, user.getAddress());
                ps.setString(8, user.getPhone());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);
                            user.setId(newId); // Lưu ID mới vào đối tượng User
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi tạo user: " + e.getMessage());
            }
            return false;
        });
    }



    public boolean update(Object obj, String userName) {
        return JDBIConnector.get().withHandle(handle -> {
            AUser user = (AUser) obj;
            listUser.replace(userName, user);

            // 1️⃣ Chỉ cập nhật thông tin user trong bảng `users`
            String updateUserSQL = "UPDATE users SET id=?, userName=?, firstName=?, lastName=?, email=?, avatar=?, address=?, phone=?, status=?, createdAt=? WHERE userName=?";

            try (PreparedStatement ps = handle.getConnection().prepareStatement(updateUserSQL)) {
                ps.setInt(1, user.getId());
                ps.setString(2, user.getUserName());
                ps.setString(3, user.getFirstName());
                ps.setString(4, user.getLastName());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getAvatar());
                ps.setString(7, user.getAddress());
                ps.setString(8, user.getPhone());
                ps.setInt(9, user.getStatus());
                ps.setDate(10, java.sql.Date.valueOf(user.getCreatedAt().toLocalDate()));
                ps.setString(11, userName);

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu cập nhật thành công ít nhất 1 dòng
            } catch (SQLException e) {
                System.out.println("Lỗi khi cập nhật user: " + e.getMessage());
                return false;
            }
        });
    }


    public boolean delete(String userName, Integer status) {
        return JDBIConnector.get().withHandle(handle -> {
            String sql = "UPDATE users SET status = ? WHERE userName = ?";
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setInt(1, status);
                ps.setString(2, userName);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Lỗi khi khóa user: " + e.getMessage());
            }
            return false;
        });
    }

    /*===========================================================================*/
    /*---------------------Dao for Role Permission User--------------------------*/
    /*===========================================================================*/

//    public Map<String, AUserRolePermission> getRolePermission(String username) {
//        Map<String, AUserRolePermission> result = new HashMap<>();
//        String sql = "SELECT u.userName, u.firstName," +
//                "       GROUP_CONCAT(DISTINCT r.roleName ORDER BY r.roleName ASC) AS roles, " +
//                "       GROUP_CONCAT(DISTINCT CONCAT(res.resourceName, ':', rr.permission) ORDER BY res.resourceName ASC) AS permissions " +
//                "FROM users u " +
//                "LEFT JOIN user_roles ur ON u.id = ur.userId " +
//                "LEFT JOIN roles r ON ur.roleId = r.id " +
//                "LEFT JOIN role_resource rr ON r.id = rr.roleId " +
//                "LEFT JOIN resource res ON rr.resourceId = res.id " +
//                "WHERE u.userName = ? " +
//                "GROUP BY u.id, u.userName";
//
//        JDBIConnector.get().useHandle(handle -> {
//            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
//                ps.setString(1, username);
//                try (ResultSet rs = ps.executeQuery()) {
//                    if (rs.next()) {
//                        AUserRolePermission rp = new AUserRolePermission();
//
//
//                        rp.setUserName(rs.getString("userName"));
//                        rp.setFirstName(rs.getString("firstName"));
//
//
//                        String rolesStr = rs.getString("roles");
//                        rp.setRoles((rolesStr != null && !rolesStr.trim().isEmpty()) ?
//                                new ArrayList<>(Arrays.asList(rolesStr.split(","))) :
//                                new ArrayList<>());
//
//
//                        Map<String, Integer> permissionsMap = new HashMap<>();
//                        String permissionsStr = rs.getString("permissions");
//                        if (permissionsStr != null && !permissionsStr.trim().isEmpty()) {
//                            String[] permissionsArray = permissionsStr.split(",");
//                            for (String perm : permissionsArray) {
//                                String[] parts = perm.split(":");
//                                if (parts.length == 2) {
//                                    try {
//                                        permissionsMap.put(parts[0], Integer.parseInt(parts[1]));
//                                    } catch (NumberFormatException ex) {
//                                        System.out.println("Lỗi parse quyền: " + perm);
//                                    }
//                                }
//                            }
//                        }
//                        rp.setPermissions(permissionsMap);
//
//                        result.put(rp.getUserName(), rp);
//                    }
//                }
//            } catch (Exception e) {
//                System.out.println("AUserDao: Lỗi khi lấy quyền hạn: " + e.getMessage());
//            }
//        });
//        return result;
//    }

    public Map<String, AUserRolePermission> getUserRolesByUserName(String userName){
        Map<String, AUserRolePermission> result = new HashMap<>();
        String sql = "SELECT u.userName, u.firstName, " +
                "       GROUP_CONCAT(DISTINCT r.roleName ORDER BY r.roleName ASC) AS roles " +
                "FROM users u " +
                "LEFT JOIN user_roles ur ON u.id = ur.userId " +
                "LEFT JOIN roles r ON ur.roleId = r.id " +
                "WHERE u.userName = ? " +
                "GROUP BY u.id, u.userName";

        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        AUserRolePermission rp = new AUserRolePermission();
                        rp.setUserName(rs.getString("userName"));
                        rp.setFirstName(rs.getString("firstName"));

                        String rolesStr = rs.getString("roles");
                        rp.setRoles((rolesStr != null && !rolesStr.trim().isEmpty()) ?
                                new ArrayList<>(Arrays.asList(rolesStr.split(","))) :
                                new ArrayList<>());

                        // Không cần xử lý permission nữa
                        rp.setPermissions(new HashMap<>()); // nếu cần set rỗng

                        result.put(rp.getUserName(), rp);
                    }
                }
            } catch (Exception e) {
                System.out.println("AUserDao: Lỗi khi lấy role: " + e.getMessage());
            }
        });

        return result;
    }

    public List<String> getRoleNameByUserName(String username) {
        List<String> roleNames = new ArrayList<>();
        String sql = "SELECT r.roleName " +
                "FROM users u " +
                "JOIN user_roles ur ON u.id = ur.userId " +
                "JOIN roles r ON ur.roleId = r.id " +
                "WHERE u.userName = ?";

        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        roleNames.add(rs.getString("roleName"));
                    }
                }
            } catch (Exception e) {
                System.out.println("AUserDao: Lỗi khi lấy quyền hạn: " + e.getMessage());
            }
        });
        return roleNames;
    }

    public List<Integer> getRoleIdByRoleName(List<String> roles) {
        List<Integer> roleIds = new ArrayList<>();
        String sql = "SELECT id FROM roles WHERE roleName = ?";

        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                for (String role : roles) {
                    ps.setString(1, role);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            roleIds.add(rs.getInt("id"));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("ServerD: Loi khi lay danh sach id quyen: " + e.getMessage());
            }
        });
        return roleIds;
    }

    public List<Integer> getRoleIdsByUserName(String username) {
        List<Integer> roleIds = new ArrayList<>();
        String sql = "SELECT r.id FROM users u " +
                "JOIN user_roles ur ON u.id = ur.userId " +
                "JOIN roles r ON ur.roleId = r.id " +
                "WHERE u.userName = ?";

        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        roleIds.add(rs.getInt("id"));
                    }
                }
            } catch (Exception e) {
                System.out.println("ServerD: Loi khi lay danh sach id quyen: " + e.getMessage());
            }
        });
        return roleIds;
    }

    public void addUserRoles(List<Integer> roleIds, String userName) {
        String sql = "INSERT INTO user_roles (userId, roleId) VALUES (?, ?)";
        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                for (Integer roleId : roleIds) {
                    ps.setInt(1, getUserIdByUserName(userName));
                    ps.setInt(2, roleId);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("ServerD: Loi khi them quyen cho User: " + e.getMessage());
            }
        });
    }

    public void removeUserRoles(List<Integer> roleIds, String userName) {
        String sql = "DELETE FROM user_roles WHERE userId = ? AND roleId = ?";
        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                for (Integer roleId : roleIds) {
                    ps.setInt(1, getUserIdByUserName(userName));
                    ps.setInt(2, roleId);
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println("ServerD: Loi khi xoa quyen cho User: " + e.getMessage());
            }
        });
    }

    public void updateUserPermissions(String userName, Map<String, Integer> newPermissions) {
        String sql = "UPDATE role_resource SET permission = ? WHERE roleId = ? AND resourceId = (SELECT id FROM resource WHERE resourceName = ?)";
        List<Integer> roleIds = getRoleIdsByUserName(userName); // gọi hàm mới

        JDBIConnector.get().useHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(sql)) {
                for (Integer roleId : roleIds) {
                    for (Map.Entry<String, Integer> entry : newPermissions.entrySet()) {
                        ps.setInt(1, entry.getValue());
                        ps.setInt(2, roleId);
                        ps.setString(3, entry.getKey());
                        ps.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                System.out.println("ServerD: Lỗi khi cập nhật quyền cho User: " + e.getMessage());
            }
        });
    }

}
