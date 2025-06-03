package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.view;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "AdminView", urlPatterns = {"/admin/manager-orders", "/admin/manager-users, ", "/admin/manager-products"})
public class AdminView extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Forward đến JSP (chỉ hiển thị giao diện)
        req.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp")
                .forward(req, resp);
    }
}
