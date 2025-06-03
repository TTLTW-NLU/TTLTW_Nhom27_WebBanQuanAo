package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.AuthService;
import vn.edu.hcmuaf.fit.webbanquanao.user.util.CapchaUtil;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("auth") != null) {
            response.sendRedirect(request.getContextPath() + "/homePage");
        } else {
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("passWord");

        // --- Kiểm tra reCAPTCHA trước ---
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        if (!CapchaUtil.verifyRecaptcha(gRecaptchaResponse)) {
            request.setAttribute("error", "Xác minh CAPTCHA thất bại!");
            request.setAttribute("username", userName); // Giữ lại username
            request.getRequestDispatcher("./login.jsp").forward(request, response);
            return;
        }

        // --- Nếu CAPTCHA thành công, tiếp tục check đăng nhập ---
        AuthService service = new AuthService();
        User user = service.checkLogin(userName, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("auth", user);
            session.setAttribute("roles", user.getRoles());
            session.setAttribute("permissions", user.getPermissions());

            System.out.println("\n\nNotify in LoginController:");
            System.out.println("Login success: " + userName);
            System.out.println("Roles: " + user.getRoles());
            System.out.println("Permissions: " + user.getPermissions());
            System.out.println();

            if (user.hasRole("ADMIN")) {
                response.sendRedirect(request.getContextPath() + "/admin.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/homePage");
            }
        } else {
            request.setAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            request.setAttribute("username", userName); // Giữ lại username nếu đăng nhập lỗi
            request.getRequestDispatcher("./login.jsp").forward(request, response);
        }
    }
}
