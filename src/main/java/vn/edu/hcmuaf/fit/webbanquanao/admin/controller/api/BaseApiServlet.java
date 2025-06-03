package vn.edu.hcmuaf.fit.webbanquanao.admin.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(java.sql.Date.class, new SqlDateAdapter())
            .create();

    protected void prepareResponse(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    protected String extractId(String pathInfo) {
        if (pathInfo == null || "/".equals(pathInfo)) {
            return null;
        }
        return pathInfo.substring(1);
    }

    protected String readBody(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    protected <T> void writeJson(HttpServletResponse resp, T data) throws IOException {
        sendJson(resp, HttpServletResponse.SC_OK, "Thành công", data);
    }

    protected void sendJson(HttpServletResponse resp, int status, String message, Object data) throws IOException {
        resp.setStatus(status);
        // Sử dụng Map cho phép giá trị null mà không gây NPE
        Map<String, Object> responseWrapper = new HashMap<>();
        responseWrapper.put("status", status);
        responseWrapper.put("message", message);
        responseWrapper.put("data", data);
        resp.getWriter().write(gson.toJson(responseWrapper));
    }

    protected void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        sendJson(resp, status, message, null);
    }

    protected void sendSuccess(HttpServletResponse resp, int status, String message) throws IOException {
        sendJson(resp, status, message, null);
    }

    protected ApiContext initContext(HttpServletRequest req, HttpServletResponse resp, String resource) {
        prepareResponse(resp);
        return new ApiContext(req, resource);
    }
}