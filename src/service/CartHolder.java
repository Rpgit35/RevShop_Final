package service;

import java.util.HashMap;
import java.util.Map;

public class CartHolder {

    private static Map<Integer, Integer> cart = new HashMap<Integer, Integer>();

    public static void setCart(Map<Integer, Integer> c) {
        cart = c;
    }

    public static Map<Integer, Integer> getCart() {
        return cart;
    }

    public static void clear() {
        cart.clear();
    }
}
