package vn.edu.hcmuaf.fit.webbanquanao.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper.ResourceMapper;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/login", "/login.jsp", "/login-facebook", "/google-login", "/google-callback",
            "/register", "/register.jsp", "/forgotPassword", "/forgot-password.jsp",
            "/ResetPassword", "/reset-password.jsp", "/verify", "/verify.jsp",
            "/verifyOTP", "/resend-otp", "/homePage", "/productDetail",
            "/productSearch", "/productFilter", "/productPagination",
            "/facebook-callback"
    );

    private static final Set<String> STATIC_EXTENSIONS = Set.of(
            "css", "js", "jpg", "jpeg", "png", "gif", "ico",
            "woff", "woff2", "ttf", "svg", "map", "webp"
    );

    private static final Set<String> ADMIN_PATHS = Set.of("/admin.jsp");

    private static final Map<String, Integer> METHOD_PERM = Map.of(
            "GET",    4,   // chỉ cần READ
            "POST",   6,   // cần cả READ + WRITE
            "PUT",    6,   // cần cả READ + WRITE
            "DELETE", 7    // cần READ + WRITE + DELETE
    );

    private UserLogsService logService;

    @Override
    public void init(FilterConfig config) {
        logService = UserLogsService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (isPublicOrStatic(path)) {
            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        User user = session != null ? (User) session.getAttribute("auth") : null;
        String ip = request.getRemoteAddr();

        if (user == null) {
            redirectToLogin(request, response);
//            logService.logAnonymousAccessAttempt(path, ip);
            return;
        }

        String resource = ResourceMapper.getResource(path);
        Integer userPerm = Optional.ofNullable(user.getPermissions()).orElse(Map.of())
                .getOrDefault(resource, 0);

        // Log user info once
        if (session.getAttribute("hasLoggedUserInfo") == null) {
            logService.logLoginSuccess(user.getUserName(), ip, user.getRoles());
            session.setAttribute("hasLoggedUserInfo", true);
        }

        // Admin page check
        if (ADMIN_PATHS.contains(path) && !hasAnyRole(user, Set.of("ADMIN", "MANAGER"))) {
            logService.logUnauthorizedAccess(user.getUserName(), path, ResourceMapper.getResource(path), userPerm, ip, user.getRoles());
            sendErrorMessageAndRedirect(request, response, "Bạn không có quyền truy cập trang này");
            return;
        }

        // Permission check
        Integer required = METHOD_PERM.get(request.getMethod());
        if (required == null) {
            logService.logUnauthorizedAccess(user.getUserName(), path, ResourceMapper.getResource(path), 0, ip, user.getRoles());
            forwardError(request, response, "HTTP method not allowed");
            return;
        }

        if (!"default".equals(resource)) {
            if (userPerm == null || userPerm == 0 || (userPerm & required) != required) {
                logService.logForbiddenAction(user.getUserName(), path, ResourceMapper.getResource(path), userPerm, ip, user.getRoles());
                forwardError(request, response, "Bạn không đủ quyền để thực hiện chức năng này");
                return;
            }
        }

        // Page-view log
        if (!isAjax(request)
                && !path.equals(session.getAttribute("lastLoggedPath"))
                && !path.startsWith("/admin/api/")) {
            logService.logAccessGranted(user.getUserName(), path, ResourceMapper.getResource(path), required, ip, user.getRoles());
            session.setAttribute("lastLoggedPath", path);
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {}

    private boolean isPublicOrStatic(String path) {
        if (PUBLIC_PATHS.stream().anyMatch(p -> path.equals(p) || path.startsWith(p + '/'))) return true;
        int idx = path.lastIndexOf('.');
        return idx >= 0 && STATIC_EXTENSIONS.contains(path.substring(idx + 1).toLowerCase());
    }

    private void redirectToLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String uri = URLEncoder.encode(req.getRequestURI(), StandardCharsets.UTF_8);
        res.sendRedirect(req.getContextPath() + "/login.jsp?redirect=" + uri);
    }

    protected void forwardError(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        res.setContentType("application/json;charset=UTF-8");
        String json = "{\"error\":true,\"message\":\"" + escapeJson(msg) + "\"}";
        res.getWriter().write(json);
        res.getWriter().flush();
    }

    private String escapeJson(String msg) {
        return msg.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    protected void sendErrorMessageAndRedirect(HttpServletRequest req, HttpServletResponse res, String msg) throws IOException {
        req.getSession().setAttribute("errorMessage", msg);
        res.sendRedirect(req.getContextPath() + "/homePage");
    }

    private boolean isAjax(HttpServletRequest req) {
        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }

    private boolean hasAnyRole(User user, Set<String> roles) {
        return Optional.ofNullable(user.getRoles()).orElse(List.of())
                .stream().anyMatch(roles::contains);
    }
}