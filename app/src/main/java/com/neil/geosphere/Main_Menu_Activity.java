package com.neil.geosphere;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

public class Main_Menu_Activity extends AppCompatActivity {
    //Declaring components
    private CardView settings, profile, map, bookmark, nearby;
    private boolean mLocationPermision = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        //setting components to declared variables
        map = findViewById(R.id.crv_main_menu_open_map);
        bookmark = findViewById(R.id.crv_main_menu_open_bookmarked);
        settings = findViewById(R.id.crv_main_menu_settings);
        profile = findViewById(R.id.crv_main_menu_profile);
        nearby = findViewById(R.id.crv_main_menu_nearby);

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

        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToNearbyLocations();
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

    //method to switch to the Nearby locations page
    public void ToNearbyLocations() {
        Intent ToNearbyLocations = new Intent(Main_Menu_Activity.this, MapsActivity.class);
        startActivity(ToNearbyLocations);
    }

}