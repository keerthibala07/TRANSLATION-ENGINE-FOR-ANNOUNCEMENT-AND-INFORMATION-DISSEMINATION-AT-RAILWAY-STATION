package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRVadapter extends RecyclerView.Adapter {
    private ArrayList<ChatModal> chatModalArrayList;
    private Context context;
    public ChatRVadapter(ArrayList<ChatModal> chatModalArrayList, Context context) {
        this.chatModalArrayList = chatModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch(viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_msg_rv_item,parent,false);
                return new UserViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_msg_rv_item,parent,false);
                return new BotviewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModal chatModal = chatModalArrayList.get(position);
        switch (chatModal.getSender()){
            case "user":
                ((UserViewHolder)holder).userTV.setText(chatModal.getMessage());
                break;
            case "bot":
                ((BotviewHolder)holder).botMsgTV.setText(chatModal.getMessage());
                break;
        }
    }
    public int getItemViewType(int position){
        switch (chatModalArrayList.get(position).getSender()){
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }
    @Override
    public int getItemCount() {
        return chatModalArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        TextView userTV;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userTV = itemView.findViewById(R.id.idTVUser);

        }
    }

    public static class BotviewHolder extends RecyclerView.ViewHolder{
        TextView botMsgTV;
        public BotviewHolder(@NonNull View itemView) {
            super(itemView);
            botMsgTV = itemView.findViewById(R.id.idTVBot);
        }

    }
}
