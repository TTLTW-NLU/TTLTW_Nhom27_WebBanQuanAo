package vn.edu.hcmuaf.fit.webbanquanao.webpage.product.service;

import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.dao.ProductDAO;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.Product;
import vn.edu.hcmuaf.fit.webbanquanao.webpage.product.model.ProductDetail;

import java.util.*;

public class ProductService {
    ProductDAO dao;

    public ProductService() {
        dao = new ProductDAO();
    }

    // Get all products
    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    // Get sale products
    public List<Product> getSaleProducts() {
        return dao.getSaleProducts();
    }

    // Get best-selling products
    public List<Product> getBestSellingProducts() {
        return dao.getBestSellingProducts();
    }

    // Get products by category
    public List<Product> getProductsByCategory(String category) {
        category = transformCategory(category);
        return dao.getProductsByCategory(category);
    }

    // Get filtered products
    public List<Product> getFilteredProducts(String category, String[] type, String[] size, Double priceMin, Double priceMax, String sortPrice) {
        category = transformCategory(category);
        List<Product> list = dao.getFilteredProducts(category, type, size, priceMin, priceMax);
        if("asc".equals(sortPrice)){
            list.sort(Comparator.comparingDouble(Product::getUnitPrice));
        } else if("desc".equals(sortPrice)){
            list.sort(Comparator.comparingDouble(Product::getUnitPrice).reversed());
        }
        return list;
    }

    // Transform category
    public String transformCategory(String category) {
        return category = switch (category) {
            case "men" -> "Nam";
            case "women" -> "Nữ";
            case "children" -> "Trẻ em";
            default -> category;
        };
    }

    // Search product by name
    public List<Product> searchByName(String name) {
        return dao.searchByName(name);
    }

    // Get products by Pagination
    public List<Product> getProductsByPagination(int page, int pageSize, List<Product> products) {
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, products.size());
        if (start >= products.size()) {
            return new ArrayList<>();
        }
        return products.subList(start, end);
    }

    // Get product by id
    public Product getDetail(int id){
        return dao.getById(id);
    }

    // Get product by productDetailId
    public Product getProductByProductDetailId(int productDetailId) {
        ProductDetail detail = dao.getProductDetailById(productDetailId);
        if (detail == null) return null;

        Product product = dao.getProductById(detail.getProductId());
        if (product == null) return null;

        List<ProductDetail> details = dao.getProductDetailsByProductId(detail.getProductId());
        product.setDetails(details);

        return product;
    }

    // Get ProductDetail by productDetailId
    public ProductDetail getProductDetailByProductId(int productDetailId) {
        return dao.getProductDetailById(productDetailId);
    }

    public static void main(String[] args) {
        ProductService service = new ProductService();
//        System.out.println(service.getFilteredProducts("Nam", new String[]{"Quần"}, new String[]{"M","L"}, null, null, "desc"));
//        System.out.println(service.searchByName("quan tay"));
//        System.out.println(service.getProductsByPagination(1, 4, service.getAllProducts()));
//        System.out.println(service.getProductByProductDetailId(101));
    }
}
