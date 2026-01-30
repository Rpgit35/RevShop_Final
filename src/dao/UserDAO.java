package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.User;
import util.DBUtil;

public class UserDAO {

    // ================= REGISTER =================
    public boolean registerUser(User user) {
        String sql = "INSERT INTO users VALUES (USERS_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, SYSDATE)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getSecurityQuestion());
            ps.setString(6, user.getSecurityAnswer());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= LOGIN =================
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= GET SECURITY QUESTION =================
    public String getSecurityQuestion(String email) {
        String sql = "SELECT security_question FROM users WHERE email=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("security_question");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   

   
// ================= VERIFY SECURITY ANSWER =================
public boolean verifySecurity(String email, String answer) {
    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(
                 "SELECT COUNT(*) FROM users WHERE email=? AND security_answer=?")) {

        ps.setString(1, email);
        ps.setString(2, answer);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

// ================= CHANGE PASSWORD =================
public boolean changePassword(String email, String newPass) {
    try (Connection con = DBUtil.getConnection();
         PreparedStatement ps = con.prepareStatement(
                 "UPDATE users SET password=? WHERE email=?")) {

        ps.setString(1, newPass);
        ps.setString(2, email);

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
