package service;

import org.junit.Test;
import static org.junit.Assert.*;

public class OrderServiceTest {

    private OrderService service = new OrderService();

    @Test
    public void testServiceExists() {
        assertNotNull(service);
    }
}
