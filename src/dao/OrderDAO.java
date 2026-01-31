package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import util.DBUtil;

public class OrderDAO {

    // ================= CREATE ORDER =================
    public void createOrder(String buyerEmail,
                            double total,
                            String shipping,
                            String billing,
                            String payment) {

        String sql = "INSERT INTO orders VALUES (orders_seq.NEXTVAL, ?, ?, ?, ?, ?, SYSDATE)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, buyerEmail);
            ps.setDouble(2, total);
            ps.setString(3, shipping);
            ps.setString(4, billing);
            ps.setString(5, payment);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= BUYER ORDERS =================
    public List<Order> getOrdersByBuyer(String email) {
        List<Order> list = new ArrayList<Order>();

        String sql = "SELECT * FROM orders WHERE buyer_email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

    // ================= SELLER ORDERS =================
    public List<Order> getOrdersForSeller(String sellerEmail) {
        List<Order> list = new ArrayList<Order>();

        String sql =
            "SELECT o.order_id, o.buyer_email, o.total_amount, o.order_date " +
            "FROM orders o " +
            "JOIN order_items oi ON o.order_id = oi.order_id " +
            "JOIN products p ON oi.product_id = p.product_id " +
            "WHERE p.seller_email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

    // ================= CANCEL ORDER =================
    public boolean cancelOrder(int orderId, String buyerEmail) {

        String sql = "DELETE FROM orders WHERE order_id = ? AND buyer_email = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setString(2, buyerEmail);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
