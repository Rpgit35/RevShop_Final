package service;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import model.Product;

public class ProductServiceTest {

    private ProductService service = new ProductService();

    @Test
    public void testGetAllProducts() {
        List<Product> list = service.getAllProducts();
        assertNotNull(list);
    }

    @Test
    public void testSearch() {
        List<Product> list = service.search("Laptop");
        assertNotNull(list);
    }
}
