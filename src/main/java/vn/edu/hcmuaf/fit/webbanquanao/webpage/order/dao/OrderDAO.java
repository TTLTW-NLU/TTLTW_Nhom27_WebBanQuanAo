package vn.edu.hcmuaf.fit.webbanquanao.webpage.order.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderDAO {

    // Create new order, return orderId
    public int createOrder(int userId, int paymentId, Integer couponId, BigDecimal totalPrice, int status) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createUpdate("""
                INSERT INTO orders (userId, paymentId, couponId, totalPrice, status)
                VALUES (:userId, :paymentId, :couponId, :totalPrice, :status)
            """)
                        .bind("userId", userId)
                        .bind("paymentId", paymentId)
                        .bind("couponId", couponId)
                        .bind("totalPrice", totalPrice)
                        .bind("status", status)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one()
        );
    }

    // Add to orderItem
    public void addOrderItem(int orderId, int productId, int quantity, BigDecimal unitPrice, float discount, int productDetailId) {
        JDBIConnector.get().useHandle(handle ->
                handle.createUpdate("""
                INSERT INTO orderitem (orderId, productId, quantity, unitPrice, discount, productDetailId)
                VALUES (:orderId, :productId, :quantity, :unitPrice, :discount, :productDetailId)
            """)
                        .bind("orderId", orderId)
                        .bind("productId", productId)
                        .bind("quantity", quantity)
                        .bind("unitPrice", unitPrice)
                        .bind("discount", discount)
                        .bind("productDetailId", productDetailId)
                        .execute()
        );
    }

    // Get all products of user's order
    public List<Order> getOrdersByUserId(int userId) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM orders WHERE userId = :userId ORDER BY orderDate DESC")
                        .bind("userId", userId)
                        .mapToBean(Order.class)
                        .list()
        );
    }

    // Get OderItems in an Order
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM orderitem WHERE orderId = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(OrderItem.class)
                        .list()
        );
    }

    // Update order status
    public void updateOrderStatus(int orderId, int status) {
        JDBIConnector.get().useHandle(handle ->
                handle.createUpdate("UPDATE orders SET status = :status WHERE id = :orderId")
                        .bind("status", status)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    // Add reason for order
    public void cancelOrder(int orderId, String reason) {
        JDBIConnector.get().useHandle(handle ->
                handle.createUpdate("""
                UPDATE orders SET status = :status, cancelReason = :reason WHERE id = :orderId
            """)
                        .bind("status", -1)
                        .bind("reason", reason)
                        .bind("orderId", orderId)
                        .execute()
        );
    }

    // Get order by orderId
    public Optional<Order> getOrderById(int orderId) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM orders WHERE id = :orderId")
                        .bind("orderId", orderId)
                        .mapToBean(Order.class)
                        .findOne()
        );
    }

    public static void main(String[] args) {
        OrderDAO dao = new OrderDAO();
//        System.out.println(dao.createOrder(2, 2, 1, BigDecimal.valueOf(274550.0), 5));
//        dao.addOrderItem(18, 1, 1, BigDecimal.valueOf(274550.0), 0, 1);
//        System.out.println(dao.getOrdersByUserId(2));
//        System.out.println(dao.getOrderItemsByOrderId(18));
//        dao.updateOrderStatus(18, 5);
//        dao.cancelOrder(18, "Langue cokk");
//        System.out.println(dao.getOrderById(18).get());
    }
}
