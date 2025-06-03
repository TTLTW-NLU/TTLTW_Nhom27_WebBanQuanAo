package vn.edu.hcmuaf.fit.webbanquanao.webpage.order.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CartService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.dao.OrderDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.Order;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.OrderItem;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.order.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderDAO orderDAO;
    private final CartService cartService;

    public OrderService() {
        this.orderDAO = new OrderDAO();
        this.cartService = new CartService();
    }

    // Add Order
    public int addOrder(int userId, double totalPrice) {
        // create order
        int orderId = orderDAO.createOrder(userId, 2, 1, BigDecimal.valueOf(totalPrice), OrderStatus.PENDING);

        // add order item
        cartService.getCartItems(userId).forEach(cd -> orderDAO.addOrderItem(orderId, cd.getProductId(), cd.getQuantity(), BigDecimal.valueOf(cd.getUnitPrice()), 0.0f, cd.getProductDetailsId()));

        return orderId;
    }

    // Get order by orderId
    public Optional<Order> getOrderById(int orderId) {
        return orderDAO.getOrderById(orderId);
    }

    // Get orders by userId
    public List<Order> getOrdersByUserId(int userId) {
        return orderDAO.getOrdersByUserId(userId);
    }

    // Get OrderItem by orderId
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        return orderDAO.getOrderItemsByOrderId(orderId);
    }

    // Cancel Order
    public void cancelOrder(int orderId, String reason) {
        orderDAO.cancelOrder(orderId, reason);
    }

    // Update Order's status
    public void updateOrderStatus(int orderId, int status, int userId) {
        orderDAO.updateOrderStatus(orderId, status);

        // remove this product from cart
        cartService.getCartItems(userId).forEach(cd -> cartService.removeFromCart(cd.getId()));
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
//        System.out.println(orderService.addOrder(2, 274550.0));
    }
}
