package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartProductDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.model.CartProduct;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckoutDAO {

    JDBIConnector conn;
    String query, query2;

    public CheckoutDAO() {
        conn = new JDBIConnector();
    }

    // create new cart
    public Integer createCart(int userId) {
        query = "SELECT id FROM cart WHERE userId = ?";
        query2 = "INSERT INTO cart (userId) VALUES (?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt1 = h.getConnection().prepareStatement(query)) {
                stmt1.setInt(1, userId);
                ResultSet rs = stmt1.executeQuery();

                // If the cart already exists, return the orderId
                if (rs.next()) {
                    return rs.getInt(1);
                }

                // Create new cart
                try (PreparedStatement stmt2 = h.getConnection().prepareStatement(query2, Statement.RETURN_GENERATED_KEYS)) {
                    stmt2.setInt(1, userId);
                    int rowAffected = stmt2.executeUpdate();

                    if (rowAffected > 0) {
                        try (ResultSet generatedKeys = stmt2.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                return generatedKeys.getInt(1);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    // add to cartDetail
    public boolean add(int cartId, int couponId, int quantity, double unitPrice, int productDetailId) {
        query = "INSERT INTO cartdetail (cartId, couponId, quantity, unitPrice, productDetailsId) VALUES (?, ?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, cartId);
                stmt.setInt(2, couponId);
                stmt.setInt(3, quantity);
                stmt.setDouble(4, unitPrice);
                stmt.setInt(5, productDetailId);

                int rowAffected = stmt.executeUpdate();
                return rowAffected > 0;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public Map<Integer, CartProduct> getCartProducts() {
        Map<Integer, CartProduct> cps = new HashMap<>();
        query = "SELECT " +
                "    p.id AS id," +
                "    p.productName AS name," +
                "    p.unitPrice AS unitPrice," +
                "    pd.image AS img," +
                "    cd.quantity AS quantity " +
                "    cd.size AS size," +
                "    cd.color AS color " +
                "FROM " +
                "    products p " +
                "JOIN " +
                "    product_details pd ON p.id = pd.productId " +
                "JOIN " +
                "    cartdetail cd ON p.id = cd.productId " +
                "JOIN " +
                "    cart c ON cd.cartId = c.id " +
                "WHERE " +
                "    c.id = ?";


        return conn.get().withHandle(handle -> {
            try (PreparedStatement ps = handle.getConnection().prepareStatement(query)) {
                ps.setInt(1, 1);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CartProduct cp = new CartProduct();
                        cp.setId(rs.getInt("id"));
                        cp.setName(rs.getString("name"));
                        cp.setUnitPrice(rs.getDouble("unitPrice"));
                        cp.setQuantity(rs.getInt("quantity"));

                        String size = rs.getString("size");
                        String image = rs.getString("image");
                        String color = rs.getString("color");

                        if (!cp.getSizes().contains(size))
                            cp.getSizes().add(size);
                        if (!cp.getImages().contains(image))
                            cp.getImages().add(image);
                        if (!cp.getColors().contains(color))
                            cp.getColors().add(color);

                        cps.put(cp.getId(), cp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return cps;
        });

    }

    // Get order by user ID
    public List<CartProductDetail> getCartByUserId(int userId) {
        List<CartProductDetail> re = new ArrayList<>();
        query = "SELECT" +
                " cd.productDetailsId," +
                " cd.quantity," +
                " cd.unitPrice" +
                " FROM cart c" +
                " JOIN cartdetail cd ON c.id = cd.cartId" +
                " WHERE c.userId = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, userId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int productDetailId = rs.getInt("productDetailsId");
                        int quantity = rs.getInt("quantity");
                        double unitPrice = rs.getDouble("unitPrice");
                        CartProductDetail c = new CartProductDetail(productDetailId, quantity, unitPrice);
                        re.add(c);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return re;
        });
    }

    // create new order
    public Integer createNewOrder(int userId, int paymentId, int couponId, double totalPrice) {
        query = "INSERT INTO orders (userId, paymentId, couponId, orderDate, totalPrice, status) VALUES (?, ?, ?, NOW(), ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, paymentId);
                stmt.setInt(3, couponId);
                stmt.setDouble(4, totalPrice);
                stmt.setInt(5, 0);

                int rowAffected = stmt.executeUpdate();
                if (rowAffected > 0) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    // move all products from cart to orderItem
    public boolean moveToOrder(int orderId, int quantity, double unitPrice, float discount, int productDetailId) {
        query = "INSERT INTO `orderitem` (`orderId`, `productId`, `quantity`, `unitPrice`, `discount`, `productDetailId`) VALUES (?, ?, ?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, getProductIdByProductDetailId(productDetailId));
                stmt.setInt(3, quantity);
                stmt.setDouble(4, unitPrice);
                stmt.setFloat(5, discount);
                stmt.setInt(6, productDetailId);

                int rowAffected = stmt.executeUpdate();

                if (rowAffected > 0) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    // get productId by productDetailId
    public Integer getProductIdByProductDetailId(int productDetailId) {
        query = "SELECT productId FROM product_details WHERE id = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, productDetailId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getInt("productId");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    // Remove all products in cart of a user by userId
    public boolean removeAllProductsByUserId(int userId) {
        query = "DELETE FROM cartdetail WHERE cartId IN (SELECT id FROM cart WHERE userId = ?)";
        query2 = "DELETE FROM cart WHERE userId = ?";

        return conn.get().withHandle(h -> {
            try {
                Connection connection = h.getConnection();
                connection.setAutoCommit(false);

                try (PreparedStatement stmt1 = connection.prepareStatement(query);
                     PreparedStatement stmt2 = connection.prepareStatement(query2)) {

                    // delete orderItem
                    stmt1.setInt(1, userId);
                    stmt1.executeUpdate();

                    // delete orders
                    stmt2.setInt(1, userId);
                    int rowsDeleted = stmt2.executeUpdate();

                    connection.commit();
                    return rowsDeleted > 0;
                }
            } catch (Exception e) {
                try {
                    h.getConnection().rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
                e.printStackTrace();
                return false;
            } finally {
                try {
                    h.getConnection().setAutoCommit(true);
                } catch (Exception autoCommitEx) {
                    autoCommitEx.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        CheckoutDAO dao = new CheckoutDAO();
        System.out.println(dao.moveToOrder(13, 1, 20000, 0.0f, 1));
    }
}
