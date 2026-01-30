package dao;

import java.sql.*;
import java.util.*;
import model.Order;
import util.DBUtil;

public class OrderDAO {

    public void createOrder(String buyerEmail,
                            Map<Integer, Integer> cart,
                            double total,
                            String shipping,
                            String billing,
                            String payment) {

        String orderSql = "INSERT INTO orders VALUES (orders_seq.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";
        String itemSql = "INSERT INTO order_items VALUES (order_items_seq.NEXTVAL, ?, ?, ?, ?)";

        try (Connection con = DBUtil.getConnection()) {

            // Insert order
            PreparedStatement ps = con.prepareStatement(orderSql, new String[]{"order_id"});
            ps.setString(1, buyerEmail);
            ps.setDouble(2, total);
            ps.setString(3, shipping);
            ps.setString(4, billing);
            ps.setString(5, payment);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Insert order items
            PreparedStatement itemPS = con.prepareStatement(itemSql);
            for (Map.Entry<Integer, Integer> e : cart.entrySet()) {
                itemPS.setInt(1, orderId);
                itemPS.setInt(2, e.getKey());
                itemPS.setInt(3, e.getValue());
                itemPS.setDouble(4, 100); // demo price per item
                itemPS.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buyer orders
   public List<Order> getOrdersByBuyer(String email) {
    List<Order> list = new ArrayList<Order>();

    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT order_id, buyer_email, total_amount, order_date FROM orders WHERE buyer_email = ?")) {

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order o = new Order();
            o.setOrderId(rs.getInt("order_id"));
            o.setBuyerEmail(rs.getString("buyer_email"));
            o.setTotalAmount(rs.getDouble("total_amount"));
            o.setOrderDate(rs.getString("order_date"));

            list.add(o);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}


    // Seller orders
    public List<Order> getOrdersForSeller(String sellerEmail) {
    List<Order> list = new ArrayList<Order>();

    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(
            "SELECT DISTINCT o.order_id, o.buyer_email, o.total_amount, o.order_date " +
            "FROM orders o " +
            "JOIN order_items oi ON o.order_id = oi.order_id " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE p.seller_email = ?")) {

        ps.setString(1, sellerEmail);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Order o = new Order();
            o.setOrderId(rs.getInt("order_id"));
            o.setBuyerEmail(rs.getString("buyer_email"));
            o.setTotalAmount(rs.getDouble("total_amount"));
            o.setOrderDate(rs.getString("order_date"));

            list.add(o);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

}
