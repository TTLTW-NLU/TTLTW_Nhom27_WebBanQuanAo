package vn.edu.hcmuaf.fit.webbanquanao.admin.service;

import vn.edu.hcmuaf.fit.webbanquanao.admin.dao.AUserDao;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.util.*;

import vn.edu.hcmuaf.fit.webbanquanao.util.RoleNames;

public class AUserService {


    private static final Integer IS_DELETED = 4; // Thêm hằng số IS_DELETED cho trạng thái xóa

    private final AUserDao uDao;

    public AUserService() {
        this.uDao = new AUserDao();
    }

    public Map<String, AUser> showUser() {
        AUserDao uDao = new AUserDao();
        return uDao.listUser;
    }

    public boolean addUser(AUser user) {
        if (uDao.listUser.containsKey(user.getUserName())) return false;
        return uDao.create(user);
    }

    public boolean updateUser(AUser user, String userName) {
        AUserDao uDao = new AUserDao();
        return uDao.update(user, getUserByUsername(userName).getUserName());
    }

    public AUser getUserByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username)) return null;
        return uDao.listUser.get(username);
    }

    public Map<String, AUserRolePermission> getUserRoleByUsername(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username)) return null;
        return uDao.getUserRolesByUserName(username);
    }

    public boolean deleteUser(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username)) return false;
        AUser user = uDao.listUser.get(username);
        if (user.getStatus().equals(IS_DELETED)) return false;
        boolean isDeletedInDB = uDao.delete(username, IS_DELETED);
        if (isDeletedInDB) user.setStatus(IS_DELETED);
        return isDeletedInDB;
    }

    public List<Integer> getRoleIdsByRoleName(List<String> roleNames) {
        AUserDao uDao = new AUserDao();
        if (roleNames == null || roleNames.isEmpty()) return null;
        return uDao.getRoleIdByRoleName(roleNames);
    }

    public List<String> getRoleNameByUserName(String username) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(username)) return null;
        return uDao.getRoleNameByUserName(username);
    }

    public boolean updateUserRolesWithPermissionCheck(User currentUser, String targetUserName, List<String> newRolesName) {
        AUserDao uDao = new AUserDao();
        if (!uDao.listUser.containsKey(targetUserName)) return false;
        AUser user = uDao.listUser.get(targetUserName);
        if (user.getStatus().equals(IS_DELETED)) return false;
        // Kiểm tra quyền của người dùng hiện tại đối với người cần chỉnh sửa quyền
        if (!canModifyUserRole(currentUser.getUserName(), targetUserName)) return false;
        // Quản lý các thay đổi về role
        ManageUserRoles(targetUserName, newRolesName);
        return true;
    }

    public void ManageUserRoles(String userName, List<String> newRolesName) {
        AUserDao uDao = new AUserDao();

        // Lấy danh sách role hiện tại từ DB
        List<String> currentRoleNames = uDao.getRoleNameByUserName(userName);

        // Tạo các set để so sánh
        Set<String> currentRolesSet = new HashSet<>(currentRoleNames);
        Set<String> newRolesSet = new HashSet<>(newRolesName);

        // Các role cần thêm
        Set<String> rolesToAdd = new HashSet<>(newRolesSet);
        rolesToAdd.removeAll(currentRolesSet);

        // Các role cần xóa
        Set<String> rolesToRemove = new HashSet<>(currentRolesSet);
        rolesToRemove.removeAll(newRolesSet);

        // Lấy ID của các role cần thêm và cần xóa
        List<Integer> roleIdsToAdd = uDao.getRoleIdByRoleName(new ArrayList<>(rolesToAdd));
        List<Integer> roleIdsToRemove = uDao.getRoleIdByRoleName(new ArrayList<>(rolesToRemove));

        // Thêm và xóa role từ DB
        uDao.addUserRoles(roleIdsToAdd, userName);
        uDao.removeUserRoles(roleIdsToRemove, userName);
    }

    public boolean canModifyUserRole(String currentUserName, String targetUserName) {
        AUserDao uDao = new AUserDao();

        if (!uDao.listUser.containsKey(targetUserName)) return false;
        if (currentUserName.equals(targetUserName)) return false;

        List<String> currentUserRoles = uDao.getRoleNameByUserName(currentUserName);
        List<String> targetUserRoles = uDao.getRoleNameByUserName(targetUserName);

        // Kiểm tra nếu người dùng hiện tại có quyền ADMIN
        boolean isCurrentUserAdmin = currentUserRoles.contains(RoleNames.ADMIN_ROLE);
        // Kiểm tra nếu người dùng hiện tại có quyền MANAGER
        boolean isCurrentUserManager = currentUserRoles.contains(RoleNames.MANAGER_ROLE);

        // ADMIN có thể thao tác với tất cả người dùng
        if (isCurrentUserAdmin) return true;

        // MANAGER chỉ có thể thay đổi quyền của USER và không thể thay đổi quyền của chính mình hoặc người cùng quyền
        if (isCurrentUserManager) {
            return targetUserRoles.contains(RoleNames.USER_ROLE); // Chỉ có thể thay đổi quyền của USER
        }

        // USER không có quyền thay đổi quyền của ai
        return false;
    }

    public static void main(String[] args) {
        AUserService userService = new AUserService();
        System.out.println(userService.getUserRoleByUsername("user1"));
    }
}
