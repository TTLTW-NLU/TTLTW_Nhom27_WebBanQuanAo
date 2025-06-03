package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AUserRolePermission;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AUserService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

@WebServlet(name = "UserRolePermissionsApi", urlPatterns = "/admin/api/roleUser/*")
public class UserRolePermissionsApi extends BaseApiServlet {
    private final AUserService userService = new AUserService();
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ROLE_USER_MANAGE);
        String pathId = extractId(req.getPathInfo());
        if (pathId == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu username trong URL");
            logService.logCustom(ctx.username, "ERROR", "Thiếu username khi GET roleUser", ctx.ip, ctx.roles);
            return;
        }

        String username = pathId;
        try {
            Map<String, AUserRolePermission> rolePermMap = userService.getUserRoleByUsername(username);
            if (rolePermMap == null) {
                logService.logCustom(ctx.username, "WARN", "Không tìm thấy user: " + username, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy user");
            } else {
                List<AUserRolePermission> list = List.copyOf(rolePermMap.values());
                writeJson(resp, list);
            }
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", "Lỗi server GET roleUser: " + e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi lấy quyền user");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ROLE_USER_MANAGE);
        User currentUser = (User) req.getSession().getAttribute("auth");
        try {
            AUserRolePermission updated = gson.fromJson(readBody(req), AUserRolePermission.class);
            List<String> oldRoles = userService.getRoleNameByUserName(updated.getUserName());

            if (updated.getUserName() == null || updated.getUserName().isBlank()) {
                logService.logCustom(ctx.username, "ERROR", "Thiếu userName trong PUT roleUser", ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu userName");
                return;
            }

            // Log request data
            logService.logCustom(ctx.username, "INFO",
                    "Yêu cầu cập nhật quyền cho user=" + updated.getUserName() + ", OldRoles=" + oldRoles + ", NewRoles=" + updated.getRoles(),
                    ctx.ip, ctx.roles);

            boolean success = userService.updateUserRolesWithPermissionCheck(
                    currentUser, updated.getUserName(), updated.getRoles());
            if (success) {
                logService.logUpdateEntity(ctx.username, ResourceNames.ROLE_USER, updated.getUserName(), ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật thành công");
            } else {
                logService.logCustom(ctx.username, "WARN",
                        "Không có quyền thay đổi roles cho user=" + updated.getUserName() + " hoặc user không tồn tại",
                        ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền thay đổi vai trò, hoặc user không tồn tại");
            }
        } catch (JsonSyntaxException e) {
            logService.logCustom(ctx.username, "ERROR", "JSON không hợp lệ: " + e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu gửi lên không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", "Lỗi server PUT roleUser: " + e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật quyền user");
        }
    }
}
