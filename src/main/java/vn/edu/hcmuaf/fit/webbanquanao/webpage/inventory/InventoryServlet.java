package vn.edu.hcmuaf.fit.webbanquanao.webpage.inventory;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "InventoryServlet", value = "/inventory")
public class InventoryServlet extends HttpServlet {
    private final InventoryService inventoryService = new InventoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.equals("view")) {
            List<ProductDetail> list = inventoryService.getAllInventory();
            request.setAttribute("inventoryList", list);
            request.getRequestDispatcher("/inventory.jsp").forward(request, response);

        } else if (action.equals("lowstock")) {
            String thresholdParam = request.getParameter("threshold");
            int threshold = thresholdParam != null ? Integer.parseInt(thresholdParam) : 5;
//            List<ProductDetailInventory> lowStockList = inventoryService.getLowStockItems(threshold);
//            request.setAttribute("inventoryList", lowStockList);
            request.setAttribute("threshold", threshold);
            request.getRequestDispatcher("/inventory.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");

        boolean success = false;

        try {
            switch (type) {
                case "update":
                    int idUpdate = Integer.parseInt(request.getParameter("id"));
                    int stockUpdate = Integer.parseInt(request.getParameter("amount"));
                    success = inventoryService.updateStock(idUpdate, stockUpdate);
                    break;
                case "increase":
                    int idInc = Integer.parseInt(request.getParameter("id"));
                    int amountInc = Integer.parseInt(request.getParameter("amount"));
                    success = inventoryService.increaseStock(idInc, amountInc);
                    break;
                case "decrease":
                    int idDec = Integer.parseInt(request.getParameter("id"));
                    int amountDec = Integer.parseInt(request.getParameter("amount"));
                    success = inventoryService.decreaseStock(idDec, amountDec);
                    break;
                case "add":
                    ProductDetail newItem = new ProductDetail();
                    newItem.setProductId(Integer.parseInt(request.getParameter("productId")));
                    newItem.setSize(request.getParameter("size"));
                    newItem.setColor(request.getParameter("color"));
                    newItem.setStock(Integer.parseInt(request.getParameter("stock")));
                    newItem.setImage(request.getParameter("image"));
                    success = inventoryService.addProductDetail(newItem);
                    break;
                case "delete":
                    int idDelete = Integer.parseInt(request.getParameter("id"));
                    success = inventoryService.deleteProductDetail(idDelete);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log error
        }

        if (success) {
            response.sendRedirect("inventory?action=view");
        } else {
            response.sendRedirect("inventory?action=view&error=true");
        }
    }
}