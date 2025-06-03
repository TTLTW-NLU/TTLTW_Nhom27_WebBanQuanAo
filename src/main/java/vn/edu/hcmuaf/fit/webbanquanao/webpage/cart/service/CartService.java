package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao.CartDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.util.List;

public class CartService {
    private CartDAO cartDAO;
    private ProductDAO productDAO;

    public CartService() {
        this.cartDAO = new CartDAO();
    }

    // Get all products from cart
    public List<CartDetail> getCartItems(int userId) {
        return cartDAO.getAllCartItems(userId);
    }


    // Thêm sản phẩm vào giỏ hàng
    public boolean addToCart(int userId, int couponId, int quantity, double unitPrice, int productDetailId) {
        int cartDetailId = cartDAO.hasProduct(userId, productDetailId);
        if(cartDetailId > 0) {
            return updateCart(userId, productDetailId, cartDAO.getQuantityOfProduct(userId, productDetailId) + quantity);
        } else {
            return cartDAO.addToCart(userId, couponId, quantity, unitPrice, productDetailId);
        }
    }

    // Cập nhật số lượng sản phẩm
    public boolean updateCart(int userId, int productDetailId, int quantity) {
        if(quantity > 0) {
            return cartDAO.updateCart(userId, productDetailId, quantity);
        }
        return false;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public boolean removeFromCart(int cartDetailId) {
        return cartDAO.removeItem(cartDetailId);
    }

    // Tính tổng tiền
    public double getCartTotal(int userId) {
        double total = 0;
        for (CartDetail cd : cartDAO.getAllCartItems(userId)) {
            total += cd.getUnitPrice() * cd.getQuantity();
        }
        return total;
    }

    // Get product detail
    public ProductDetail getProductDetail(int productId, String color, String size) {
        return cartDAO.getProductDetail(productId, color, size);
    }

    public static void main(String[] args) {
        CartService service = new CartService();
//        System.out.println(service.getCartTotal(2));
//        System.out.println(service.getCart(2));
    }
}
