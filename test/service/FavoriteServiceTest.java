package service;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;

public class FavoriteServiceTest {

    private FavoriteService service;

    @Before
    public void setup() {
        service = new FavoriteService();
    }

    @Test
    public void testAddAndGetFavorites() {
        service.add(
                "junit_user@test.com",
                1
        );

        List<Integer> favs =
                service.getFavorites("junit_user@test.com");

        assertNotNull(favs);
    }
}
