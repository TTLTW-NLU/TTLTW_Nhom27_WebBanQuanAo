package vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery;

import org.json.JSONArray;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.ShippingFeeRequest;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.delivery.model.ShippingFeeResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GHNApiUtil {
    private static final String GHN_API_URL_CALCULATE_FEE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
    private static final String GHN_API_URL_SERVICE = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/available-services";
    private static final String TOKEN = "750c9e8a-3d3f-11f0-9b81-222185cb68c8";
    private static final int SHOP_ID = 196732;

    public static ShippingFeeResponse calculateShippingFee(ShippingFeeRequest req) throws IOException {
        URL url = new URL(GHN_API_URL_CALCULATE_FEE);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Get service
        int serviceId = getService(req.getFromDistrictId(), req.getToDistrictId());
        req.setServiceId(serviceId);

        // Create payload JSON
        JSONObject payload = new JSONObject();
        payload.put("from_district_id", req.getFromDistrictId());
        payload.put("from_ward_code", req.getFromWardCode());
        payload.put("to_district_id", req.getToDistrictId());
        payload.put("to_ward_code", req.getToWardCode());
        payload.put("weight", req.getWeight());
        payload.put("length", req.getLength());
        payload.put("width", req.getWidth());
        payload.put("height", req.getHeight());
        payload.put("service_id", req.getServiceId());
        payload.put("insurance_value", req.getInsuranceValue());
        payload.put("coupon", req.getCoupon());
        payload.put("shop_id", SHOP_ID);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.toString().getBytes("UTF-8"));
        }

        // Read response from GHN
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JSONObject data = new JSONObject(response.toString()).getJSONObject("data");

        ShippingFeeResponse res = new ShippingFeeResponse();
        res.setTotal(data.getInt("total"));
        res.setServiceFee(data.getInt("service_fee"));
        res.setInsuranceFee(data.optInt("insurance_fee", 0));
        res.setPickStationFee(data.optInt("pick_station_fee", 0));
        res.setCouponValue(data.optInt("coupon_value", 0));
        res.setR2sFee(data.optInt("r2s_fee", 0));
        res.setDocumentReturn(data.optInt("document_return", 0));
        res.setDoubleCheck(data.optInt("double_check", 0));
        res.setCodFee(data.optInt("cod_fee", 0));
        res.setPickRemoteAreasFee(data.optInt("pick_remote_areas_fee", 0));
        res.setDeliverRemoteAreasFee(data.optInt("deliver_remote_areas_fee", 0));
        res.setCodFailedFee(data.optInt("cod_failed_fee", 0));

        return res;
    }

    public static Integer getService(int fromDistrictId, int toDistrictId) {
        Integer serviceId = null;

        try {
            // Create connection to url
            URL url = new URL(GHN_API_URL_SERVICE);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Token", TOKEN);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON for body of request
            JSONObject payload = new JSONObject();
            payload.put("from_district", fromDistrictId);
            payload.put("to_district", toDistrictId);
            payload.put("shop_id", SHOP_ID);

            // Write JSON into body
            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.toString().getBytes("UTF-8"));
            }

            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            // Get data from response
            JSONArray dataArr = new JSONObject(response.toString()).getJSONArray("data");
            for (int i = 0; i <dataArr.length(); i++) {
                JSONObject service = dataArr.getJSONObject(i);
                if (service.getInt("service_type_id") == 2) {
                    serviceId = service.getInt("service_id");
                    break;
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return serviceId;
    }

    public static void main(String[] args) throws IOException {
        ShippingFeeRequest req = new ShippingFeeRequest();
        req.setFromDistrictId(3695);
        req.setFromWardCode("90752");
        req.setToDistrictId(1533);
        req.setToWardCode("22013");
        req.setWeight(1000);
        req.setLength(15);
        req.setWidth(15);
        req.setHeight(15);
        req.setServiceId(getService(3695, 1533));
        req.setInsuranceValue(500000);
        req.setCoupon(null);

        System.out.println(GHNApiUtil.calculateShippingFee(req));
    }
}
