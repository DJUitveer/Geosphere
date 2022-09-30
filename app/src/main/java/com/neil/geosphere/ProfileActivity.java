package com.neil.geosphere;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
        //Todo: Edit profile
    }

    public void DeleteProfile() {
        //Todo: Delete profile
    }

    public void ProfileAchievements() {
        //Todo: Achievements
    }

    public void ProfileHistory() {
        //Todo: History
    }

    public void LogOut() {
        //Todo: Log Out
    }
}