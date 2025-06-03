package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    JDBIConnector conn;
    String query, query2;

    public Dao() {
        conn = new JDBIConnector();
    }

    // get all products of shop
    public List<Product> getAllProduct() {
        List<Product> products = new ArrayList<Product>();
        query = "SELECT * FROM products";
        query2 = "SELECT * FROM product_details WHERE productId = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt1 = h.getConnection().prepareStatement(query);
                 ResultSet rs = stmt1.executeQuery()) {
                while(rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setTypeId(rs.getInt("typeId"));
                    p.setCategoryId(rs.getInt("categoryId"));
                    p.setSupplierId(rs.getInt("supplierId"));
                    p.setProductName(rs.getString("productName"));
                    p.setDescription(rs.getString("description"));
                    p.setReleaseDate(rs.getDate("releaseDate"));
                    p.setUnitSold(rs.getInt("unitSold"));
                    p.setUnitPrice(rs.getDouble("unitPrice"));
                    p.setStatus(rs.getInt("status"));

                    try(PreparedStatement stmt2 = h.getConnection().prepareStatement(query2)) {
                        stmt2.setInt(1, p.getId());
                        try (ResultSet rs2 = stmt2.executeQuery()) {
                            List<ProductDetail> pdList = new ArrayList<>();
                            while (rs2.next()) {
                                ProductDetail pd = new ProductDetail();
                                pd.setId(rs2.getInt("id"));
                                pd.setProductId(p.getId());
                                pd.setSize(rs2.getString("size"));
                                pd.setStock(rs2.getInt("stock"));
                                pd.setImage(rs2.getString("image"));
                                pd.setColor(rs2.getString("color"));

                                pdList.add(pd);
                            }
                            p.setDetails(pdList);
                        }
                    }
                    products.add(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return products;
        });
    }

    // get product by productDetail
    public List<Product> getProductByDetail(int productId, String size, String color) {
        List<Product> products = new ArrayList<Product>();
        query = "SELECT * FROM products";
        query2 = "SELECT * FROM product_details WHERE productId = ? AND size = ? AND color = ?";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt1 = h.getConnection().prepareStatement(query);
            ResultSet rs = stmt1.executeQuery()) {
                while(rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setTypeId(rs.getInt("typeId"));
                    p.setCategoryId(rs.getInt("categoryId"));
                    p.setSupplierId(rs.getInt("supplierId"));
                    p.setProductName(rs.getString("productName"));
                    p.setDescription(rs.getString("description"));
                    p.setReleaseDate(rs.getDate("releaseDate"));
                    p.setUnitSold(rs.getInt("unitSold"));
                    p.setUnitPrice(rs.getDouble("unitPrice"));
                    p.setStatus(rs.getInt("status"));

                    try(PreparedStatement stmt2 = h.getConnection().prepareStatement(query2)) {
                        stmt2.setInt(1, p.getId());
                        stmt2.setString(2, size);
                        stmt2.setString(3, color);
                        try (ResultSet rs2 = stmt2.executeQuery()) {
                            List<ProductDetail> pdList = new ArrayList<>();
                            while (rs2.next()) {
                                ProductDetail pd = new ProductDetail();
                                pd.setId(rs2.getInt("id"));
                                pd.setProductId(p.getId());
                                pd.setSize(rs2.getString("size"));
                                pd.setStock(rs2.getInt("stock"));
                                pd.setImage(rs2.getString("image"));
                                pd.setColor(rs2.getString("color"));

                                pdList.add(pd);
                            }
                            p.setDetails(pdList);
                        }
                    }
                    products.add(p);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return products;
        });
    }

    public static void main(String[] args) {
        Dao dao = new Dao();
        System.out.println(dao.getAllProduct());
    }
}
