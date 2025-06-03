package vn.edu.hcmuaf.fit.webbanquanao.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;
    private String firstName;
    private String lastName;
    private String email;
    private String avatar;
    private String address;
    private Integer phone;
    private Integer status;
    private LocalDateTime createdAt;
    private List<String> roles; // Danh sách role
    private Map<String, Integer> permissions; // Key: Resource, Value: Quyền (rwx dưới dạng số)


    public User() {
    }

    public User(String s, String password, String firstName, String lastName, String gmail, String avatar, String address, Integer phone) {
    }

    public User(String userName, String firstName, String lastName, String email, String passWord) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passWord = passWord;
        this.roles = new ArrayList<>();  // Khởi tạo roles là một ArrayList rỗng
        this.permissions = new HashMap<>(); // Khởi tạo permissions là một HashMap rỗng
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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


    // Kiểm tra role
    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }

    // Kiểm tra quyền theo bitwise (r=4, w=2, x=1)
    public boolean hasPermission(String resource, String action) {
        if (permissions == null || !permissions.containsKey(resource)) return false;

        int permission = permissions.get(resource);
        int requiredPermission = switch (action) {
            case "GET" -> 4;
            case "POST" -> 2;
            case "DELETE" -> 1;
            default -> 0;
        };
        return (permission & requiredPermission) == requiredPermission;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", roles=" + roles +
                ", permissions=" + permissions +
                '}';
    }
}
