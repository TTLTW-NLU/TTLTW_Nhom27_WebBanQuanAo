package vn.edu.hcmuaf.fit.webbanquanao.admin.Mapper;

import java.util.Map;
import java.util.regex.Pattern;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

public class ResourceMapper {
    private static final Map<Pattern, String> URL_TO_RESOURCE = Map.ofEntries(
            // === Admin View ===
            Map.entry(Pattern.compile("^/admin.jsp$"), ResourceNames.ADMIN_PANEL),

            // === Admin API (split for manager control) ===
            Map.entry(Pattern.compile("^/admin/api/orders/.*$|^/admin/api/order-details/*$"), ResourceNames.ADMIN_API_ORDER_MANAGE),
            Map.entry(Pattern.compile("^/admin/api/products/.*$|^/admin/api/product-details/*$"), ResourceNames.ADMIN_API_PRODUCT_MANAGE),
            Map.entry(Pattern.compile("^/admin/api/users/.*$"), ResourceNames.ADMIN_API_USER_MANAGE),
            Map.entry(Pattern.compile("^/admin/api/roleUser/*$"), ResourceNames.ADMIN_API_USER_MANAGE),

            // === Cart ===
            Map.entry(Pattern.compile("^/Cart/.*$|^/add-cart$|^/del-cart$|^/updateCart$|^/ShowCart$|^/Checkout$"), ResourceNames.CART_MANAGE),

            // === Orders ===
            Map.entry(Pattern.compile("^/order/.*$"), ResourceNames.ORDER_MANAGE),

            // === Payments ===
            Map.entry(Pattern.compile("^/payment/.*$|^/payments$"), ResourceNames.PAYMENT_PROCESS),

            // === Products ===
            Map.entry(Pattern.compile("^/product/.*$|^/productDetail$|^/productFilter$|^/productPagination$|^/productSearch$"), ResourceNames.PRODUCT_ACCESS),

            // === Users ===
            Map.entry(Pattern.compile("^/user/.*$|^/user.jsp$|^/verify.jsp"), ResourceNames.USER_ACCOUNT)
    );

    public static String getResource(String path) {
        String resource = URL_TO_RESOURCE.entrySet().stream()
                .filter(entry -> entry.getKey().matcher(path).matches())
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("default");

        return resource;
    }

}