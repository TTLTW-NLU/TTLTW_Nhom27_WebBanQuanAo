package vn.edu.hcmuaf.fit.webbanquanao.user.auth.service;

import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.util.Map;

public class GoogleUserInfo {
    private static final String GOOGLE_USERINFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    public static String getEmail(String accessToken) throws IOException {
        return getUserInfo(accessToken, "email");
    }

    public static String getFullName(String accessToken) throws IOException {
        return getUserInfo(accessToken, "name");
    }

    private static String getUserInfo(String accessToken, String key) throws IOException {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory();

        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(GOOGLE_USERINFO_URL));
        request.getHeaders().setAuthorization("Bearer " + accessToken);

        try {
            HttpResponse response = request.execute();
            if (response.getStatusCode() != 200) {
                throw new IOException("Failed to get user info: " + response.getStatusMessage());
            }
            Map<String, Object> userInfo = JSON_FACTORY.fromInputStream(response.getContent(), Map.class);
            return (String) userInfo.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
