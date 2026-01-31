package service;

import java.util.List;
import java.util.Scanner;

import dao.OrderDAO;
import model.Order;

public class OrderService {

    private OrderDAO dao = new OrderDAO();

    // ================= CHECKOUT =================
    public void checkout(CartService cart,
                          String buyerEmail,
                          Scanner sc) {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Cannot place order.");
            return;
        }

        double total = 0;

        for (int qty : cart.getCart().values()) {
            total += qty * 100;
        }

        System.out.print("Enter Shipping Address: ");
        String shipping = sc.nextLine();

        System.out.print("Enter Billing Address: ");
        String billing = sc.nextLine();

        System.out.println("Choose Payment:");
        System.out.println("1. COD");
        System.out.println("2. UPI");
        System.out.println("3. CARD");
        System.out.print("Select: ");

        int choice = Integer.parseInt(sc.nextLine());
        String paymentMethod = "COD";

        if (choice == 2) paymentMethod = "UPI";
        else if (choice == 3) paymentMethod = "CARD";

        dao.createOrder(buyerEmail, total, shipping, billing, paymentMethod);

        cart.clearCart();

        System.out.println("Order placed successfully.");
        System.out.println("Total Paid: Rs." + total);
        System.out.println("Payment Method: " + paymentMethod);
    }

    // ================= BUYER ORDERS =================
    public List<Order> myOrders(String email) {
        return dao.getOrdersByBuyer(email);
    }

    // ================= SELLER ORDERS =================
    public List<Order> sellerOrders(String sellerEmail) {
        return dao.getOrdersForSeller(sellerEmail);
    }

    // ================= CANCEL ORDER =================
    public boolean cancelOrder(int orderId, String buyerEmail) {
        return dao.cancelOrder(orderId, buyerEmail);
    }
}
