package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao;

import vn.edu.hcmuaf.fit.webbanquanao.database.JDBIConnector;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.CommentProduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CommentDAO {
    Map<Integer, CommentProduct> data = new HashMap<>();
    JDBIConnector conn;
    String query;

    public CommentDAO() {
        conn = new JDBIConnector();
    }

    // Get all comments
    public Map<Integer, CommentProduct> getAll() {
        query = "SELECT" +
                "   c.id AS id," +
                "   c.content AS content," +
                "   u.avatar AS authorAvatar," +
                "   u.userName AS authorName," +
                "   c.rating AS rating " +
                "FROM comment c " +
                "JOIN users u ON c.userId = u.id";

        return conn.get().withHandle(h -> {
            Map<Integer, CommentProduct> result = new HashMap<>();
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()
            ) {
                while (rs.next()) {
                    int commentId = rs.getInt("id");
                    CommentProduct c = result.getOrDefault(commentId, new CommentProduct());

                    c.setId(commentId);
                    c.setContent(rs.getString("content"));
                    c.setAuthorAvatar(rs.getString("authorAvatar"));
                    c.setAuthorName(rs.getString("authorName"));
                    c.setRating(rs.getInt("rating"));

                    result.put(commentId, c);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    // Get comment by productId
    public Map<Integer, CommentProduct> getCommentByProductId(int productId) {
        query = "SELECT" +
                "   c.id AS id," +
                "   c.content AS content," +
                "   u.avatar AS authorAvatar," +
                "   u.userName AS authorName," +
                "   c.rating AS rating " +
                "FROM comment c " +
                "JOIN users u ON c.userId = u.id " +
                "JOIN products p ON c.productId = p.id " +
                "WHERE p.id = ?";

        return conn.get().withHandle(h -> {
            Map<Integer, CommentProduct> result = new HashMap<>();
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, productId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int commentId = rs.getInt("id");
                        CommentProduct c = result.getOrDefault(commentId, new CommentProduct());

                        c.setId(commentId);
                        c.setContent(rs.getString("content"));
                        c.setAuthorAvatar(rs.getString("authorAvatar"));
                        c.setAuthorName(rs.getString("authorName"));
                        c.setRating(rs.getInt("rating"));

                        result.put(commentId, c);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return result;
        });
    }

    // Insert
    public boolean add(String description, int rating, int userId, int productId) {
        query = "INSERT INTO comment (userId, productId, content, rating) VALUES (?, ?, ?, ?)";

        return conn.get().withHandle(h -> {
            try (PreparedStatement stmt = h.getConnection().prepareStatement(query)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, productId);
                stmt.setString(3, description);
                stmt.setInt(4, rating);

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return true;
            }
        });
    }

    public static void main(String[] args) {
        CommentDAO dao = new CommentDAO();
        System.out.println(dao.getCommentByProductId(1));
    }
}
