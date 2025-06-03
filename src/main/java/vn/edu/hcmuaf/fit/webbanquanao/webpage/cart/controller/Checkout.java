package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.service.CheckoutService;

import java.io.IOException;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // get userId from session
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("auth") == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            User user = (User) session.getAttribute("auth");

            // checkout
            boolean isSuccess = false;
            CheckoutService service = new CheckoutService();
            isSuccess = service.checkout(user.getId(), 1, 1);

            // redirection
            if (isSuccess) {
                response.sendRedirect("homePage");
            } else {
                response.sendRedirect("checkout.jsp?error=checkout_failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("checkout.jsp?error=checkout_failed");
        }
    }
}