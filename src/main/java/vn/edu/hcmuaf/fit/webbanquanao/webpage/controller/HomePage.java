package vn.edu.hcmuaf.fit.webbanquanao.webpage.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomePage", value = "/homePage")
public class HomePage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService service = new ProductService();
        List<Product> list;

        // SaleProducts list and BestSellingProducts list
        list = service.getSaleProducts();
        request.setAttribute("saleProducts", list);
        list = service.getBestSellingProducts();
        request.setAttribute("bestSellingProducts", list);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}