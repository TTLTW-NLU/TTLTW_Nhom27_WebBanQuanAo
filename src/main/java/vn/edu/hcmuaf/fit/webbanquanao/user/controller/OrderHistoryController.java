package vn.edu.hcmuaf.fit.webbanquanao.user.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api.LocalDateTimeAdapter;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.user.service.OrderService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "OrderController", value = "/user/orderController")
public class OrderHistoryController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService(); // Khởi tạo OrderService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth"); // Lấy thông tin user từ session

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

        // Kiểm tra xem user có tồn tại không
        if (user == null) {
            out.print(gson.toJson(Map.of("error", "Người dùng chưa đăng nhập")));
            out.flush();
            return;
        }

        // Lấy danh sách đơn hàng theo userName
        String userName = user.getUserName(); // Lấy userName từ User object
        Map<Integer, Order> orders = orderService.getAllOrdersByUserName(userName);
        List<Order> orderList = orders.values().stream().collect(Collectors.toList());

        String json = gson.toJson(orderList);
        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");

        Map<String, Object> response = new HashMap<>();
        Gson gson = new Gson();

        try {
            // Lấy action từ URL (ví dụ: /orderController?action=cancel hoặc action=confirm)
            String action = req.getParameter("action");

            // Đọc dữ liệu JSON từ request
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }

            // Parse JSON
            Map<String, Object> requestData = gson.fromJson(jsonBuffer.toString(), Map.class);

            // Kiểm tra dữ liệu đầu vào
            if (requestData == null || !requestData.containsKey("id") || action == null) {
                throw new IllegalArgumentException("Dữ liệu không hợp lệ");
            }

            // Chuyển đổi id từ Number sang int
            int orderId = ((Number) requestData.get("id")).intValue();

            System.out.println("ID nhận được: " + orderId + ", Hành động: " + action); // Log kiểm tra

            boolean isSuccess = false;

            // Xử lý theo loại hành động (cancel hoặc confirm)
            if ("cancel".equalsIgnoreCase(action)) {
                // Kiểm tra và lấy lý do hủy đơn hàng
                String reason = (String) requestData.get("cancelReason");
                if (reason == null || reason.trim().isEmpty()) {
                    throw new IllegalArgumentException("Lý do hủy không được để trống");
                }

                isSuccess = orderService.cancelOrder(orderId, reason);
                response.put("message", isSuccess ? "Đơn hàng đã được hủy thành công" : "Không thể hủy đơn hàng. Vui lòng thử lại");

            } else if ("confirm".equalsIgnoreCase(action)) {
                isSuccess = orderService.confirmOrder(orderId);
                response.put("message", isSuccess ? "Đơn hàng đã được xác nhận đã nhận hàng" : "Không thể xác nhận đơn hàng. Vui lòng thử lại");

            } else {
                throw new IllegalArgumentException("Hành động không hợp lệ");
            }

            // Gửi phản hồi dựa trên kết quả xử lý
            resp.setStatus(isSuccess ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
        } catch (JsonSyntaxException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.put("message", "Dữ liệu JSON không hợp lệ");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.put("message", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.put("message", "Đã xảy ra lỗi trong quá trình xử lý yêu cầu");
        }

        resp.getWriter().write(gson.toJson(response));
    }


}
