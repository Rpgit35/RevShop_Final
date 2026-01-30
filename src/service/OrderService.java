package service;

import java.util.List;
import java.util.Scanner;
import dao.OrderDAO;
import model.Order;

public class OrderService {

    private OrderDAO dao = new OrderDAO();

    public void checkout(CartService cart,
                          String buyerEmail,
                          Scanner sc) {

        if (cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }

        double total = 0;
        for (int qty : cart.getCart().values()) {
            total += qty * 100;
        }

        System.out.print("Shipping Address: ");
        String shipping = sc.nextLine();

        System.out.print("Billing Address: ");
        String billing = sc.nextLine();

        System.out.println("Payment: 1.COD 2.UPI 3.CARD");
        int ch = Integer.parseInt(sc.nextLine());

        String pay = ch == 1 ? "COD" : ch == 2 ? "UPI" : "CARD";

        dao.createOrder(buyerEmail,
                cart.getCart(),
                total,
                shipping,
                billing,
                pay);

        cart.clearCart();

        System.out.println("Order placed! Rs." + total + " | " + pay);
    }

    public List<Order> myOrders(String email) {
        return dao.getOrdersByBuyer(email);
    }

    public List<Order> sellerOrders(String sellerEmail) {
        return dao.getOrdersForSeller(sellerEmail);
    }
}
