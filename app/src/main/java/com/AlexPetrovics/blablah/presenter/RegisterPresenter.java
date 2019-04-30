package com.AlexPetrovics.blablah.presenter;

import android.app.Activity;

import com.AlexPetrovics.blablah.utils.RegisterManager;
import com.AlexPetrovics.blablah.views.interfaces.RegisterView;
import com.google.firebase.auth.FirebaseUser;

public class RegisterPresenter implements RegisterView, RegisterView.onRegistrationListener {
    private RegisterView.View mRegisterView;
    private RegisterManager mRegisterManager;

    public RegisterPresenter(RegisterView.View registerView){
        this.mRegisterView = registerView;
        mRegisterManager = new RegisterManager(this);
    }


    public void register(Activity activity, String email, String password) {
        mRegisterManager.performFirebaseRegistration(activity,email,password);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        mRegisterView.onRegistrationSuccess(firebaseUser);
    }

    @Override
    public void onFailure(String message) {
        mRegisterView.onRegistrationFailure(message);


    }

}