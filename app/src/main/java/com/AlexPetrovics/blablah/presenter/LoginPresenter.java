package com.AlexPetrovics.blablah.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.AlexPetrovics.blablah.views.interfaces.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void firebaseAnonymousAuth() {
        loginView.onChangeButtonText();
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    loginView.showToast("Authentication Failed");
                } else {
                    loginView.onAuthSuccess();
                }
            }
        });
    }
    public void login(Activity activity, String email, String password) {
        performFirebaseLogin(activity, email, password);

    }
    public void performFirebaseLogin(Activity activity, String email, String password) {
        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            loginView.showToast("Authentication Failed");
                        } else {
                            loginView.onAuthSuccess();
                        }
                    }
                });
    }

    public void invalidateRoom(String roomName) {
        if (roomName.trim().isEmpty()) {
            loginView.showToast("Enter a valid room name!");
        } else {
            loginView.startChatActivity(roomName);
        }
    }

    public void showRoomDialogInActivity() {
        loginView.onShowRoomDialog();
    }

}
