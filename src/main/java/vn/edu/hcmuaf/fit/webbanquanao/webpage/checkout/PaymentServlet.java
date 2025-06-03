package vn.edu.hcmuaf.fit.webbanquanao.webpage.checkout;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CartService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.AddressService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.GHNApiUtil;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.Address;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.ShippingFeeRequest;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.ShippingFeeResponse;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.service.OrderService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "PaymentServlet", value = "/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;
    private AddressService addressService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        cartService = new CartService();
        addressService = new AddressService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get userId
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        int userId = user.getId();

        // Total price
//        double amountDouble = calculateTotalAmountWithShipping(userId, request);
        double amountDouble = cartService.getCartTotal(userId);

        // Insert order
        int orderId = orderService.addOrder(userId, amountDouble);

        // Insert order failed
        if (orderId < 1) {
            response.sendRedirect("cart");
            return;
        }

        String bankCode = request.getParameter("bankCode");
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        long amount = (long) (amountDouble * 100);
        String vnp_TxnRef = orderId+"";
        String vnp_IpAddr = Config.getIpAddress(request);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        response.getWriter().write(gson.toJson(job));
        response.sendRedirect(paymentUrl);
    }

    public double calculateTotalAmountWithShipping(int userId, HttpServletRequest request) throws IOException {
        int toDistrictId = Integer.parseInt(request.getParameter("district"));
        String toWardCode = request.getParameter("ward");
        int weight = 1000;
        int length = 15;
        int width = 15;
        int height = 15;
        int insuranceValue = 500000;

        // Get address of admin
        Address adminAddress = addressService.getAddressByUserId(1);
        int fromDistrictId = adminAddress.getDistrictId();
        String fromWardCode = adminAddress.getWardCode();

        // Init shippingFeeRequest object
        ShippingFeeRequest feeReq = new ShippingFeeRequest(fromDistrictId, fromWardCode, toDistrictId, toWardCode, weight, length, width, height, -1, insuranceValue, null);

        // Products price
        double productTotal = cartService.getCartTotal(userId);

        // Shipping fee
        ShippingFeeResponse feeRes = GHNApiUtil.calculateShippingFee(feeReq);
        double shippingFee = feeRes.getTotal();

        return productTotal + shippingFee;
    }
}