package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.CommentProduct;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.CommentService;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "ProductDetail", value = "/productDetail")
public class ProductDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        CommentService commentService = new CommentService();

        String pidParam = request.getParameter("pid");
        int pid;
        if (pidParam != null) {
            pid = Integer.parseInt(pidParam);
        } else {
            pid = (int) request.getAttribute("pid");
        }

        Product p = productService.getDetail(pid);
        List<CommentProduct> comments = commentService.getCommentByProductId(pid);

        // Unique color and size
        Set<String> colorSet = new LinkedHashSet<>();
        Set<String> sizeSet = new LinkedHashSet<>();
        for (ProductDetail d : p.getDetails()) {
            colorSet.add(d.getColor());
            sizeSet.add(d.getSize());
        }
        request.setAttribute("uniqueColors", colorSet);
        request.setAttribute("uniqueSizes", sizeSet);

        request.setAttribute("p", p);
        request.setAttribute("comments", comments);
        request.getRequestDispatcher("products_detail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String description = request.getParameter("description");
        String ratingParam = request.getParameter("rating");
        String userIdParam = request.getParameter("userId");
        String productIdParam = request.getParameter("pid");

        int rating = Integer.parseInt(ratingParam);
        int productId = Integer.parseInt(productIdParam);
        int userId = 0;
        try {
            userId = Integer.parseInt(userIdParam);
        } catch (NumberFormatException e) {
            response.getWriter().println("ID người dùng hoặc ID sản phẩm không hợp lệ.");
            return;
        }

        CommentService service = new CommentService();
        service.add(description, rating, userId, productId);

        request.setAttribute("pid", productId);
        doGet(request, response);
    }
}