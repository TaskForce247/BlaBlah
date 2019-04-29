package com.AlexPetrovics.blablah.model;

import com.AlexPetrovics.blablah.callbackInterface.ModelCallBack;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class Message {
    private ArrayList<Chat> chats;

    public void addMessages(DataSnapshot dataSnapshot, ModelCallBack modelCallBack) {
        if (chats == null) {
            chats = new ArrayList<>();
        }
        Chat chat = new Chat(dataSnapshot);
        chats.add(chat);
        modelCallBack.onModelUpdated(chats);
    }
}
