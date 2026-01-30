package service;

import java.util.HashMap;
import java.util.Map;

public class CartService {

    // productId -> quantity
    private Map<Integer, Integer> cart = new HashMap<>();

    // ================= ADD TO CART =================
    public void addToCart(int productId, int quantity) {
        if (cart.containsKey(productId)) {
            cart.put(productId, cart.get(productId) + quantity);
        } else {
            cart.put(productId, quantity);
        }

        // Sync with CartHolder so OrderDAO can read it
        CartHolder.setCart(cart);
    }

    // ================= REMOVE FROM CART =================
    public void removeFromCart(int productId) {
        cart.remove(productId);

        // Sync
        CartHolder.setCart(cart);
    }

    // ================= GET CART =================
    public Map<Integer, Integer> getCart() {
        return cart;
    }

    // ================= CLEAR CART =================
    public void clearCart() {
        cart.clear();

        // Sync
        CartHolder.setCart(cart);
    }

    // ================= CHECK EMPTY =================
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    // ================= VIEW CART =================
    public void viewCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty");
            return;
        }

        System.out.println("Your Cart:");
        for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
            System.out.println("Product ID: " + entry.getKey() +
                               " | Qty: " + entry.getValue());
        }
    }
}
