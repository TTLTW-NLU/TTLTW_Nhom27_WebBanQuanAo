package vn.edu.hcmuaf.fit.webbanquanao.admin.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class UserLogs implements Serializable {
    private int id;
    private String level;
    private String username;
    private List<String> roles;
    private String action;
    private String ipAddress;
    private LocalDateTime createdAt;

    public UserLogs() {
    }

    public UserLogs(int id, String level, String username, List<String> roles, String action, String ipAddress, LocalDateTime createdAt) {
        this.id = id;
        this.level = level;
        this.username = username;
        this.roles = roles;
        this.action = action;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserLogs{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", action='" + action + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
