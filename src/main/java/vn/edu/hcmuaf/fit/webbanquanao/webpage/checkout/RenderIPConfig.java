package vn.edu.hcmuaf.fit.webbanquanao.webpage.checkout;

import java.util.List;

public class RenderIPConfig {
    private static final List<String> RENDER_IPS = List.of(
            "13.228.225.19",
            "18.142.128.26",
            "54.254.162.138"
    );

    public static boolean isRenderIP(String ip) {
        return RENDER_IPS.contains(ip);
    }

    public static String getPrimaryIP() {
        return RENDER_IPS.get(0);
    }
}
