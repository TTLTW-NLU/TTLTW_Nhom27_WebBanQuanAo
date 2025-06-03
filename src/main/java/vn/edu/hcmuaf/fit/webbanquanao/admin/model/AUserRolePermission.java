package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.util.List;
import java.util.Map;

public class AUserRolePermission {
    private String userName;
    private String firstName;
    private List<String> roles;
    private Map<String, Integer> permissions;

    public AUserRolePermission() {
    }

    public AUserRolePermission(String userName, String firstName, List<String> roles, Map<String, Integer> permissions) {
        this.userName = userName;
        this.firstName = firstName;
        this.roles = roles;
        this.permissions = permissions;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(Map<String, Integer> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "AUserRolePermission{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
