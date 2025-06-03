package vn.edu.hcmuaf.fit.webbanquanao.user.auth.model;

import java.util.HashMap;
import java.util.Map;

public class OTPStorage {
    private static final Map<String, String> otpMap = new HashMap<>();

    public static void storeOTP(String email, String otp) {
        otpMap.put(email, otp);
    }

    public static String getOTP(String email) {
        return otpMap.get(email);
    }

    public static void removeOTP(String email) {
        otpMap.remove(email);
    }
}
