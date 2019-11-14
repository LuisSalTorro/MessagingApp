package com.example.messagingapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        //do somethings to see if the user has account
        Intent intent = new Intent(this, messagingBoard.class);
        startActivity(intent);
    }
}
