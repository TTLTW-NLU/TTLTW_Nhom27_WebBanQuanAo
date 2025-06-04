package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.model.FacebookUser;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "facebook-callback", value = "/facebook-callback")
public class FacebookCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = System.getenv("FACEBOOK_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("FACEBOOK_CLIENT_SECRET");
    private static final String REDIRECT_URI = System.getenv("FACEBOOK_REDIRECT_URI");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login.jsp?error=Facebook login failed");
            return;
        }

        String tokenUrl = "https://graph.facebook.com/v18.0/oauth/access_token?"
                + "client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + code;

        String accessToken = getAccessToken(tokenUrl);
        String userInfo = getUserInfo(accessToken);
        FacebookUser fbUser = parseFacebookUser(userInfo);

        if (fbUser.getEmail() == null || fbUser.getEmail().isEmpty()) {
            response.sendRedirect("login.jsp?error=Email permission is required");
            return;
        }

        UserDao userDAO = new UserDao();
        User user = userDAO.getUserByEmail(fbUser.getEmail());

        if (user == null) {
            String[] nameParts = fbUser.getName().trim().split("\\s+");
            String firstName = nameParts[0];
            String lastName = nameParts.length > 1
                    ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length))
                    : "";

            String username = fbUser.getEmail().split("@")[0];
            String hashedPassword = userDAO.hashPassword(generateRandomPassword());

            user = new User(
                    username,
                    firstName,
                    lastName,
                    fbUser.getEmail(),
                    hashedPassword
            );

            user.setStatus(1);
            user.setCreatedAt(LocalDateTime.now());
            user.setRoles(new ArrayList<>(List.of("USER")));

            if (!userDAO.createUser(user)) {
                response.sendRedirect("login.jsp?error=Failed to create account");
                return;
            }

            user = userDAO.getUserByEmail(fbUser.getEmail());
        } else if (user.getStatus() == 0) {
            response.sendRedirect("login.jsp?error=Account is disabled");
            return;
        }

        session.setAttribute("auth", user);
        session.setAttribute("roles", user.getRoles());
        session.setAttribute("permissions", user.getPermissions());

        System.out.println("\n\nNotify in LoginController:");
        System.out.println("Login success: " + user.getUserName());
        System.out.println("Roles: " + user.getRoles());
        System.out.println("Permissions: " + user.getPermissions());
        System.out.println();

        // Chuyển hướng đến trang trước đó nếu có
        String redirect = (String) session.getAttribute("redirect_after_login");
        if (redirect != null) {
            session.removeAttribute("redirect_after_login");
            response.sendRedirect(redirect);
        } else {
            response.sendRedirect("homePage");
        }
    }

    private String getAccessToken(String tokenUrl) throws IOException {
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        return json.getString("access_token");
    }

    private String getUserInfo(String accessToken) throws IOException {
        String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
        return sendGetRequest(url);
    }

    private String sendGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private FacebookUser parseFacebookUser(String json) {
        JSONObject obj = new JSONObject(json);
        return new FacebookUser(obj.getString("id"), obj.getString("name"), obj.optString("email"));
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Không xử lý POST
    }
}
