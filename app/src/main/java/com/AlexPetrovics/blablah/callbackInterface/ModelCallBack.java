package com.AlexPetrovics.blablah.callbackInterface;

import com.AlexPetrovics.blablah.model.Chat;

import java.util.ArrayList;

public interface ModelCallBack {
    void onModelUpdated(ArrayList<Chat> messages);
}
