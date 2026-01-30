package dao;

import java.sql.*;
import java.util.*;
import util.DBUtil;

public class NotificationDAO {

    public void add(String email, String msg) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO notifications VALUES (notifications_seq.NEXTVAL, ?, ?, SYSDATE)")) {

            ps.setString(1, email);
            ps.setString(2, msg);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getNotifications(String email) {
        List<String> list = new ArrayList<String>();

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT message FROM notifications WHERE user_email=?")) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getString("message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
