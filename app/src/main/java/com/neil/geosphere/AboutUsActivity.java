package com.neil.geosphere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class AboutUsActivity extends AppCompatActivity {
Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        menu = findViewById(R.id.btn_menu_about_us);

        //Method to open and interact with menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(AboutUsActivity.this, menu);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    //Switch statement to decide what users chooses
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bookmarks:
                                Intent ToBookmark = new Intent(AboutUsActivity.this, BookmarkedLocationsActivity.class);
                                startActivity(ToBookmark);
                                break;
                            case R.id.settings:
                                Intent toSettings = new Intent(AboutUsActivity.this, SettingsActivity.class);
                                startActivity(toSettings);
                                break;
                            case R.id.my_profile:
                                Intent ToMyProfile=new Intent(AboutUsActivity.this, ProfileActivity.class);
                                startActivity(ToMyProfile);
                                break;
                            case R.id.about_us:
                                Toast.makeText(AboutUsActivity.this, "Page Already active ", Toast.LENGTH_LONG).show();
//                                Intent ToAboutUs = new Intent(AboutUsActivity.this, AboutUsActivity.class);
//                                startActivity(ToAboutUs);
                                break;
                            case R.id.help:
                                Intent ToHelp = new Intent(AboutUsActivity.this, HelpActivity.class);
                                startActivity(ToHelp);
                                break;
                            case R.id.home:
                                Intent ToHome = new Intent(AboutUsActivity.this, Main_Menu_Activity.class);
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

    @Override
    public void onBackPressed() {
        return;
    }
}