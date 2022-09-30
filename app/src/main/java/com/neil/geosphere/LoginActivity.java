package com.neil.geosphere;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    //Declaring Components
    private EditText email, password;
    private TextView register;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Initializing Components
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signIn = findViewById(R.id.btnSignIn);
        register = findViewById(R.id.tvSignUp);

        //getting user input when button is clicked
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        //login method
        login(userEmail, userPassword);
        //register method
        signUp(userEmail, userPassword);
    }

    //method to login using normal email and google
    public void login(String email, String password) {
        //Todo: Login method (email and google)
    }

    //method to signup using normal email
    public void signUp(String email, String password) {
        //Todo: Email Sign Up method
    }

    //method to signup with google
    public void googleSignUp() {
        //Todo: Google signup method
    }

}