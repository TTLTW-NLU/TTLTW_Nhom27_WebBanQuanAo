package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.JsonSyntaxException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.webbanquanao.admin.model.AProduct;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.AProductService;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.webbanquanao.util.ResourceNames;

@WebServlet(name = "ProductsApi", urlPatterns = "/admin/api/products/*")
public class ProductsApi extends BaseApiServlet {
    private final AProductService productService = new AProductService();
    private final UserLogsService logService = UserLogsService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        String id = extractId(req.getPathInfo());

        if (id == null) {
            List<AProduct> products = new ArrayList<>(productService.showProduct().values());

            Object viewedFlag = ctx.session.getAttribute("viewAllProducts");
            if (!Boolean.TRUE.equals(viewedFlag)) {
                logService.logAccessGranted(ctx.username, req.getRequestURI(), ResourceNames.ADMIN_API_PRODUCT_MANAGE, ctx.permissions, ctx.ip, ctx.roles);
                ctx.session.setAttribute("viewAllProducts", Boolean.TRUE);
            }
            writeJson(resp, products);
        } else {
            handleGetById(id, ctx, req, resp);
        }
    }

    private void handleGetById(String id, ApiContext ctx, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int pid = Integer.parseInt(id);
            AProduct p = productService.getProductById(pid);
            if (p != null) {
                writeJson(resp, p);
            } else {
                logService.logCustom(ctx.username, "WARN", "Product not found ID=" + pid, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);

        try {
            AProduct p = gson.fromJson(readBody(req), AProduct.class);
            validateCreate(p);

            boolean success = productService.createProduct(p);

            if (success) {
                logService.logCreateEntity(ctx.username, ResourceNames.ADMIN_API_PRODUCT_MANAGE, "new", ctx.ip, ctx.roles); // hoặc "new" hoặc bất cứ gì bạn muốn
                sendSuccess(resp, HttpServletResponse.SC_CREATED, "Tạo sản phẩm thành công");
            } else {
                sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể tạo sản phẩm");
            }
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi tạo sản phẩm");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        String id = extractId(req.getPathInfo());
        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int pid = Integer.parseInt(id);

            String jsonBody = readBody(req);

            // Parse với Gson vừa tạo
            AProduct p = gson.fromJson(jsonBody, AProduct.class);

            validateUpdate(p);

            if (productService.updateProduct(pid, p)) {
                logService.logUpdateEntity(ctx.username, ResourceNames.PRODUCT, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Cập nhật thành công");
            } else {
                logService.logCustom(ctx.username, "WARN", "Update failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (JsonSyntaxException | IllegalArgumentException e) {
            logService.logCustom(ctx.username, "ERROR", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi cập nhật sản phẩm");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiContext ctx = initContext(req, resp, ResourceNames.ADMIN_API_PRODUCT_MANAGE);
        String id = extractId(req.getPathInfo());
        if (id == null) {
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Thiếu ID trong URL");
            return;
        }

        try {
            int pid = Integer.parseInt(id);
            if (productService.delete(pid)) {
                logService.logDeleteEntity(ctx.username, ResourceNames.PRODUCT, id, ctx.ip, ctx.roles);
                sendSuccess(resp, HttpServletResponse.SC_OK, "Xóa sản phẩm thành công");
            } else {
                logService.logCustom(ctx.username, "ERROR", "Delete failed, ID=" + id, ctx.ip, ctx.roles);
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy sản phẩm");
            }
        } catch (NumberFormatException e) {
            logService.logCustom(ctx.username, "ERROR", "Invalid productId: " + id, ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID không hợp lệ");
        } catch (Exception e) {
            logService.logCustom(ctx.username, "FATAL", e.getMessage(), ctx.ip, ctx.roles);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi server khi xóa sản phẩm");
        }
    }

    // Validation với thông báo chi tiết hơn
    private void validateCreate(AProduct p) {
        StringBuilder errors = new StringBuilder();
        if (p.getName() == null || p.getName().trim().isEmpty())
            errors.append("Tên sản phẩm không được để trống. ");
        if (p.getUnitPrice() <= 0)
            errors.append("Giá sản phẩm phải lớn hơn 0. ");
        if (errors.length() > 0)
            throw new IllegalArgumentException("Lỗi khi tạo sản phẩm (ID=" + p.getId() + "): "
                    + errors.toString().trim());

    }

    private void validateUpdate(AProduct p) {
        StringBuilder errors = new StringBuilder();
        if (p.getId() == null)
            errors.append("ID sản phẩm bị thiếu. ");
        if (p.getName() == null || p.getName().trim().isEmpty())
            errors.append("Tên sản phẩm không được để trống. ");
        if (p.getUnitPrice() <= 0)
            errors.append("Giá sản phẩm phải lớn hơn 0 nếu được cung cấp. ");
        if (errors.length() > 0)
            throw new IllegalArgumentException("Lỗi khi cập nhật sản phẩm (ID=" + p.getId() + "): "
                    + errors.toString().trim());
    }

}
