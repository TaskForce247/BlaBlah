package com.AlexPetrovics.blablah.views.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.AlexPetrovics.blablah.R;
import com.AlexPetrovics.blablah.presenter.LoginPresenter;
import com.AlexPetrovics.blablah.utils.Utils;
import com.AlexPetrovics.blablah.views.interfaces.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.button_auth)
    Button buttonAuth;
    @BindView(R.id.button_register)
    Button buttonRegister;
    @BindView(R.id.email_login)
    EditText editMail;
    @BindView(R.id.password_login)
    EditText editPass;
    @BindView(R.id.text_status_auth)
    TextView textStatusAuth;

    @BindView(R.id.button_enter_room)
    Button buttonEnterRoom;
    @BindView(R.id.linear_button_room)
    LinearLayout linearButtonRoom;


    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @OnClick({R.id.button_auth, R.id.button_enter_room,R.id.button_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_auth:
                checkLoginDetails();
                break;

            case R.id.button_enter_room:
                loginPresenter.showRoomDialogInActivity();
                break;
            case R.id.button_register:
                moveToRegisterActivity();
                break;
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthSuccess() {
        buttonAuth.setVisibility(View.GONE);
        textStatusAuth.setVisibility(View.VISIBLE);
        linearButtonRoom.setVisibility(View.VISIBLE);
        buttonRegister.setVisibility(View.GONE);
        editMail.setVisibility(View.GONE);
        editPass.setVisibility(View.GONE);
    }

    @Override
    public void onShowRoomDialog() {

        Intent intent = new Intent(LoginActivity.this, QrActivity.class);
        startActivityForResult(intent, 1);

    }


    @Override
    public void onChangeButtonText() {
        buttonAuth.setText(R.string.auth_process_text);
    }

    @Override
    public void startChatActivity(String roomName) {

        Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
        intent.putExtra(Utils.EXTRA_ROOM_NAME, roomName);
        startActivity(intent);
    }
    private void checkLoginDetails() {
        if(!TextUtils.isEmpty(editMail.getText().toString()) && !TextUtils.isEmpty(editPass.getText().toString())){
            initLogin(editMail.getText().toString(), editPass.getText().toString());
        }else{
            if(TextUtils.isEmpty(editMail.getText().toString())){
                editMail.setError("Please enter a valid email");
            }if(TextUtils.isEmpty(editPass.getText().toString())){
                editPass.setError("Please enter password");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                loginPresenter.invalidateRoom(result);

            }
            if (resultCode == RESULT_CANCELED) {
            }
        }}
    private void moveToRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }


    private void initLogin(String email, String password) {

        loginPresenter.login(this, email, password);
    }
}
