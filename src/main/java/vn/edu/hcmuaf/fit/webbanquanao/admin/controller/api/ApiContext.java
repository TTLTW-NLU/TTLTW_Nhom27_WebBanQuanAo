package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.util.List;

import jakarta.servlet.http.HttpSession;

public class ApiContext {
    public final String username;
    public final Integer permissions;
    public final List<String> roles;
    public final String ip;
    public final HttpSession session;

    public ApiContext(HttpServletRequest req, String permissionKey) {
        this.session = req.getSession();
        User user = (User) session.getAttribute("auth");
        this.username = (user != null) ? user.getUserName() : "anonymous";
        this.roles = (user != null) ? user.getRoles() : List.of();
        this.permissions = (user != null && user.getPermissions() != null)
                ? user.getPermissions().getOrDefault(permissionKey, 0)
                : 0;
        this.ip = req.getRemoteAddr();

    }
}
