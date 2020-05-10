package com.barkov.ais.cvgram.entity;

import java.util.Date;

public class MessageThread {

    private int id;
    private int sendFrom;
    private int sendTo;
    private String senderName;
    private String recieverName;
    private int counter;
    private Date lastDate;

    public MessageThread()
    {

    }

    public MessageThread(int sendFrom, int sendTo, String senderName, String recieverName, int counter, Date lastDate) {
        this.sendFrom = sendFrom;
        this.sendTo = sendTo;
        this.senderName = senderName;
        this.recieverName = recieverName;
        this.counter = counter;
        this.lastDate = lastDate;
    }

    @Override
    public String toString() {
        return "MessageThread{" +
                "id=" + id +
                ", sendFrom=" + sendFrom +
                ", sendTo=" + sendTo +
                ", senderName='" + senderName + '\'' +
                ", recieverName='" + recieverName + '\'' +
                ", counter=" + counter +
                ", lastDate=" + lastDate +
                '}';
    }

    public int getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(int sendFrom) {
        this.sendFrom = sendFrom;
    }

    public int getSendTo() {
        return sendTo;
    }

    public void setSendTo(int sendTo) {
        this.sendTo = sendTo;
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

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
