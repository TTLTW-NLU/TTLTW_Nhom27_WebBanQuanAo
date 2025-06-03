package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao;

import jakarta.annotation.Nullable;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductDAO {
    JDBIConnector conn;
    String query, query2;

    public ProductDAO() {
        conn = new JDBIConnector();
    }

    // Get product by id
    public Product getById(int id) {
        return getAllProducts().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    // Get all products
    public List<Product> getAllProducts() {
        Map<Integer, Product> re = new HashMap<>();
        query = "SELECT" +
                "   id," +
                "   typeId," +
                "   categoryId," +
                "   supplierId," +
                "   productname," +
                "   description," +
                "   releaseDate," +
                "   unitSold," +
                "   unitPrice," +
                "   status," +
                "   id AS parameter " +
                "FROM products p";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        return getProductsByQuery(query, query2, null);
    }

    // Get sale products
    public List<Product> getSaleProducts() {
        Map<Integer, Product> re = new HashMap<>();
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status," +
                "   p.id AS parameter " +
                "FROM products p " +
                "JOIN sales_product sp ON p.id = sp.productId " +
                "JOIN sales s ON s.id = sp.saleId";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        return getProductsByQuery(query, query2, null);
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status," +
                "   oi.productDetailId AS parameter " +
                "FROM products p " +
                "JOIN orderitem oi ON p.id = oi.productId " +
                "JOIN orders o ON oi.orderId = o.id " +
                "WHERE YEAR(o.orderDate) = YEAR(CURDATE()) " +
                "ORDER BY p.unitSold DESC";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE id = ?";

        return getProductsByQuery(query, query2, null);
    }

    // Get list products by category
    public List<Product> getProductsByCategory(String category) {
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status," +
                "   p.id AS parameter " +
                "FROM products p " +
                "JOIN categories c ON p.categoryId = c.id " +
                "WHERE c.name = ?";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        return getProductsByQuery(query, query2, category);
    }

    // search products by name
    public List<Product> searchByName(String name) {
        query = "SELECT" +
                "   id," +
                "   typeId," +
                "   categoryId," +
                "   supplierId," +
                "   productname," +
                "   description," +
                "   releaseDate," +
                "   unitSold," +
                "   unitPrice," +
                "   status," +
                "   id AS parameter " +
                "FROM products " +
                "WHERE productname LIKE ?";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        String searchPattern = (name != null && !name.isEmpty()) ? "%" + name + "%" : "%%";
        return getProductsByQuery(query, query2, searchPattern);
    }

    // Get products by query
    public List<Product> getProductsByQuery(String sql1, String sql2, @Nullable String parameter) {
        List<Product> list = new ArrayList<>();
        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(sql1)) {
                if (parameter != null && !parameter.isEmpty()) {
                    stmt.setString(1, parameter);
                }
                try (ResultSet rs = stmt.executeQuery()) {
                    while(rs.next()) {
                        Product p = mapResultSetToProduct(rs);

                        try (PreparedStatement stmt2 = h.getConnection().prepareStatement(sql2)) {
                            stmt2.setInt(1, rs.getInt("parameter"));
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                while(rs2.next()) {
                                    ProductDetail detail = new ProductDetail();
                                    detail.setId(rs2.getInt("id"));
                                    detail.setProductId(rs2.getInt("productId"));
                                    detail.setSize(rs2.getString("size"));
                                    detail.setStock(rs2.getInt("stock"));
                                    detail.setImage(rs2.getString("image"));
                                    detail.setColor(rs2.getString("color"));
                                    p.getDetails().add(detail);
                                }
                            }
                        }

                        int index = list.indexOf(p);
                        if (index != -1) {
                            Product existing = list.get(index);
                            existing.addDetails(p.getDetails());
                        } else {
                            list.add(p);
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        });
    }

    // Get filtered products
    public List<Product> getFilteredProducts(String category, String[] type, String[] sizeF, Double priceMin, Double priceMax) {
        query = "SELECT" +
                "   p.id," +
                "   p.typeId," +
                "   p.categoryId," +
                "   p.supplierId," +
                "   p.productname," +
                "   p.description," +
                "   p.releaseDate," +
                "   p.unitSold," +
                "   p.unitPrice," +
                "   p.status " +
                "FROM products p " +
                "JOIN categories c ON p.categoryId = c.id " +
                "JOIN types t ON p.typeId = t.id " +
                "WHERE c.name = ?";
        query2 = "SELECT" +
                "   id," +
                "   productId," +
                "   size," +
                "   stock," +
                "   image," +
                "   color " +
                "FROM product_details " +
                "WHERE productId = ?";

        if (type != null && type.length > 0) {
            query += " AND t.name IN (" + String.join(",", Collections.nCopies(type.length, "?")) + ")";
        }

        List<Product> re = conn.get().withHandle(h -> {
            List<Product> list = new ArrayList<>();
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                int paramIndex = 1;
                stmt.setString(paramIndex++, category);
                if (type != null && type.length > 0) {
                    for (String t : type) {
                        stmt.setString(paramIndex++, t);
                    }
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Product p = mapResultSetToProduct(rs);

                        try (PreparedStatement stmt2 = h.getConnection().prepareStatement(query2)) {
                            stmt2.setInt(1, p.getId());
                            try (ResultSet rs2 = stmt2.executeQuery()) {
                                while (rs2.next()) {
                                    ProductDetail detail = new ProductDetail();
                                    detail.setId(rs2.getInt("id"));
                                    detail.setProductId(rs2.getInt("productId"));
                                    detail.setSize(rs2.getString("size"));
                                    detail.setStock(rs2.getInt("stock"));
                                    detail.setImage(rs2.getString("image"));
                                    detail.setColor(rs2.getString("color"));
                                    p.getDetails().add(detail);
                                }
                            }
                        }

                        list.add(p);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;
        });

        if (sizeF != null && sizeF.length > 0) {
            Iterator<Product> iterator = re.iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                boolean hasSize = false;

                sizeLoop:
                for (String size : sizeF) {
                    for (ProductDetail productDetail : product.getDetails()) {
                        if (productDetail.getSize().equals(size)) {
                            hasSize = true;
                            break sizeLoop;
                        }
                    }
                }

                if (!hasSize) {
                    iterator.remove();
                }
            }
        }

        if (priceMin != null) {
            re.removeIf(product -> product.getUnitPrice() < priceMin);
        }
        if (priceMax != null) {
            re.removeIf(product -> product.getUnitPrice() > priceMax);
        }

        return re;
    }

    public Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setTypeId(rs.getInt("typeId"));
        p.setCategoryId(rs.getInt("categoryId"));
        p.setSupplierId(rs.getInt("supplierId"));
        p.setProductName(rs.getString("productname"));
        p.setDescription(rs.getString("description"));
        p.setReleaseDate(rs.getDate("releaseDate"));
        p.setUnitSold(rs.getInt("unitSold"));
        p.setUnitPrice(rs.getDouble("unitPrice"));
        p.setStatus(rs.getInt("status"));
        return p;
    }

    // Get ProductDetail by productDetailId
    public ProductDetail getProductDetailById(int id) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM product_details WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(ProductDetail.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Get Product by productId
    public Product getProductById(int id) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM products WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Product.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Get ProductDetails by productId
    public List<ProductDetail> getProductDetailsByProductId(int productId) {
        return JDBIConnector.get().withHandle(handle ->
                handle.createQuery("SELECT * FROM product_details WHERE productId = :productId")
                        .bind("productId", productId)
                        .mapToBean(ProductDetail.class)
                        .list()
        );
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.getAllProducts());
//        System.out.println(dao.getSaleProducts());
//        System.out.println(dao.getBestSellingProducts());
//        System.out.println(dao.getProductsByCategory("Nữ"));
//        System.out.println(dao.searchByName("Quần tây"));
//        System.out.println(dao.getFilteredProducts("Nam", new String[]{"Quần"}, new String[]{"M"}, null, 150000.00));
    }
}