package service;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import model.Product;

public class FavoriteServiceTest {

    private FavoriteService service = new FavoriteService();

    @Test
    public void testAddFavorite() {
        boolean result = service.add("junit_user@test.com", 1);
        assertTrue(result);
    }

    @Test
    public void testGetFavorites() {
        List<Product> list = service.getFavorites("junit_user@test.com");
        assertNotNull(list);
    }
}
