package com.AlexPetrovics.blablah.model;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class Chat {
    private String msgKey;
    private long timeStamp;
    private String message;
    private String senderId;

    public Chat(DataSnapshot dataSnapshot) {
        HashMap<String, Object> object = (HashMap<String, Object>) dataSnapshot.getValue();
        this.msgKey = dataSnapshot.getKey();
        this.message = object.get("text").toString();
        this.senderId = object.get("senderId").toString();
        this.timeStamp = Long.parseLong(object.get("time").toString());
    }


    public long getTimeStamp() {
        return timeStamp;
    }



    public String getMessage() {
        return message;
    }



    public String getSenderId() {
        return senderId;
    }

}