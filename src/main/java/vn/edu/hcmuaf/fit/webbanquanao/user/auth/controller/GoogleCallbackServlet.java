package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.webbanquanao.user.auth.service.GoogleUserInfo;
import vn.edu.hcmuaf.fit.webbanquanao.user.dao.UserDao;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@WebServlet("/google-callback")
public class GoogleCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");
    private static final String REDIRECT_URI = System.getenv("GOOGLE_REDIRECT_URI");

    private static final AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            CLIENT_ID,
            CLIENT_SECRET,
            Arrays.asList(
                    "https://www.googleapis.com/auth/userinfo.email",
                    "https://www.googleapis.com/auth/userinfo.profile"
            )
    ).build();

    private UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();

        // Validate CSRF state token
        String expectedState = (String) session.getAttribute("oauthState");
        String receivedState = req.getParameter("state");
        if (expectedState == null || !expectedState.equals(receivedState)) {
            resp.sendRedirect("login.jsp?error=Invalid OAuth state");
            return;
        }

        String code = req.getParameter("code");
        if (code == null || code.isEmpty()) {
            resp.sendRedirect("login.jsp?error=Google authentication failed");
            return;
        }

        // Exchange code for access token
        GoogleTokenResponse tokenResponse;
        try {
            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    CLIENT_ID,
                    CLIENT_SECRET,
                    code,
                    REDIRECT_URI
            ).execute();
        } catch (IOException e) {
            resp.sendRedirect("login.jsp?error=Failed to exchange token");
            return;
        }

        // Get user details from Google
        String accessToken = tokenResponse.getAccessToken();
        GoogleUserInfo userInfo = new GoogleUserInfo();
        String email = userInfo.getEmail(accessToken);
        String fullName = userInfo.getFullName(accessToken);

        if (email == null || fullName == null) {
            resp.sendRedirect("login.jsp?error=Failed to retrieve user info");
            return;
        }

        // Split full name into first and last name
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";

        // Find or create user
        User user = userDao.getUserByEmail(email);
        if (user == null) {
            // Create new user from Google info
            user = new User();
            user.setEmail(email);
            user.setUserName(email.split("@")[0]);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setStatus(1); // Active

            // Set random password for Google users
            String randomPassword = BCrypt.hashpw(UUID.randomUUID().toString(), BCrypt.gensalt());
            user.setPassWord(randomPassword);

            if (!userDao.createUser(user)) {
                resp.sendRedirect("login.jsp?error=Failed to create user");
                return;
            }

            // Get the newly created user with full details
            user = userDao.getUserByEmail(email);
        }

        // Set user object in session
        session.setAttribute("auth", user);

        // Redirect to home page
        resp.sendRedirect("homePage");
    }
}
