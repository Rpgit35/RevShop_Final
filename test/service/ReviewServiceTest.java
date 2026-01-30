package service;

import org.junit.*;
import static org.junit.Assert.*;

public class ReviewServiceTest {

    private ReviewService service;

    @Before
    public void setup() {
        service = new ReviewService();
    }

    @Test
    public void testAddReview() {
        service.addReview(
                1,
                "junit_user@test.com",
                5,
                "JUnit review test"
        );

        // If no exception, test passes
        assertTrue(true);
    }
}
