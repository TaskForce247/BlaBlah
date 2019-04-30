package com.AlexPetrovics.blablah.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.AlexPetrovics.blablah.R;
import com.AlexPetrovics.blablah.model.Chat;
import com.AlexPetrovics.blablah.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<Chat> chats;
    private Context context;

    public ChatAdapter(ArrayList<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (chats.get(position).getSenderId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            holder.layoutMessageLeft.setVisibility(View.GONE);
            holder.layoutMessageRight.setVisibility(View.VISIBLE);
            holder.textMessageRight.setText(chats.get(position).getMessage());
            holder.textTimeMessagesRight.setText(Utils.convertTime(chats.get(position).getTimeStamp()));
        } else {
            holder.layoutMessageLeft.setVisibility(View.VISIBLE);
            holder.layoutMessageRight.setVisibility(View.GONE);
            holder.textMessageLeft.setText(chats.get(position).getMessage());
            holder.textTimeMessagesLeft.setText(Utils.convertTime(chats.get(position).getTimeStamp()));
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_message_left)
        TextView textMessageLeft;
        @BindView(R.id.text_time_messages_left)
        TextView textTimeMessagesLeft;
        @BindView(R.id.layout_message_left)
        LinearLayout layoutMessageLeft;
        @BindView(R.id.text_time_messages_right)
        TextView textTimeMessagesRight;
        @BindView(R.id.text_message_right)
        TextView textMessageRight;
        @BindView(R.id.layout_message_right)
        LinearLayout layoutMessageRight;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}