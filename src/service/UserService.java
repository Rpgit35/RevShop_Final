package service;

import dao.UserDAO;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
s

public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserDAO dao = new UserDAO();

    // ================= EMAIL VALIDATION =================
    public boolean isValidGmail(String email) {
        if (email == null) return false;
        return email.toLowerCase().endsWith("@gmail.com");
    }

    // ================= REGISTER =================
    public boolean registerUser(User u) {
        return dao.registerUser(u);
    }

    // Wrapper for controller
    public boolean register(User user) {
        return dao.registerUser(user);
    }

    // ================= LOGIN =================
    public User login(String email, String pass) {
        return dao.login(email, pass);
    }

    // ================= SECURITY QUESTION =================
    public String getSecurityQuestion(String email) {
        return dao.getSecurityQuestion(email);
    }

    // ================= VERIFY ANSWER =================
    public boolean verifySecurity(String email, String answer) {
        return dao.verifySecurity(email, answer);
    }

    // ================= CHANGE PASSWORD =================
    public boolean changePassword(String email, String newPass) {
        return dao.changePassword(email, newPass);
    }

    // ================= FORGOT PASSWORD =================
    public boolean forgotPassword(String email, String answer, String newPass) {
        if (verifySecurity(email, answer)) {
            return changePassword(email, newPass);
        }
        return false;
    }

    // Controller-friendly wrapper
    public boolean resetPassword(String email, String answer, String newPass) {
        if (dao.verifySecurity(email, answer)) {
            return dao.changePassword(email, newPass);
        }
        return false;
    }
}
