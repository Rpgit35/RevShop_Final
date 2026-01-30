package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Product;
import util.DBUtil;

public class ProductDAO {

    // ================= ADD PRODUCT =================
    public boolean addProduct(Product p) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO products VALUES " +
                     "(products_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, SYSDATE)")) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setDouble(3, p.getPrice());
            ps.setDouble(4, p.getMrp());
            ps.setString(5, p.getCategory());
            ps.setInt(6, p.getStock());
            ps.setString(7, p.getSellerEmail());
            ps.setInt(8, p.getStockThreshold());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= UPDATE PRODUCT =================
    public boolean updateProduct(int id, double price, int stock) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE products SET price=?, stock=? WHERE product_id=?")) {

            ps.setDouble(1, price);
            ps.setInt(2, stock);
            ps.setInt(3, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE PRODUCT =================
    public boolean deleteProduct(int id) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM products WHERE product_id=?")) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= GET ALL PRODUCTS =================
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<Product>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM products")) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= SEARCH =================
    public List<Product> search(String keyword) {
        List<Product> list = new ArrayList<Product>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM products WHERE LOWER(name) LIKE ? OR LOWER(description) LIKE ?")) {

            String key = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, key);
            ps.setString(2, key);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= CATEGORY =================
    public List<Product> byCategory(String category) {
        List<Product> list = new ArrayList<Product>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM products WHERE LOWER(category)=?")) {

            ps.setString(1, category.toLowerCase());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= MAPPER =================
    private Product map(ResultSet rs) throws Exception {
        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setMrp(rs.getDouble("mrp"));
        p.setCategory(rs.getString("category"));
        p.setStock(rs.getInt("stock"));
        p.setSellerEmail(rs.getString("seller_email"));
        p.setStockThreshold(rs.getInt("stock_threshold"));
        return p;
    }
}
