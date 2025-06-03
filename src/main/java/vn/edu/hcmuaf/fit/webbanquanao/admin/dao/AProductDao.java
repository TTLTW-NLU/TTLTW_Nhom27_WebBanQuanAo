package vn.edu.hcmuaf.fit.webbanquanao.admin.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProduct;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProductDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;

public class AProductDao {
    public Map<Integer, AProduct> listProduct;

    public AProductDao() {
        listProduct = getAllProducts();
    }

    public Map<Integer, AProductDetails> getAllProductDetails(Integer id) {
        Map<Integer, AProductDetails> productDetails = new LinkedHashMap<>();
        String sql = "SELECT * FROM product_details WHERE productId = ? ORDER BY id DESC;";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AProductDetails productDetail = new AProductDetails();
                    productDetail.setId(rs.getInt("id"));
                    productDetail.setProductId(rs.getInt("productId"));
                    productDetail.setSize(rs.getString("size"));
                    productDetail.setStock(rs.getInt("stock"));
                    productDetail.setColor(rs.getString("color"));
                    productDetail.setImage(rs.getString("image"));
                    productDetails.put(productDetail.getId(), productDetail);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach chi tiet san pham: " + e.getMessage());
            }
            return productDetails;
        });
    }

    public Map<Integer, AProduct> getAllProducts() {
        Map<Integer, AProduct> products = new LinkedHashMap<>();
        String sql = "SELECT * FROM products ORDER BY id DESC;";

        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    AProduct product = new AProduct();
                    product.setId(rs.getInt("id"));
                    product.setTypeId(rs.getInt("typeId"));
                    product.setCategoryId(rs.getInt("categoryId"));
                    product.setSupplierId(rs.getInt("supplierId"));
                    product.setName(rs.getString("productName"));
                    product.setDescription(rs.getString("description"));
                    product.setReleaseDate(rs.getDate("releaseDate"));
                    product.setUnitSold(rs.getInt("unitSold"));
                    product.setUnitPrice(rs.getDouble("unitPrice"));
                    product.setStatus(rs.getInt("status"));
                    products.put(product.getId(), product);
                }
            } catch (Exception e) {
                System.out.println("Loi khi lay danh sach san pham: " + e.getMessage());
            }
            return products;
        });
    }


    public boolean create(Object obj) {
        return JDBIConnector.get().withHandle(h -> {
            AProduct product = (AProduct) obj;
            String sql = "INSERT INTO products(typeId, categoryId, supplierId, productName, description, releaseDate, unitSold, unitPrice, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";

            try (PreparedStatement ps = h.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, product.getTypeId());
                ps.setInt(2, product.getCategoryId());
                ps.setInt(3, product.getSupplierId());
                ps.setString(4, product.getName());
                ps.setString(5, product.getDescription());
                ps.setDate(6, product.getReleaseDate());
                ps.setInt(7, product.getUnitSold());
                ps.setDouble(8, product.getUnitPrice());
                ps.setInt(9, product.getStatus());

                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int newId = generatedKeys.getInt(1);
                            product.setId(newId);
                            listProduct.put(newId, product); // Cập nhật danh sách sản phẩm trong bộ nhớ
                        }
                    }
                    return true;
                }
            } catch (Exception e) {
                System.out.println("Lỗi khi thêm sản phẩm: " + e.getMessage());
            }
            return false;
        });
    }


    public boolean createProductDetails(Object obj) {
        return JDBIConnector.get().withHandle(h -> {
            AProductDetails productDetail = (AProductDetails) obj;
            String sql = "INSERT INTO product_details(productId, size, stock, image, color) VALUES(?, ?, ?, ?, ?);";
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setInt(1, productDetail.getProductId());
                ps.setString(2, productDetail.getSize());
                ps.setInt(3, productDetail.getStock());
                ps.setString(4, productDetail.getImage());
                ps.setString(5, productDetail.getColor());
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi them product details: " + e.getMessage());
            }
            return false;
        });
    }


    public boolean update(Object obj, Integer id) {
        return JDBIConnector.get().withHandle(h -> {
            AProduct product = (AProduct) obj;
            listProduct.replace(id, product);
            String sql = "UPDATE products SET id = ?, typeId = ?, categoryId = ?, supplierId = ?, productName = ?, description = ?, releaseDate = ?, unitSold = ?, unitPrice = ?, status = ? WHERE id = ?;";
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setInt(1, product.getId());
                ps.setInt(2, product.getTypeId());
                ps.setInt(3, product.getCategoryId());
                ps.setInt(4, product.getSupplierId());
                ps.setString(5, product.getName());
                ps.setString(6, product.getDescription());
                ps.setDate(7, product.getReleaseDate());
                ps.setInt(8, product.getUnitSold());
                ps.setDouble(9, product.getUnitPrice());
                ps.setInt(10, product.getStatus());
                ps.setInt(11 , id);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi update product: " + e.getMessage());
            }
            return false;
        });
    }

    public boolean updateProductDetails(Object obj, Integer id, Integer productId) {
        return JDBIConnector.get().withHandle(h -> {
            AProductDetails productDetail = (AProductDetails) obj;
            String sql = "UPDATE product_details SET id = ?, productId = ?, size = ?, stock = ?, image = ?, color = ? WHERE id = ? AND productId = ?;";
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setInt(1, productDetail.getId());
                ps.setInt(2, productDetail.getProductId());
                ps.setString(3, productDetail.getSize());
                ps.setInt(4, productDetail.getStock());
                ps.setString(5, productDetail.getImage());
                ps.setString(6, productDetail.getColor());
                ps.setInt(7, id);
                ps.setInt(8, productId);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Loi khi update product details: " + e.getMessage());
            }
            return false;
        });
    }


    public boolean delete(Integer id, int status) {
        String sql = "UPDATE products SET status = ? WHERE id = ?";
        return JDBIConnector.get().withHandle(h -> {
            try (PreparedStatement ps = h.getConnection().prepareStatement(sql)) {
                ps.setInt(1, status);
                ps.setInt(2, id);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Lỗi khi cập nhật trạng thái sản phẩm: " + e.getMessage());
                return false;
            }
        });
    }


}
