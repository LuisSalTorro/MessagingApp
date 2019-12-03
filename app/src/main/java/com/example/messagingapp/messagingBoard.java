package com.example.messagingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
public class messagingBoard extends AppCompatActivity {
    Button send;
    EditText message;
    TextView textField;
    String name;
    private DatabaseReference myDatabase;
//    private FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>
//            mFirebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_board);
        name = getIntent().getStringExtra("Name");
        setupGUI();
        sendMessages();
    }

    public void sendMessages(){
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabase.child("").setValue(encrypt(name + message.getText().toString()));
                message.setText("");
            }
        });
    }

    public void setupGUI(){
        //mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        send = findViewById(R.id.sendButton);
        message = findViewById(R.id.messageField);
        textField = findViewById(R.id.textField);
        databaseSetup();
    }
    public void databaseSetup(){
        myDatabase = FirebaseDatabase.getInstance().getReference("Message");

        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                textField.setText(encrypt(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                textField.setText("CANCELLED");
            }
        });
    }

    public String reverseString(String str){
        StringBuilder strBuilder = new StringBuilder();
        char[] strChars = str.toCharArray();

        for (int i = strChars.length - 1; i >= 0; i--) {
            strBuilder.append(strChars[i]);
        }

        return strBuilder.toString();
    }

    public String alphabetAndCharacters(){
        String special = "@#%^&=_";
        String alphabet = "abcdefghijklmnopqrstuvwxyz"  + special;

        return alphabet;
    }

    public String encrypt(String word){
        char[] alpha = alphabetAndCharacters().toCharArray();
        char[] revAlpha = reverseString(alphabetAndCharacters()).toCharArray();

        char[] splitWord = word.toCharArray();
        for(int i = 0; i < splitWord.length; i++){
            for(int j = 0; j < revAlpha.length; j++){
                if(splitWord[i] == alpha[j]){
                    splitWord[i] = revAlpha[j];
                    break;
                }
            }
        }
        String encrypredWord = new String(splitWord);
        return encrypredWord;
    }


}
