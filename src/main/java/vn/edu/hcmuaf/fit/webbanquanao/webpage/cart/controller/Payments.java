package vn.edu.hcmuaf.fit.webbanquanao.webpage.cart.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.webbanquanao.user.model.User;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "Payments", value = "/payments")
public class Payments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        CartService cart = (CartService) session.getAttribute("cart");
//        User auth = (User) session.getAttribute("auth");
//
//        if (cart == null || auth == null) {
//            response.sendRedirect("cart.jsp");
//            return;
//        }
//
//        int userId = auth.getId();
//        int paymentId = 1;
//        int couponId = 1;
//        Date orderDate = new Date(System.currentTimeMillis());
//        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
//        boolean status = false;
//
////        boolean addOrder = cart.addToOrder(userId, paymentId, couponId, orderDate, totalPrice, status);
//        boolean addOrder = false;
//        if (addOrder) {
//            request.setAttribute("message", "Thanh toán thành công! Đơn hàng của bạn đã được ghi nhận.");
//        } else {
//            request.setAttribute("message", "Thanh toán thất bại. Vui lòng thử lại sau.");
//        }
//        request.setAttribute("payments", addOrder);
//        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

}