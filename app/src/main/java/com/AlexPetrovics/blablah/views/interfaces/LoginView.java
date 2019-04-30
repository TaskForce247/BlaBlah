package com.AlexPetrovics.blablah.views.interfaces;

public interface LoginView {
    void showToast(String message);

    void onAuthSuccess();

    void onShowRoomDialog();

    void onChangeButtonText();

    void startChatActivity(String roomName);
}
