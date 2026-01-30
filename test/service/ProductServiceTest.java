package service;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;
import model.Product;

public class ProductServiceTest {

    private ProductService service;

    @Before
    public void setup() {
        service = new ProductService();
    }

    @Test
    public void testAddProduct() {
        Product p = new Product();
        p.setName("JUnit Phone");
        p.setDescription("Test phone");
        p.setPrice(20000);
        p.setMrp(25000);
        p.setCategory("mobiles");
        p.setStock(5);
        p.setStockThreshold(2);
        p.setSellerEmail("seller@test.com");

        service.addProduct(p);

        List<Product> list = service.search("JUnit");
        assertTrue(list.size() > 0);
    }

    @Test
    public void testByCategory() {
        List<Product> list = service.byCategory("mobiles");
        assertNotNull(list);
    }
}
