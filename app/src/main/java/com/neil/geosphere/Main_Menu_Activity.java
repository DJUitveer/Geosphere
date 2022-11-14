package com.neil.geosphere;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.Objects.Settings;

public class Main_Menu_Activity extends AppCompatActivity {
    //Declaring components
    private CardView settings, profile, map, bookmark;
    private Button menu;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //setting components to declared variables
        map = findViewById(R.id.crv_main_menu_open_map);
        bookmark = findViewById(R.id.crv_main_menu_open_bookmarked);
        settings = findViewById(R.id.crv_main_menu_settings);
        profile = findViewById(R.id.crv_main_menu_profile);
        menu = findViewById(R.id.btn_menu_main_menu);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        getUserFilteredLocations();
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToSettings();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToProfile();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToMap();
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToBookmarks();
            }
        });
        //Method to open and interact with menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Main_Menu_Activity.this, menu);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    //Switch statement to decide what users chooses
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bookmarks:
                                Intent ToBookmark = new Intent(Main_Menu_Activity.this, BookmarkedLocationsActivity.class);
                                startActivity(ToBookmark);
                                break;
                            case R.id.settings:
                                Intent toSettings = new Intent(Main_Menu_Activity.this, SettingsActivity.class);
                                startActivity(toSettings);
                                break;
                            case R.id.my_profile:
                                Intent ToMyProfile = new Intent(Main_Menu_Activity.this, ProfileActivity.class);
                                startActivity(ToMyProfile);
                                break;
                            case R.id.about_us:
                                Intent ToAboutUs = new Intent(Main_Menu_Activity.this, AboutUsActivity.class);
                                startActivity(ToAboutUs);
                                break;
                            case R.id.help:

                                Intent ToHelp = new Intent(Main_Menu_Activity.this, HelpActivity.class);
                                startActivity(ToHelp);
                                break;
                            case R.id.home:
                                Toast.makeText(Main_Menu_Activity.this, "Page Already active ", Toast.LENGTH_LONG).show();
//                                Intent ToHome = new Intent(Main_Menu_Activity.this, HelpActivity.class);
//                                startActivity(ToHome);
                                break;
                            case R.id.image:
                                ToImage();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    //method to switch to the Profile page
    public void ToProfile() {
        Intent toProfile = new Intent(Main_Menu_Activity.this, ProfileActivity.class);
        startActivity(toProfile);
    }

    //method to switch to the Settings page
    public void ToSettings() {
        Intent toSettings = new Intent(Main_Menu_Activity.this, SettingsActivity.class);
        startActivity(toSettings);
    }

    //method to switch to the Bookmarks page
    public void ToBookmarks() {
        Intent ToBookmarks = new Intent(Main_Menu_Activity.this, BookmarkedLocationsActivity.class);
        startActivity(ToBookmarks);
    }

    //method to switch to the Map page
    public void ToMap() {
//        GetLastGeoLocation();
        Intent ToMap = new Intent(Main_Menu_Activity.this, MapsActivity.class);
        startActivity(ToMap);
    }
    public void ToImage()
    {
        Intent Toimage = new Intent(Main_Menu_Activity.this, GeoImageActivity.class);
        startActivity(Toimage);
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void getUserFilteredLocations() {
        String uid = fAuth.getCurrentUser().getUid();
        fStore.collection("UserSettings").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Settings getSettings = document.toObject(Settings.class);
                    if (getSettings != null) {
                        String landmarkType = getSettings.getLandmarkType();
                        CurrentUser.userFilterSetting = landmarkType;
                    } else {
                        Toast.makeText(Main_Menu_Activity.this, "Warning Settings could not be retrieved.", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
}