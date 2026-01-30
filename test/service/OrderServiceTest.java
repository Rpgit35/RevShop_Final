package service;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;
import model.Order;

public class OrderServiceTest {

    private OrderService orderService;
    private CartService cartService;

    @Before
    public void setup() {
        orderService = new OrderService();
        cartService = new CartService();
    }

    @Test
    public void testCheckoutAndOrders() {
        cartService.addToCart(1, 2);

        Scanner sc = new Scanner("Hyderabad\nHyderabad\n1\n");

        orderService.checkout(
                cartService,
                "junit_user@test.com",
                sc
        );

        assertTrue(cartService.isEmpty());

        List<Order> orders =
                orderService.myOrders("junit_user@test.com");

        assertNotNull(orders);
    }
}
