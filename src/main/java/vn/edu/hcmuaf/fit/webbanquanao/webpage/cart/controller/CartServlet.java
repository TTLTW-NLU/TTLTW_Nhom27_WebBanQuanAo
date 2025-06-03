package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CartService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Cart", value = "/cart")
public class CartServlet extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        this.cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hiển thị giỏ hàng
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");
        int userId = user.getId();

        // All products of cart
        List<CartDetail> cart = cartService.getCartItems(userId);
        request.setAttribute("cart", cart);

        // Total price
        double totalPrice = cartService.getCartTotal(userId);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) {
                throw new IllegalArgumentException("Action không được để trống");
            }

            // Get userId
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("auth");
            int userId = user.getId();

            switch (action.toLowerCase()) {
                case "add":
                    handleAdd(request, response,
                            getIntParameter(request, "productId"),
                            getIntParameter(request, "quantity"),
                            userId);
                    break;

                case "update":
                    handleUpdate(request, response,
                            getIntParameter(request, "productDetailId"),
                            getIntParameter(request, "quantity"),
                            userId);
                    break;

                case "remove":
                    // Xử lý đặc biệt cho cartDetailId (có thể null)
                    String cartDetailIdStr = request.getParameter("cartDetailId");
                    int cartDetailId = cartDetailIdStr != null && !cartDetailIdStr.isEmpty() ? Integer.parseInt(cartDetailIdStr) : -1;
                    handleRemove(request, response, cartDetailId);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action không hợp lệ");
            }

        } catch (Exception e) {
            request.getSession().setAttribute("message", "lỗi chức năng giỏ hàng");
        }
    }

    // Helper method chỉ parse khi có giá trị
    private int getIntParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(paramName + " không được để trống");
        }
        return Integer.parseInt(value);
    }

    // Thêm sản phẩm
    private void handleAdd(HttpServletRequest request, HttpServletResponse response, int productId, int quantity, int userId) throws IOException {
        try {
            // Lấy các tham số
            String color = request.getParameter("color");
            String size = request.getParameter("size");
            String unitPriceStr = request.getParameter("unitPrice");
            String couponIdStr = request.getParameter("couponId");

            // Kiểm tra các tham số bắt buộc
            if (color == null || size == null || unitPriceStr == null || couponIdStr == null ||
                color.isEmpty() || size.isEmpty() || unitPriceStr.isEmpty() || couponIdStr.isEmpty()) {
                throw new IllegalArgumentException("Thiếu thông tin sản phẩm");
            }

            // Parse các giá trị
            double unitPrice = Double.parseDouble(unitPriceStr);
            int couponId = Integer.parseInt(couponIdStr);

            // Lấy chi tiết sản phẩm và thêm vào giỏ hàng
            ProductDetail pd = cartService.getProductDetail(productId, color, size);
            boolean result = cartService.addToCart(userId, couponId, quantity, unitPrice, pd.getId());
            request.getSession().setAttribute("message", result ? "Thêm vào giỏ hàng thành công" : "Thêm vào giỏ hàng thất bại");
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Không lấy được dữ liệu sản phẩm");
        } finally {
            response.sendRedirect(request.getContextPath() + "/productDetail?pid=" + productId);
        }
    }

    // Cập nhật sản phẩm
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response, int productDetailId, int quantity, int userId) throws IOException, ServletException {
        cartService.updateCart(userId, productDetailId, quantity);
        doGet(request, response);// Tải lại giỏ hàng
    }

    // Xoá sản phẩm
    private void handleRemove(HttpServletRequest request, HttpServletResponse response, int cartDetailId) throws IOException, ServletException {
        cartService.removeFromCart(cartDetailId);
        doGet(request, response);// Tải lại giỏ hàng
    }
}