package no.kristiania.chatapp.db;

import java.sql.Timestamp;

public class Message {
    private long id;
    private long senderId;
    private long groupId;
    private String message;
    private Timestamp dateTimeSent;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateTimeSent() {
        return dateTimeSent;
    }

    public void setDateTimeSent(Timestamp dateTimeSent) {
        this.dateTimeSent = dateTimeSent;
    }
}
