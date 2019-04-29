package com.AlexPetrovics.blablah.callbackInterface;

import com.google.firebase.database.DataSnapshot;

public interface FirebaseCallBack {
    void onNewMessage(DataSnapshot dataSnapshot);
}
