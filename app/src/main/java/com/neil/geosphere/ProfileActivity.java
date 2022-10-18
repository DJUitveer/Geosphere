package com.neil.geosphere;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    //Declaring Components
    private Button logOut, menu;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        //Initializing Components
        logOut = findViewById(R.id.btnLogout);
        menu = findViewById(R.id.btn_menu_profile);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogOut();
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ProfileActivity.this, menu);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    //Switch statement to decide what users chooses
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bookmarks:
                                Intent ToBookmark = new Intent(ProfileActivity.this, BookmarkedLocationsActivity.class);
                                startActivity(ToBookmark);
                                break;
                            case R.id.settings:
                                Intent toSettings = new Intent(ProfileActivity.this, SettingsActivity.class);
                                startActivity(toSettings);
                                break;
                            case R.id.my_profile:
                                Toast.makeText(ProfileActivity.this, "Page Already active ", Toast.LENGTH_LONG).show();
                                break;
                            case R.id.about_us:
                                Intent ToAboutUs = new Intent(ProfileActivity.this, AboutUsActivity.class);
                                startActivity(ToAboutUs);
                                break;
                            case R.id.help:
                                Intent ToHelp = new Intent(ProfileActivity.this, HelpActivity.class);
                                startActivity(ToHelp);
                                break;
                            case R.id.home:
                                Intent ToHome = new Intent(ProfileActivity.this, Main_Menu_Activity.class);
                                startActivity(ToHome);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }


    //Method to Sign out the user
    public void LogOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        return;
    }
}