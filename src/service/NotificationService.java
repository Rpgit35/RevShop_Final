package service;

import java.util.List;
import dao.NotificationDAO;

public class NotificationService {

    private NotificationDAO dao = new NotificationDAO();

    public void notify(String email, String msg) {
        dao.add(email, msg);
    }

    public List<String> myNotifications(String email) {
        return dao.getNotifications(email);
    }
}
