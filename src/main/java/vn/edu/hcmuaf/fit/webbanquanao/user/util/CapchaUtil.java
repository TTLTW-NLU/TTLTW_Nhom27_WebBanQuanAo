package vn.edu.hcmuaf.fit.webbanquanao.user.util;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

public class CapchaUtil {
    private static final String SECRET_KEY = "6LeYvVQrAAAAADNsFR5RxnD5_3tQrruzHwtdsStO";

    public static boolean verifyRecaptcha(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            return false;
        }

        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "secret=" + URLEncoder.encode(SECRET_KEY, "UTF-8") +
                "&response=" + URLEncoder.encode(gRecaptchaResponse, "UTF-8");

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {
            out.write(params);
            out.flush();
        }

        StringBuilder responseStr = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseStr.append(inputLine);
            }
        }

        JSONObject json = new JSONObject(responseStr.toString());
        return json.getBoolean("success");
    }
}

