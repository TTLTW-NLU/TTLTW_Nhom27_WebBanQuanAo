package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUser;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

import java.io.IOException;
import java.util.*;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

@WebServlet(name = "UsersApi", urlPatterns = "/admin/api/users/*")
public class UsersApi extends BaseApiServlet {
    private final AUserService userService = new AUserService();
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_USER_MANAGE);
        String username = extractId(req.getPathInfo());

        if (username == null) {
            List<AUser> users = new ArrayList<>(userService.showUser().values());

            Object viewedFlag = ctx.session.getAttribute("viewAllUsers");
            if (!Boolean.TRUE.equals(viewedFlag)) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), ResourceNames.ADMIN_API_USER_MANAGE, ctx.permissions, ctx.ip, ctx.roles);
                ctx.session.setAttribute("viewAllUsers", Boolean.TRUE);
            }
            writeJson(resp, users);
        } else {
            AUser user = userService.getUserByUsername(username);
            if (user != null) {
                writeJson(resp, user);
            } else {
                logService.logCustom(ctx.username, "WARN", "User not found: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_USER_MANAGE);
        try {
            AUser user = gson.fromJson(readBody(req), AUser.class);
            validateCreate(user);

            boolean created = userService.addUser(user);
            if (!created) {
                logService.logCreateEntity(ctx.username, ResourceNames.ADMIN_API_USER_MANAGE, user.getUserName(), ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_CONFLICT, "User đã tồn tại hoặc không thể tạo");
                return;
            }

            logService.logCreateEntity(ctx.username, ResourceNames.ADMIN_API_USER_MANAGE, user.getUserName(), ctx.ip, ctx.roles);
            sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo user thành công");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo user");
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_USER_MANAGE);
        String username = extractId(req.getPathInfo());
        if (username == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu username trong URL");
            return;
        }
        try {
            AUser user = gson.fromJson(readBody(req), AUser.class);
            validateUpdate(user);
            boolean updated = userService.updateUser(user, username);
            if (updated) {
                logService.logUpdateEntity(ctx.username, ResourceNames.USER, username, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật user thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update failed: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật user");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_USER_MANAGE);
        String username = extractId(req.getPathInfo());
        if (username == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu username trong URL");
            return;
        }
        try {
            boolean deleted = userService.deleteUser(username);
            if (deleted) {
                logService.logDeleteEntity(ctx.username, ResourceNames.USER, username, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa user thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Delete failed: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            }
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa user");
        }
    }

    // Validation methods with detailed error messages
    private void validateCreate(AUser u) {
        StringBuilder errors = new StringBuilder();
        if (u.getUserName() == null || u.getUserName().trim().isEmpty())
            errors.append("Tên đăng nhập bị thiếu. ");
        if (u.getPassWord() == null || u.getPassWord().trim().isEmpty())
            errors.append("Mật khẩu bị thiếu. ");
        if (errors.length() > 0)
            throw new IllegalArgumentException(
                    "Lỗi khi tạo User: " + errors.toString().trim()
            );
    }

    private void validateUpdate(AUser u) {
        StringBuilder errors = new StringBuilder();
        if (u.getUserName() == null || u.getUserName().trim().isEmpty())
            errors.append("Tên đăng nhập không được để trống. ");
        if (errors.length() > 0){
            throw new IllegalArgumentException(
                    "Lỗi khi cập nhật User (username=" + u.getUserName() + "): "
                            + errors.toString().trim()
            );}
    }
}