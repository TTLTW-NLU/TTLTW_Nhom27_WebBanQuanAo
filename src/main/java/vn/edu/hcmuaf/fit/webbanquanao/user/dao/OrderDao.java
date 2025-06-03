package vn.edu.hcmuaf.fit.webbanquanao.user.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class OrderDao {

    public Map<Integer, Order> listOrdersUser;

    public OrderDao() {
        listOrdersUser = getAllOrdersOfUser();
    }

    public Map<Integer, Order> getAllOrdersOfUser() {
        Map<Integer, Order> orders = new LinkedHashMap<>();

        String sql = "SELECT o.id, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status\n" + "from orders o\n" + "INNER JOIN payments p on o.paymentId = p.id\n" + "INNER JOIN coupons c on o.couponId = c.id\n" + "INNER JOIN users u on o.userId = u.id";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserName(rs.getString("userName"));
                    order.setFirstName(rs.getString("firstName"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setCode(rs.getString("code"));
                    order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setStatus(rs.getInt("status"));
                    orders.put(order.getId(), order);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach don hang: " + e.getMessage());
            }
            return orders;
        });
    }

    public Map<Integer, Order> getAllOrdersByUserName(String userName) {
        Map<Integer, Order> orders = new HashMap<>();

        // Lấy danh sách đơn hàng
        String sqlOrders = "SELECT o.id, u.firstName, p.paymentMethod, c.code, o.orderDate, o.totalPrice, o.status " +
                "FROM orders o " +
                "LEFT JOIN payments p ON o.paymentId = p.id " +
                "LEFT JOIN coupons c ON o.couponId = c.id " +
                "INNER JOIN users u ON o.userId = u.id " +
                "WHERE u.userName = ?";

        // Lấy danh sách order item
        String sqlOrderItems = "SELECT oi.id, oi.orderId, p.productName, pd.color, pd.size, " +
                "oi.quantity, oi.unitPrice, oi.discount " +
                "FROM orderitem oi " +
                "LEFT JOIN products p ON oi.productId = p.id " +
                "LEFT JOIN product_details pd ON oi.productDetailId = pd.id " +
                "WHERE oi.orderId = ?";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sqlOrders)) {
                ps.setString(1, userName);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setFirstName(rs.getString("firstName"));
                    order.setPaymentMethod(rs.getString("paymentMethod"));
                    order.setCode(rs.getString("code"));
                    order.setOrderDate(rs.getTimestamp("orderDate").toLocalDateTime());
                    order.setTotalPrice(rs.getDouble("totalPrice"));
                    order.setStatus(rs.getInt("status"));

                    // Lấy danh sách order items cho order này
                    try (PreparedStatement psItems = h.getConnection().prepareStatement(sqlOrderItems)) {
                        psItems.setInt(1, order.getId());
                        ResultSet rsItems = psItems.executeQuery();

                        List<OrderDetail> orderItems = new ArrayList<>();
                        while (rsItems.next()) {
                            OrderDetail item = new OrderDetail();
                            item.setId(rsItems.getInt("id"));
                            item.setOrderId(rsItems.getInt("orderId"));
                            item.setProductName(rsItems.getString("productName"));
                            item.setColor(rsItems.getString("color"));
                            item.setSize(rsItems.getString("size"));
                            item.setQuantity(rsItems.getInt("quantity"));
                            item.setUnitPrice(rsItems.getDouble("unitPrice"));
                            item.setDiscount(rsItems.getDouble("discount"));

                            orderItems.add(item);
                        }
                        order.setOrderDetails(orderItems); // Thêm danh sách OrderItem vào Order
                    }

                    orders.put(order.getId(), order);
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi lấy danh sách đơn hàng: " + e.getMessage());
            }
            return orders;
        });
    }

    // Hủy đơn hàng theo id và ghi lại lý do hủy
    public boolean cancelOrderById(int orderId, String reason) {
        String checkStatusSQL = "SELECT status FROM orders WHERE id = ?";
        String cancelOrderSQL = "UPDATE orders SET status = ?, cancelReason = ? WHERE id = ?";

        return JDBIConnector.get().withHandle(handle -> {
            try {
                // 1. Kiểm tra trạng thái đơn hàng
                try (PreparedStatement ps = handle.getConnection().prepareStatement(checkStatusSQL)) {
                    ps.setInt(1, orderId);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        return false; // Không tìm thấy đơn hàng
                    }
                    int status = rs.getInt("status");
                    if (status == 0 || status == 3 || status == 4) {
                        return false; // Không thể hủy các đơn đã hủy, đã giao, đã nhận
                    }
                }

                // 2. Tiến hành hủy đơn hàng và lưu lý do
                try (PreparedStatement ps = handle.getConnection().prepareStatement(cancelOrderSQL)) {
                    ps.setInt(1, 0);
                    ps.setString(2, reason);  // Ghi lý do hủy
                    ps.setInt(3, orderId);
                    int rowsAffected = ps.executeUpdate();
                    return rowsAffected > 0; // Thành công nếu có bản ghi được cập nhật
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }


    // Xác nhận đơn hàng theo id
    public boolean completedOrderById(int orderId) {
        String checkStatusSQL = "SELECT status FROM orders WHERE id = ?";
        String completeOrderSQL = "UPDATE orders SET status = ? WHERE id = ? AND status = ?";

        return JDBIConnector.get().withHandle(handle -> {
            try {
                // 1. Kiểm tra trạng thái đơn hàng
                try (PreparedStatement ps = handle.getConnection().prepareStatement(checkStatusSQL)) {
                    ps.setInt(1, orderId); // Chỉ cần truyền orderId để kiểm tra trạng thái
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        return false; // Không tìm thấy đơn hàng
                    }
                    int status = rs.getInt("status");
                    if (status != 3) {
                        return false; // Chỉ xác nhận nếu trạng thái là "Đang giao"
                    }
                }

                // 2. Cập nhật trạng thái thành "Đã nhận hàng"
                try (PreparedStatement ps = handle.getConnection().prepareStatement(completeOrderSQL)) {
                    ps.setInt(1, 4);        // Chuyển trạng thái thành "Đã nhận hàng"
                    ps.setInt(2, orderId);  // Điều kiện cập nhật theo id đơn hàng
                    ps.setInt(3, 3);        // Kiểm tra trạng thái hiện tại phải là "Đang giao"

                    int rowsAffected = ps.executeUpdate();
                    return rowsAffected > 0; // Thành công nếu có bản ghi được cập nhật
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false; // Xử lý lỗi
            }
        });
    }


}
