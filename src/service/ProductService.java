package service;

import java.util.List;
import dao.ProductDAO;
import model.Product;

public class ProductService {

    private ProductDAO dao = new ProductDAO();

    public boolean addProduct(Product p) {
        return dao.addProduct(p);
    }

    public boolean updateProduct(int id, double price, int stock) {
        return dao.updateProduct(id, price, stock);
    }

    public boolean deleteProduct(int id) {
        return dao.deleteProduct(id);
    }

    public List<Product> getAllProducts() {
        return dao.getAllProducts();
    }

    // ✅ ADD THIS
    public List<Product> search(String keyword) {
        return dao.search(keyword);
    }

    public List<Product> byCategory(String category) {
        return dao.byCategory(category);
    }
}
