package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UserRoles implements Serializable {
    private Integer userId;
    private Integer roleId;

    public UserRoles() {
    }

    public UserRoles(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
