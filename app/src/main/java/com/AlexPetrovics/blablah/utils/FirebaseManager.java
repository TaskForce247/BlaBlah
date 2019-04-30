package com.AlexPetrovics.blablah.utils;

import com.AlexPetrovics.blablah.callbackInterface.FirebaseCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseManager implements ChildEventListener {
    private volatile static FirebaseManager firebaseManager;
    private DatabaseReference databaseReference;
    private FirebaseCallBack firebaseCallBack;

    public static synchronized FirebaseManager getInstance(String roomName, FirebaseCallBack firebaseCallBack) {
        if (firebaseManager == null) {
            synchronized (FirebaseManager.class) {
                firebaseManager = new FirebaseManager(roomName, firebaseCallBack);
            }
        }
        return firebaseManager;
    }

    public FirebaseManager(String roomName, FirebaseCallBack firebaseCallBack) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(roomName);
        this.firebaseCallBack = firebaseCallBack;
    }

    public void addMessageListeners() {
        databaseReference.addChildEventListener(this);
    }

    public void sendMessageToFirebase(String message) { //actual message sending part
        Map<String, Object> map = new HashMap<>();
        map.put("text", message);
        map.put("time", System.currentTimeMillis());
        map.put("senderId", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        String keyToPush = databaseReference.push().getKey();
        databaseReference.child(keyToPush).setValue(map);
    }
    public void removeListener() {
        databaseReference.removeEventListener(this);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        firebaseCallBack.onNewMessage(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }



    public void destroy() {
        firebaseManager = null;
        firebaseCallBack = null;
    }
}