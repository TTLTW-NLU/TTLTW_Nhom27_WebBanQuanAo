package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProductDetails;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AProductService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

@WebServlet(name = "ProductDetailsApi", urlPatterns = "/admin/api/product-details/*")
public class ProductDetailsApi extends BaseApiServlet {
    private final AProductService productService = new AProductService();
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID sản phẩm");
            return;
        }

        try {
            int productId = Integer.parseInt(id);
            Map<Integer, AProductDetails> detailsMap = productService.showProductDetails(productId);
            List<AProductDetails> details = new ArrayList<>(detailsMap.values());

            if (details.isEmpty()) {
                writeJson(resp, Map.of("message", "Sản phẩm ID = " + productId + " không có chi tiết!"));
                return;
            }

            writeJson(resp, details);

        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId for details: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", "Lỗi server lấy chi tiết sản phẩm: " + e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi truy xuất chi tiết sản phẩm");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        try {
            AProductDetails pd = gson.fromJson(readBody(req), AProductDetails.class);
            validateCreate(pd);

            boolean created = productService.createProductDetails(pd);
            if (created) {
                logService.logCreateEntity(ctx.username, ResourceNames.ADMIN_API_PRODUCT_MANAGE, "new", ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo chi tiết sản phẩm thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Tạo chi tiết cho sản phẩm thất bại", ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_CONFLICT, "Không thể tạo chi tiết sản phẩm");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo chi tiết sản phẩm");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID chi tiết trong URL");
            return;
        }

        try {
            int detailId = Integer.parseInt(id);
            AProductDetails pd = gson.fromJson(readBody(req), AProductDetails.class);
            validateUpdate(pd);

            boolean updated = productService.updateProductDetails(pd, detailId, pd.getProductId());
            if (updated) {
                logService.logUpdateEntity(ctx.username, ResourceNames.PRODUCT_DETAIL, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật chi tiết sản phẩm thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Cập nhật thất bại cho chi tiết ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy chi tiết sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid detailId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật chi tiết sản phẩm");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID chi tiết trong URL");
            return;
        }

        try {
            int detailId = Integer.parseInt(id);
            if (productService.delete(detailId)) {
                logService.logDeleteEntity(ctx.username, ResourceNames.PRODUCT_DETAIL, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa chi tiết sản phẩm thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Xóa thất bại chi tiết ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy chi tiết sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid detailId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa chi tiết sản phẩm");
        }
    }

    // Validation methods
    private void validateCreate(AProductDetails pd) {
        StringBuilder errors = new StringBuilder();
        if (pd.getSize() == null || pd.getSize().trim().isEmpty()) errors.append("Kích thước không được để trống. ");
        if (pd.getColor() == null || pd.getColor().trim().isEmpty()) errors.append("Màu sắc không được để trống. ");
        if (pd.getStock() == null || pd.getStock() < 0) errors.append("Số lượng phải >= 0. ");
        if (pd.getImage() == null || pd.getImage().trim().isEmpty()) errors.append("Hình ảnh không được để trống. ");
        if (errors.length() > 0) {
            throw new IllegalArgumentException("Lỗi khi tạo chi tiết sản phẩm: " + errors.toString().trim());
        }
    }

    private void validateUpdate(AProductDetails pd) {
        StringBuilder errors = new StringBuilder();
        if (pd.getId() == null) errors.append("ID chi tiết bị thiếu. ");
        if (pd.getProductId() == null) errors.append("ID sản phẩm bị thiếu. ");
        if (pd.getSize() == null || pd.getSize().trim().isEmpty()) errors.append("Kích thước không được để trống. ");
        if (pd.getColor() == null || pd.getColor().trim().isEmpty()) errors.append("Màu sắc không được để trống. ");
        if (pd.getStock() == null || pd.getStock() < 0) errors.append("Số lượng phải >= 0. ");
        if (pd.getImage() == null || pd.getImage().trim().isEmpty()) errors.append("Hình ảnh không được để trống. ");
        if (errors.length() > 0) {
            throw new IllegalArgumentException("Lỗi khi cập nhật chi tiết sản phẩm ID=" + pd.getId() + ": " + errors.toString().trim());
        }
    }
}
