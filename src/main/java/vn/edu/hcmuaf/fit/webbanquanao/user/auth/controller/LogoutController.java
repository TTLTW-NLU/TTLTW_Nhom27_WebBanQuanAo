package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {

    private UserLogsService logService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logService = UserLogsService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Optional: forward to logout page or simply delegate to doPost
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("auth");
            if (user != null) {
                // Ghi log đăng xuất thành công (SLF4J + DB)
                logService.logLogoutSuccess(
                        user.getUserName(),
                        request.getRemoteAddr(),
                        user.getRoles() != null ? user.getRoles() : Collections.emptyList()
                );
            }
            session.invalidate();
        }
        response.sendRedirect("login.jsp");
    }
}
