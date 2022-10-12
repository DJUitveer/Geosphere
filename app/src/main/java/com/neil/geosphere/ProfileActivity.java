package com.neil.geosphere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    //Declaring Components
    private Button edit, delete, achievements, history, logOut;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        //Initializing Components
        edit = findViewById(R.id.btnEdit);
        delete = findViewById(R.id.btnDeleteProfile);
        achievements = findViewById(R.id.btnAchievements);
        history = findViewById(R.id.btnHistory);
        logOut = findViewById(R.id.btnLogout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });
    }

    public void EditProfile() {
        //Todo: Edit profile (neil & creolin)
    }

    public void DeleteProfile() {
        //Todo: Delete profile (neil & creolin)
    }

    public void ProfileAchievements() {
        //Todo: Achievements (final POE)
    }

    public void ProfileHistory() {
        //Todo: History (mojo)
    }

    //Method to Sign out the user
    public void LogOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }
}