package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao.CheckoutDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartProductDetail;

import java.util.List;

public class CheckoutService {

    public CheckoutService() {
    }

    // get totalPrice of cart by userId
    public double getTotalPrice(List<CartProductDetail> cartProductDetails) {
        double result = 0;
        for(CartProductDetail c : cartProductDetails) {
            result += c.getQuantity() * c.getUnitPrice();
        }
        return result;
    }

    // checkout
    public boolean checkout(int userId, int paymentId, int couponId) {
        CheckoutDAO dao = new CheckoutDAO();

        // get products of cart
        List<CartProductDetail> products = dao.getCartByUserId(userId);
        double totalPrice = getTotalPrice(products);

        // create new order and get new orderId || get new orderId already exists
        int orderId = dao.createNewOrder(userId, paymentId, couponId, totalPrice);

        // move products from cart to orderItem
        boolean moved = false;
        for (CartProductDetail p : products) {
            moved = dao.moveToOrder(orderId, p.getQuantity(), p.getUnitPrice(), 0.0f, p.getProductDetailId());
        }

        // remove products of cart
        if (moved) {
            return dao.removeAllProductsByUserId(userId);
        }

        return false;
    }

    public static void main(String[] args) {
        CheckoutService service = new CheckoutService();
        System.out.println(service.checkout(2,1,1));
    }
}
