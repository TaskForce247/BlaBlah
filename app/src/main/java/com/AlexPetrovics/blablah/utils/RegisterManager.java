package com.AlexPetrovics.blablah.utils;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.AlexPetrovics.blablah.views.interfaces.RegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterManager {


    private static final String TAG = RegisterView.class.getSimpleName();
    private RegisterView.onRegistrationListener mOnRegisterView;

    public RegisterManager(RegisterView.onRegistrationListener onRegisterView){
        this.mOnRegisterView = onRegisterView;
    }

    public void performFirebaseRegistration(Activity activity, String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            mOnRegisterView.onFailure(task.getException().getMessage());
                        }else{
                            mOnRegisterView.onSuccess(task.getResult().getUser());
                        }
                    }
                });
    }
}