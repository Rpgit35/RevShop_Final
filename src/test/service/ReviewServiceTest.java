package service;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReviewServiceTest {

    private ReviewService service = new ReviewService();

    @Test
    public void testAddReview() {
        boolean result = service.addReview(1, "junit_user@test.com", 5, "Good product");
        assertTrue(result);
    }
}
