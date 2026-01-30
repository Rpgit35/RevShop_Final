package service;

import dao.UserDAO;
import model.User;

public class UserService {

    private UserDAO dao = new UserDAO();

    public boolean registerUser(User u) {
        return dao.registerUser(u);
    }

    public User login(String email, String pass) {
        return dao.login(email, pass);
    }

    // ================= FORGOT PASSWORD =================
    public boolean verifySecurity(String email, String answer) {
        return dao.verifySecurity(email, answer);
    }

    public boolean changePassword(String email, String newPass) {
        return dao.changePassword(email, newPass);
    }

    public boolean forgotPassword(String email, String answer, String newPass) {
        if (verifySecurity(email, answer)) {
            return changePassword(email, newPass);
        }
        return false;
    }
// ================= CONTROLLER WRAPPERS =================

public boolean register(model.User user) {
    return dao.registerUser(user);
}

public String getSecurityQuestion(String email) {
    return dao.getSecurityQuestion(email);
}

public boolean resetPassword(String email, String answer, String newPass) {
    if (dao.verifySecurity(email, answer)) {
        return dao.changePassword(email, newPass);
    }
    return false;
}

}
