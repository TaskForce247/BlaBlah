package com.AlexPetrovics.blablah.presenter;

import com.AlexPetrovics.blablah.callbackInterface.FirebaseCallBack;
import com.AlexPetrovics.blablah.callbackInterface.ModelCallBack;
import com.AlexPetrovics.blablah.model.Chat;
import com.AlexPetrovics.blablah.model.Message;
import com.AlexPetrovics.blablah.utils.FirebaseManager;
import com.AlexPetrovics.blablah.views.interfaces.ChatView;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ChatPresenter implements FirebaseCallBack, ModelCallBack {
    private ChatView chatView;
    private Message message;

    public ChatPresenter(ChatView chatView) {
        this.chatView = chatView;
        this.message = new Message();
    }

    public void sendMessageToFirebase(String roomName, String message) {
        if (!message.trim().equals("")) {
            FirebaseManager.getInstance(roomName, this).sendMessageToFirebase(message);
        }
        chatView.clearEditText();
    }

    @Override
    public void onNewMessage(DataSnapshot dataSnapshot) {
        message.addMessages(dataSnapshot, this);
    }

    @Override
    public void onModelUpdated(ArrayList<Chat> messages) {
        if (messages.size() > 0) {
            chatView.updateList(messages);
        }
    }
    public void setListener(String roomname) {
        FirebaseManager.getInstance(roomname, this).addMessageListeners();
    }

    public void onDestroy(String roomName) {
        FirebaseManager.getInstance(roomName, this).removeListener();
        FirebaseManager.getInstance(roomName, this).destroy();
        chatView = null;
    }



}
