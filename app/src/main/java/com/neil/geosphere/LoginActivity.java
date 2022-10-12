package com.neil.geosphere;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    //Declaring Components
    private EditText email, password;
    private TextView register;
    private Button signIn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("Users");

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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register method
                signUp(userEmail, userPassword);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //login method
                login(userEmail, userPassword);
            }
        });
    }

    //method to login using normal email and google
    public void login(String email, String password) {
        //Method to prevent users entering blank data
        if ((email.isEmpty()) && (password.isEmpty())) {
            Toast.makeText(LoginActivity.this, "Please provide Sign Up details", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Activity) LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Sign Up Successful.", Toast.LENGTH_SHORT);
                                FirebaseUser user = mAuth.getCurrentUser();
                                //CurrentUser.currentUserKey = user.getUid();
//                                Intent switchToSignUp = new Intent(MainActivity.this, SignUpActivity.class);
//                                switchToSignUp.putExtra("LoginEmail", email);
//                                startActivity(switchToSignUp);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "createUserWithEmail:failure", Toast.LENGTH_SHORT);
                                Toast.makeText(LoginActivity.this, "Sign Up Failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //method to signup using normal email
    public void signUp(String email, String password) {
        //Firebase authentication to validate user log in details
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, display a message to the user and move to the next activity.
                            Toast.makeText(LoginActivity.this, "signInWithEmail:success", Toast.LENGTH_LONG);
                            FirebaseUser user = mAuth.getCurrentUser();
                            myRef.orderByChild("email").startAt(user.getEmail()).endAt(user.getEmail()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        //CurrentUser.currentUserKey=user.getUid();
                                        Toast.makeText(LoginActivity.this, "User Logged in ", Toast.LENGTH_LONG);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
                            //Intent switchToMainMenu = new Intent(context, MainMenuActivity.class);
                            //startActivity(switchToMainMenu);
                            //search for user

                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Login Failed. Please enter correct Login details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //method to signup with google
    public void googleSignUp() {
        //Todo: Google signup method (nikshay)
    }

}