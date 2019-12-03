package com.example.messagingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login, create;

    FirebaseAuth userAuthentication;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGUI();
        userAuthentication = FirebaseAuth.getInstance();
        FirebaseUser user = userAuthentication.getCurrentUser(); //checks if user has is logged in
        //if user is logged in then it moves user into the messaging part of app
        if(user != null){
            Intent intent = new Intent(MainActivity.this, messagingBoard.class);
            intent.putExtra("Name",createName());
            startActivity(intent);
            finish(); //this destroys current activity. So users can't get back to this Activity even through backbutton
        }
        buttonListeners();
    }

    public String createName(){
        StringBuilder name = new StringBuilder();
        String emailSTR = this.email.getText().toString();
        String[] numsAndOps = emailSTR.toString().split(" ");
        for(int i = 0; i < emailSTR.length(); i++){
            if(numsAndOps[i].equals("@")){
                break;
            }
            name.append(numsAndOps[i]);
        }
        return name.toString();
    }

    public void buttonListeners(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contentIsFilled()){
                    validateUser();
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);

                startActivity(intent);
            }
        });
    }

    public void validateUser(){
        //check users account and password
        String emailSTR = email.getText().toString(),
                passwordSTR = password.getText().toString();
        Toast.makeText(MainActivity.this, "Please wait while we validate your login", Toast.LENGTH_SHORT).show();
        userAuthentication.signInWithEmailAndPassword(emailSTR, passwordSTR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, messagingBoard.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean contentIsFilled(){
        if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(this,"Fill out the fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void setupGUI(){
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEdit);
        login = findViewById(R.id.loginButton);
        create = findViewById(R.id.register);
    }
}
