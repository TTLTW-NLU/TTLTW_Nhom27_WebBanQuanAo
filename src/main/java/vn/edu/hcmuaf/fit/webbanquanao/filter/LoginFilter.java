//package vn.edu.hcmuaf.fit.webbanquanao.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
//import vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper.ResourceMapper;
//
//import java.io.IOException;
//import java.util.Map;
//
//@WebFilter("/*")
//public class LoginFilter implements Filter {
//    private static final String[] PUBLIC_PATHS = {"/login", "/login.jsp", "/login-facebook", "/google-login", "/register", "/register.jsp", "/forgotPassword", "/forgot-password.jsp", "/ResetPassword", "/reset-password.jsp", "/verify", "/verify.jsp", "/verifyOTP", "/resend-otp", "/css/", "/js/", "/images/", "/assets/", "/homePage", "/productDetail", "/productSearch", "/productFilter", "/productPagination"};
//
//    private boolean isPublic(String path) {
//        for (String publicPath : PUBLIC_PATHS) {
//            if (path.contains(publicPath)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpSession session = httpRequest.getSession(false); // không tạo session mới nếu chưa có
//
//        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//        String resource = ResourceMapper.getResource(path);
//
//        boolean isValidResource = !"default".equals(resource);
//
//        // Chỉ log nếu resource hợp lệ
//        if (isValidResource) {
//            System.out.println("[LoginFilter] ✅ Authorized → Path: " + path + ", Resource: " + resource);
//        }
//
//        // Nếu là tài nguyên công khai → cho phép
//        if (isPublic(path)) {
////            System.out.println("[LoginFilter] Public path, allow: " + path);
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Nếu chưa đăng nhập hoặc không có session "auth"
//        if (session == null || session.getAttribute("auth") == null) {
//            System.out.println("[LoginFilter] Khong co session or chua dang nhap, redirect -> /login");
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
//            return;
//        }
//
//        // Đã đăng nhập
//        User currentUser = (User) session.getAttribute("auth");
//
//        // Kiểm tra trạng thái tài khoản
//        if (currentUser.getStatus() == null || currentUser.getStatus() == 0) {
//            System.out.println("[LoginFilter] Tai khoan chua kich hoat, redirect về /activate-account");
//            httpResponse.sendRedirect(httpRequest.getContextPath() + "/activate-account.jsp");
//            return;
//        }
//
//        // Log thông tin user duy nhất 1 lần
//        if (session.getAttribute("hasLoggedUserInfo") == null) {
//            System.out.println("[LoginFilter] User: " + currentUser.getUserName());
//            System.out.println("[LoginFilter] Roles: " + currentUser.getRoles());
//            System.out.println("[LoginFilter] Permissions: " + currentUser.getPermissions());
//            session.setAttribute("hasLoggedUserInfo", true);
//        }
//
//        Integer permission = currentUser.getPermissions().get(resource);
//        if (!"default".equals(resource)) {
//            if (permission == null || permission < 1) { // Yêu cầu quyền >= 1 (READ)
//                System.out.println("[LoginFilter] Khong co quyen truy cap vao resource: " + resource + " → redirect /403.jsp");
//                httpResponse.sendRedirect(httpRequest.getContextPath() + "/403.jsp");
//                return;
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}
