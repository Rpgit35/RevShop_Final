package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import util.DBUtil;

public class FavoriteDAO {

    // ================= ADD FAVORITE =================
    public boolean addFavorite(String email, int productId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO favorites VALUES (favorites_seq.NEXTVAL, ?, ?)")) {

            ps.setString(1, email);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= REMOVE FAVORITE =================
    public boolean removeFavorite(String email, int productId) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM favorites WHERE buyer_email=? AND product_id=?")) {

            ps.setString(1, email);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= GET FAVORITES =================
    public List<Product> getFavorites(String email) {
        List<Product> list = new ArrayList<Product>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT p.* FROM products p " +
                     "JOIN favorites f ON p.product_id = f.product_id " +
                     "WHERE f.buyer_email=?")) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setMrp(rs.getDouble("mrp"));
                p.setCategory(rs.getString("category"));
                p.setStock(rs.getInt("stock"));
                p.setSellerEmail(rs.getString("seller_email"));

                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
