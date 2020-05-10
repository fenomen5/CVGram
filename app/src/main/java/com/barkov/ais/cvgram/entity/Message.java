package com.barkov.ais.cvgram.entity;

import java.util.Date;

public class Message {

    private int id;
    private String senderName;
    private String recieverName;
    private String message;
    private Date sendAt;

    public Message()
    {

    }

    public Message(String senderName, String recieverName, String message, Date sendAt) {
        this.senderName = senderName;
        this.recieverName = recieverName;
        this.message = message;
        this.sendAt = sendAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderName='" + senderName + '\'' +
                ", recieverName='" + recieverName + '\'' +
                ", message='" + message + '\'' +
                ", sendAt=" + sendAt +
                '}';
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendAt() {
        return sendAt;
    }

    public void setSendAt(Date sendAt) {
        this.sendAt = sendAt;
    }
}
