package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductFilter", value = "/productFilter")
public class ProductFilter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String[] type = request.getParameterValues("type");
        String[] size = request.getParameterValues("size");
        String priceMinStr = request.getParameter("price-min");
        String priceMaxStr = request.getParameter("price-max");
        String sortPrice = request.getParameter("sortPrice");
        Double priceMin = (priceMinStr != null && !priceMinStr.isEmpty()) ? Double.parseDouble(priceMinStr) : null;
        Double priceMax = (priceMaxStr != null && !priceMaxStr.isEmpty()) ? Double.parseDouble(priceMaxStr) : null;

        ProductService productService = new ProductService();
        List<Product> filteredProducts = productService.getFilteredProducts(category, type, size, priceMin, priceMax, sortPrice);

        request.setAttribute("allProducts", filteredProducts);
        request.setAttribute("category", category);
        request.getRequestDispatcher("productPagination").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}