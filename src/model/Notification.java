package model;

import java.util.Date;

public class Notification {
    private int notifId;
    private String userEmail;
    private String message;
    private Date createdAt;

    public int getNotifId() { return notifId; }
    public void setNotifId(int notifId) { this.notifId = notifId; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
