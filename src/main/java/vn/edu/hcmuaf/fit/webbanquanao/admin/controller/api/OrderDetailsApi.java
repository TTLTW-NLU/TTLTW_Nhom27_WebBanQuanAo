package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrderItem;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AOrderService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

@WebServlet(name = "OrderDetailsApi", urlPatterns = "/admin/api/order-details/*")
public class OrderDetailsApi extends BaseApiServlet {
    private final AOrderService orderService = new AOrderService();
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ORDER_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID đơn hàng");
            return;
        }

        try {
            int orderId = Integer.parseInt(id);
            Map<Integer, AOrderItem> itemsMap = orderService.showOrderItem(orderId);
            List<AOrderItem> items = new ArrayList<>(itemsMap.values());

            writeJson(resp, items);

        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId for details: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", "Lỗi server lấy chi tiết đơn hàng: " + e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi truy xuất chi tiết đơn hàng");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ORDER_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID chi tiết đơn hàng trong URL");
            return;
        }

        try {
            int detailId = Integer.parseInt(id);
            AOrderItem item = gson.fromJson(readBody(req), AOrderItem.class);
            validateUpdate(item);

            boolean updated = orderService.updateOrderItem(item, detailId, item.getOrderId());
            if (updated) {
                logService.logUpdateEntity(ctx.username, ResourceNames.ORDER_DETAIL, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật chi tiết đơn hàng thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update detail failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy chi tiết đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid detailId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "detailId không hợp lệ");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật chi tiết đơn hàng");
        }
    }

    // Validation giống OrdersApi
    private void validateUpdate(AOrderItem o) {
        StringBuilder errors = new StringBuilder();
        if (o.getId() == null || o.getId() <= 0) errors.append("ID chi tiết đơn hàng không hợp lệ. ");
        if (o.getOrderId() == null || o.getOrderId() <= 0) errors.append("ID đơn hàng không hợp lệ. ");
        if (o.getProductName() == null || o.getProductName().trim().isEmpty()) errors.append("Tên sản phẩm bị thiếu. ");
        if (o.getQuantity() == null || o.getQuantity() < 0) errors.append("Số lượng phải >= 0. ");
        if (o.getUnitPrice() < 0) errors.append("Giá đơn vị phải >= 0. ");
        if (o.getDiscount() < 0) errors.append("Giảm giá phải >= 0. ");
        if (errors.length() > 0) {
            throw new IllegalArgumentException("Lỗi khi cập nhật OrderDetail (ID = " + o.getId() + "): " + errors.toString().trim());
        }
    }
}
