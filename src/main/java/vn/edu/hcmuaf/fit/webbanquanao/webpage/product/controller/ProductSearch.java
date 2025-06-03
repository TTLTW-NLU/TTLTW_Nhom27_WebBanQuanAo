package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ProductSearch", value = "/productSearch")
public class ProductSearch extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = new ProductService();
        List<Product> products = service.getAllProducts();
        request.setAttribute("allProducts", products);
        request.getRequestDispatcher("search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");
        List<Product> products;
        ProductService service = new ProductService();

        if (keyword == null || keyword.trim().isEmpty()) {
            keyword = "";
        }

        products = service.searchByName(keyword);
        PrintWriter out = response.getWriter();
        if (!products.isEmpty()) {
            for (Product p : products) {
                out.println("<div class=\"col-md-3 mt-4\">\n" +
                        "                            <a href=\"productDetail?pid=" + p.getId() + "\" class=\"product-card\">\n" +
                        "                                <img src=\"assets/product-imgs/" + p.getDetails().getFirst().getImage() + "\" alt=\"" + p.getProductName() + "\" class=\"product-image img-fluid\">\n" +
                        "                                <div class=\"product-title\">" + p.getProductName() + "</div>\n" +
                        "                                <div class=\"product-price\" data-price=\"" + p.getUnitPrice() + "\"></div>\n" +
                        "                            </a>\n" +
                        "                        </div>");
            }
        } else {
            out.println("<h2 class=\"display-5 mt-5 text-center w-100 no-result\">Không tìm thấy sản phẩm</h2>");
        }
    }
}
