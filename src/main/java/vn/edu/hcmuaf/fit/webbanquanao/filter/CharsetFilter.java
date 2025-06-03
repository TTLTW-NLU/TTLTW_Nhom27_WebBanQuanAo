//package vn.edu.hcmuaf.fit.webbanquanao.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class CharsetFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig config) throws ServletException {
//        // Khởi tạo (nếu cần)
//    }
//
//    @Override
//    public void destroy() {
//        // Hủy filter (nếu cần)
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        // 1. Thiết lập encoding TRƯỚC KHI xử lý request
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        httpRequest.setCharacterEncoding("UTF-8");
//        httpResponse.setCharacterEncoding("UTF-8");
//        httpResponse.setContentType("application/json; charset=UTF-8");
//
//        // 2. Cho phép request đi tiếp (chỉ gọi MỘT LẦN)
//        chain.doFilter(httpRequest, httpResponse);
//    }
//}