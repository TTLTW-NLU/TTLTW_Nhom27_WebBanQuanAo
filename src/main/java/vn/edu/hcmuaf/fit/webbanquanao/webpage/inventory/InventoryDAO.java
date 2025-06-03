package vn.edu.hcmuaf.fit.webbanquanao.webpage.inventory;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.util.List;

public class InventoryDAO {
    private final Jdbi jdbi;

    public InventoryDAO() {
        this.jdbi = JDBIConnector.get();
    }

    // Get all products
    public List<ProductDetail> getAllProductDetails() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM product_details")
                        .mapToBean(ProductDetail.class)
                        .list()
        );
    }

    // Update stock
    public boolean updateStock(int productDetailId, int newStock) {
        String sql = "UPDATE product_details SET stock = :stock WHERE id = :id";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("stock", newStock)
                        .bind("id", productDetailId)
                        .execute()
        );
        return rows > 0;
    }

    // Increase stock
    public boolean increaseStock(int productDetailId, int amount) {
        String sql = "UPDATE product_details SET stock = stock + :amount WHERE id = :id";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("amount", amount)
                        .bind("id", productDetailId)
                        .execute()
        );
        return rows > 0;
    }

    // Decrease stock
    public boolean decreaseStock(int productDetailId, int amount) {
        String sql = "UPDATE product_details SET stock = stock - :amount WHERE id = :id AND stock >= :amount";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("amount", amount)
                        .bind("id", productDetailId)
                        .execute()
        );
        return rows > 0;
    }

    // Get list of products with stock below threshold
//    public List<ProductDetailInventory> getLowStockItems(int threshold) {
//        String sql = "SELECT * FROM product_details WHERE stock < :threshold";
//        return jdbi.withHandle(handle ->
//                handle.createQuery(sql)
//                        .bind("threshold", threshold)
//                        .mapToBean(ProductDetailInventory.class)
//                        .list()
//        );
//    }

    // Delete product to warehouse
    public boolean deleteProductDetail(int id) {
        String sql = "DELETE FROM product_details WHERE id = :id";
        int rows = jdbi.withHandle(handle ->
                handle.createUpdate(sql)
                        .bind("id", id)
                        .execute()
        );
        return rows > 0;
    }

    // Add product to inventory
    public int addProduct(Product product) {
        String sql = """
                    INSERT INTO Product (typeId, categoryId, supplierId, productName, description, releaseDate, unitSold, unitPrice, status)
                    VALUES (:typeId, :categoryId, :supplierId, :productName, :description, NOW(), :unitSold, :unitPrice, :status)
                """;

        return JDBIConnector.get().withHandle(handle ->
                handle.createUpdate(sql)
                        .bindBean(product)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }

    // Add product detail to inventory
    public boolean addProductDetail(ProductDetail detail) {
        String sql = """
                    INSERT INTO product_details (productId, size, color, stock, image)
                    VALUES (:productId, :size, :color, :stock, :image)
                """;

        int rows = JDBIConnector.get().withHandle(handle ->
                handle.createUpdate(sql)
                        .bindBean(detail)
                        .execute()
        );
        return rows > 0;
    }


    // Get product detail by productId
    public List<ProductDetail> getDetailsByProductId(int productId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM product_details WHERE productId = :productId")
                        .bind("productId", productId)
                        .mapToBean(ProductDetail.class)
                        .list()
        );
    }
}
