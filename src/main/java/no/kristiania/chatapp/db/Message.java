package no.kristiania.chatapp.db;

public class Message {
    private int id;
    private int senderId;
    private int groupId;
    private String message;
    private String dateTimeSent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(String dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }
}
