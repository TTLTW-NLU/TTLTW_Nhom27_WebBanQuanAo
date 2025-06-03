package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AOrder;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AOrderService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

import java.io.IOException;
import java.util.*;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

@WebServlet(name = "OrdersApi", urlPatterns = "/admin/api/orders/*")
public class OrdersApi extends BaseApiServlet {
    private final AOrderService orderService = new AOrderService();
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ORDER_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            List<AOrder> orders = new ArrayList<>(orderService.showOrders().values());

            Object viewedFlag = ctx.session.getAttribute("viewAllOrders");
            if (!Boolean.TRUE.equals(viewedFlag)) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), ResourceNames.ADMIN_API_ORDER_MANAGE, ctx.permissions, ctx.ip, ctx.roles);
                ctx.session.setAttribute("viewAllOrders", Boolean.TRUE);
            }
            writeJson(resp, orders);
        } else {
            handleGetById(id, ctx, req, resp);
        }
    }

    private void handleGetById(String id, ApiContext ctx, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int orderId = Integer.parseInt(id);
            AOrder order = orderService.showOrders().get(orderId);
            if (order != null) {
                writeJson(resp, order);
            } else {
                logService.logCustom(ctx.username, "WARN", "Order not found ID=" + orderId, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ORDER_MANAGE);
        try {
            AOrder order = gson.fromJson(readBody(req), AOrder.class);
            validateCreate(order);
            Map<Integer, AOrder> orders = orderService.showOrders();

            if (orders.containsKey(order.getId())) {
                logService.logCreateEntity(ctx.username, ResourceNames.ADMIN_API_ORDER_MANAGE, String.valueOf(order.getId()), ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_CONFLICT, "Đơn hàng đã tồn tại");
            } else {
                orders.put(order.getId(), order);
                logService.logCreateEntity(ctx.username, ResourceNames.ADMIN_API_ORDER_MANAGE, String.valueOf(order.getId()), ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo đơn hàng thành công");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo đơn hàng");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ORDER_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int orderId = Integer.parseInt(id);
            AOrder order = gson.fromJson(readBody(req), AOrder.class);
            validateUpdate(order);

            if (orderService.updateOrder(order, orderId)) {
                logService.logUpdateEntity(ctx.username, ResourceNames.ORDER, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật đơn hàng");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_ORDER_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int orderId = Integer.parseInt(id);
            if (orderService.deleteOrder(orderId)) {
                logService.logDeleteEntity(ctx.username, ResourceNames.ORDER, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa đơn hàng thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Delete failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy đơn hàng");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid orderId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "orderId không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa đơn hàng");
        }
    }

    // Validation
    private void validateCreate(AOrder o) {
        StringBuilder errors = new StringBuilder();
        if (o.getId() == null) errors.append("ID bị thiếu. ");
        if (o.getFirstName() == null || o.getFirstName().trim().isEmpty()) errors.append("Tên người nhận bị thiếu. ");
        if (o.getOrderDate() == null) errors.append("Ngày đặt hàng bị thiếu. ");
        if (o.getTotalPrice() <= 0) errors.append("Tổng tiền phải lớn hơn 0. ");
        if (!errors.isEmpty())
            throw new IllegalArgumentException("Lỗi khi tạo Order (ID = " + o.getId() + "): " + errors.toString().trim());
    }


    private void validateUpdate(AOrder o) {
        StringBuilder errors = new StringBuilder();
        if (o.getFirstName() == null || o.getFirstName().trim().isEmpty()) errors.append("Tên người nhận bị thiếu. ");
        if (o.getPaymentId() == null) errors.append("Phương thức thanh toán bị thiếu. ");
        if (!errors.isEmpty())
            throw new IllegalArgumentException("Lỗi khi cập nhật Order (ID = " + o.getId() + "): " + errors.toString().trim());
    }

}
