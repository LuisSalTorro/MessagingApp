package com.example.messagingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText username, password, confirmPassword;
    Button register;

    FirebaseAuth userAuthentication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupGUI();
        userAuthentication = FirebaseAuth.getInstance();

        validateAccount();
    }

    public void validateAccount(){
        //check if username exists
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!contentsAreFilled()){
                    Toast.makeText(Register.this, "Please fill out the contents", Toast.LENGTH_SHORT).show();
                }
                else if(passwordIsCorrectLength() && passwordsMatch()){
                    //register
                    registration();
                }
            }
        });

    }
    public void registration(){
        String emailSTR = username.getText().toString(),
                passwordSTR = password.getText().toString();
        userAuthentication.createUserWithEmailAndPassword(emailSTR, passwordSTR).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this, "Account registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Register.this, "There was an error creating an account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //checks if every field is filled
    public boolean contentsAreFilled(){
        String usernameSTR = username.getText().toString();
        String passwordSTR = password.getText().toString();
        String confirmPasswordSTR = confirmPassword.getText().toString();
        if(usernameSTR.isEmpty() || passwordSTR.isEmpty() || confirmPasswordSTR.isEmpty()){
            return false;
        }
        return true;
    }

    //makes sure passwords are 7 characters or longer
    public boolean passwordIsCorrectLength(){
        String pass = password.getText().toString();
        if(pass.length() > 6){
            return true;
        }
        Toast.makeText(this, "Please enter at least 7 characters", Toast.LENGTH_SHORT).show();
        return false;
    }

    //makes sure passwords are the same
    public boolean passwordsMatch(){
        //checks to see if the passwords are the same
        if(password.getText().toString().equals(confirmPassword.getText().toString())){
            return true;
        }
        Toast.makeText(this, "Please make sure passwords match", Toast.LENGTH_SHORT).show();
        return false;
    }

    public void setupGUI(){
        username = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEdit);
        confirmPassword = findViewById(R.id.confirmPasswordEdit);
        register = findViewById(R.id.register);
    }
}
