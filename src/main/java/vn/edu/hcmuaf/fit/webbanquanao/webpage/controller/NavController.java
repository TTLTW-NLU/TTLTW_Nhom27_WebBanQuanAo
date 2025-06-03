package vn.edu.hcmuaf.fit.webbanquanao.webpage.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NavController", value = "/navController")
public class NavController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");

        ProductService productService = new ProductService();
        List<Product> products = productService.getProductsByCategory(category);

        request.setAttribute("allProducts", products);
        request.setAttribute("category", category);
//        String path = "/".concat(category.toLowerCase()).concat(".jsp");
//        request.setAttribute("path", path);
        request.getRequestDispatcher("productPagination").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}