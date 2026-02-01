package service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CartServiceTest {

    private CartService cart;

    @Before
    public void setUp() {
        cart = new CartService();
    }

    @Test
    public void testAddToCart() {
        cart.addToCart(1, 2);

        assertFalse(cart.isEmpty());
        assertEquals(2, (int) cart.getCart().get(1));
    }

    @Test
    public void testRemoveFromCart() {
        cart.addToCart(1, 2);
        cart.removeFromCart(1);

        assertTrue(cart.isEmpty());
    }

    @Test
    public void testClearCart() {
        cart.addToCart(1, 2);
        cart.addToCart(2, 3);

        cart.clearCart();

        assertTrue(cart.isEmpty());
    }

    @Test
    public void testUpdateQuantity() {
        cart.addToCart(1, 1);
        cart.addToCart(1, 2); // should increase quantity

        assertEquals(3, (int) cart.getCart().get(1));
    }
}
