package com.example.myapplication;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFAB;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private ArrayList<ChatModal>chatModalArrayList;
    private ChatRVadapter chatRvadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdtmessage);
        sendMsgFAB = findViewById(R.id.idFABSend);
        chatModalArrayList = new ArrayList<>();
        chatRvadapter = new ChatRVadapter(chatModalArrayList,this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRvadapter);

        sendMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMsgEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter the message",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    getResponse(userMsgEdt.getText().toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                userMsgEdt.setText("");
            }
        });
    }
    private void getResponse(String message) throws InterruptedException {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ira");
        chatModalArrayList.add(new ChatModal(message,USER_KEY));
        chatRvadapter.notifyDataSetChanged();

        int len = message.length();
        boolean isDigits = message.matches("\\d+");
        if(len<=5 && isDigits){
        databaseReference.child(message).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    String cur = "Please enter the Train number to show the announcement!";
//                    chatModalArrayList.add(new ChatModal(cur,BOT_KEY));
//                    chatRvadapter.notifyDataSetChanged();


//                        String langch = "Enter the preffered language...tamil,e";
//                        chatModalArrayList.add(new ChatModal(langch,BOT_KEY));

                    chatRvadapter.notifyDataSetChanged();
                    // Assuming that "message" is a user input and child nodes are languages
                    for (DataSnapshot languageSnapshot : dataSnapshot.getChildren()) {
                        String language = languageSnapshot.getKey();
                        String response = languageSnapshot.getValue(String.class);
                        String fullResponse = language + ": " + response;
                        chatModalArrayList.add(new ChatModal(fullResponse, BOT_KEY));}

                    chatRvadapter.notifyDataSetChanged();

                }
                else{
                    String cur = "Project is still under developement so stay tuned for update";
                    chatModalArrayList.add(new ChatModal(cur,BOT_KEY));
                    chatRvadapter.notifyDataSetChanged();
                    // Handle the case when no response is found for the user's message
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });}
        else if(message.toLowerCase().equals("hi")){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String tname = "The current date and time is "+formatter.format(date);
            String tname2 = "Hi our Project is under-development so wait patiently!!!";
            chatModalArrayList.add(new ChatModal(tname2,BOT_KEY));
            chatModalArrayList.add(new ChatModal(tname,BOT_KEY));
        }
        else{


            String tname = "Hi how are you?";
            String tname2 = "Project is underdevelopment so wait patiently!!!";
            chatModalArrayList.add(new ChatModal(tname,BOT_KEY));
            chatModalArrayList.add(new ChatModal(tname2,BOT_KEY));

        }

    }
}