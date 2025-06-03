package vn.edu.hcmuaf.fit.webbanquanao.user.auth.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "login-facebook", value = "/login-facebook")
public class FacebookLoginServlet extends HttpServlet {
    private static final String CLIENT_ID = System.getenv("FACEBOOK_CLIENT_ID");
    private static final String REDIRECT_URI = System.getenv("FACEBOOK_REDIRECT_URI");

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fbAuthUrl = "https://www.facebook.com/v18.0/dialog/oauth?"
                + "client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&scope=email,public_profile"
                + "&response_type=code";

        response.sendRedirect(fbAuthUrl);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
