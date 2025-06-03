package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.controller;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "productPagination", value = "/productPagination")
public class ProductPagination extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 8;
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Product> products = (List<Product>) request.getAttribute("allProducts");
        ProductService productService = new ProductService();

        if (products == null) {
            products = new ArrayList<>(); // Khởi tạo danh sách trống nếu không có sản phẩm
        }
        int totalProducts = products.size();
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        products = productService.getProductsByPagination(page, pageSize, products);

        request.setAttribute("allProducts", products);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);

        String category = (String) request.getAttribute("category");
        String path = "/".concat(category.toLowerCase()).concat(".jsp");

        request.getRequestDispatcher(path).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đọc dữ liệu JSON từ request
        BufferedReader reader = request.getReader();
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        // Tạo DateFormat tùy chỉnh để xử lý định dạng ngày "Dec 15, 2024"
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

        // Tạo Gson với DateTypeAdapter tuỳ chỉnh
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateTypeAdapter(dateFormat))
                .create();

        // Xử lý đối tượng JSON thành một đối tượng chứa các tham số cần thiết
        JsonObject jsonObject = JsonParser.parseString(jsonBuilder.toString()).getAsJsonObject();

        String category = jsonObject.get("category").getAsString();
        int page = jsonObject.get("page").getAsInt();
        int pageSize = jsonObject.get("pageSize").getAsInt();

        // Chuyển đổi danh sách sản phẩm từ JSON
        Type listType = new TypeToken<List<Product>>() {}.getType();
        List<Product> products = gson.fromJson(jsonObject.get("products"), listType);

        // Kiểm tra nếu danh sách sản phẩm là null hoặc trống
        if (products == null || products.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Invalid or empty product list\"}");
            return;
        }

        request.setAttribute("allProducts", products);
        request.setAttribute("category", category);
        request.setAttribute("page", page);
        request.setAttribute("pageSize", pageSize);
        doGet(request, response);
    }

    public class DateTypeAdapter extends TypeAdapter<Date> {

        private final DateFormat dateFormat;

        public DateTypeAdapter(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            out.value(dateFormat.format(value));  // Chuyển đối tượng Date thành chuỗi
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            try {
                return dateFormat.parse(in.nextString());  // Phân tích cú pháp chuỗi thành Date
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }
}