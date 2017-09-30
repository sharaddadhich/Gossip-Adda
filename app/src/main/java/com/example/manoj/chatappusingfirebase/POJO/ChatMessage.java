package com.example.manoj.chatappusingfirebase.POJO;

/**
 * Created by sharaddadhich on 21/09/17.
 */

public class ChatMessage {

    String message,picurl;

    public ChatMessage() {
    }

    public ChatMessage(String message, String picurl) {
        this.message = message;
        this.picurl = picurl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
}

