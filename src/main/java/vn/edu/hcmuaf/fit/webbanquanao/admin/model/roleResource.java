package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;

public class roleResource implements Serializable {
    Integer roleId;
    Integer resourceId;
    Integer permission;

    public roleResource() {
    }

    public roleResource(Integer roleId, Integer resourceId, Integer permission) {
        this.roleId = roleId;
        this.resourceId = resourceId;
        this.permission = permission;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getPermissionValue() {
        return permission;
    }

    public void setPermissionValue(Integer permissionId) {
        this.permission = permissionId;
    }

    @Override
    public String toString() {
        return "roleResource{" +
                "roleId=" + roleId +
                ", resourceId=" + resourceId +
                ", permission=" + permission +
                '}';
    }
}
