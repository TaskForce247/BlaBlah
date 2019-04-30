package com.AlexPetrovics.blablah.views.interfaces;

import com.AlexPetrovics.blablah.model.Chat;

import java.util.ArrayList;

public interface ChatView {
    void updateList(ArrayList<Chat> chats);

    void clearEditText();
}
