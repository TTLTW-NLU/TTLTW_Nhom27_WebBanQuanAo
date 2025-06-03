package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao.CommentDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.CommentProduct;

import java.util.ArrayList;
import java.util.List;

public class CommentService {
    CommentDAO dao;

    public CommentService() {
        dao = new CommentDAO();
    }

    // Get all comments
    public List<CommentProduct> getAll() {
        List<CommentProduct> result = new ArrayList<>();
        dao.getAll().forEach((key, value) ->{
            result.add(value);
        });
        return result;
    }

    // Get comment by productId and userId
    public List<CommentProduct> getCommentByProductId(int productId) {
        List<CommentProduct> result = new ArrayList<>();
        dao.getCommentByProductId(productId).forEach((key, value) ->{
            result.add(value);
        });
        return result;
    }

    // Insert
    public boolean add(String description, int rating, int userId, int productId) {
        return dao.add(description, rating, userId, productId);
    }
}
