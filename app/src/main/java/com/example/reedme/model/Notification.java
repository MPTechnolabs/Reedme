package com.example.reedme.model;

public class Notification {
    int Id;
    String Message;
    String Title;

    public Notification(int id, String title, String message) {
        this.Id = id;
        this.Title = title;
        this.Message = message;
    }

    public void setNotificationId(int id) {
        this.Id = id;
    }

    public void setNotificationTitle(String title) {
        this.Title = title;
    }

    public void setNotificationMessage(String message) {
        this.Message = message;
    }

    public int getNotificationId() {
        return this.Id;
    }

    public String getNotificationTitle() {
        return this.Title;
    }

    public String getNotificationMessage() {
        return this.Message;
    }
}
