package com.AlexPetrovics.blablah.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.AlexPetrovics.blablah.R;
import com.AlexPetrovics.blablah.model.Chat;
import com.AlexPetrovics.blablah.presenter.ChatPresenter;
import com.AlexPetrovics.blablah.utils.Utils;
import com.AlexPetrovics.blablah.views.adapter.ChatAdapter;
import com.AlexPetrovics.blablah.views.interfaces.ChatView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity implements ChatView {

    @BindView(R.id.recycler_chat)
    RecyclerView recyclerChat;
    @BindView(R.id.edit_text_message)
    EditText editTextMessage;
    @BindView(R.id.button_send_message)
    Button buttonSendMessage;
    @BindView(R.id.linear_send_chat)
    LinearLayout linearSendChat;
    private String roomName;
    private ChatPresenter chatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        roomName = getIntent().getStringExtra(Utils.EXTRA_ROOM_NAME);
        chatPresenter = new ChatPresenter(this);
        chatPresenter.setListener(roomName);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.button_send_message)
    public void onClick() {
        chatPresenter.sendMessageToFirebase(roomName, editTextMessage.getText().toString());
    }

    @Override
    public void updateList(ArrayList<Chat> chats) {
        ChatAdapter chatAdapter = new ChatAdapter(chats, this);
        recyclerChat.setAdapter(chatAdapter);
        recyclerChat.scrollToPosition(chats.size() - 1);
    }

    @Override
    public void clearEditText() {
        editTextMessage.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatPresenter.onDestroy(roomName);
    }
}
