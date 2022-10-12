package com.neil.geosphere;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    //Declaring Components
    private Button edit, delete, achievements, history, logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Initializing Components
        edit = findViewById(R.id.btnEdit);
        delete = findViewById(R.id.btnDeleteProfile);
        achievements = findViewById(R.id.btnAchievements);
        history = findViewById(R.id.btnHistory);
        logOut = findViewById(R.id.btnLogout);

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
    }
}