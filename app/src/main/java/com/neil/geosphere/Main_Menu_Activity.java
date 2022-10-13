package com.neil.geosphere;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String TAG = "GeoLoaction:";
    private static final int REQUEST=112;
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

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
                String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION};
                if (ActivityCompat.checkSelfPermission(Main_Menu_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main_Menu_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                  ActivityCompat.requestPermissions((Activity) Main_Menu_Activity.this,PERMISSIONS,REQUEST);
                }else{
                    GetLastGeoLocation();
                    ToMap();
                }
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
        GetLastGeoLocation();
        Intent ToMap = new Intent(Main_Menu_Activity.this, MapsActivity.class);
        startActivity(ToMap);
    }

    //method to switch to the Nearby locations page
    public void ToNearbyLocations() {
        Intent ToNearbyLocations = new Intent(Main_Menu_Activity.this, MapsActivity.class);
        startActivity(ToNearbyLocations);
    }

    @SuppressLint("MissingPermission")
    private void GetLastGeoLocation() {
        if (ActivityCompat.checkSelfPermission(Main_Menu_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main_Menu_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                    Log.i(TAG, String.valueOf(geoPoint.getLatitude()));
                    Log.i(TAG, String.valueOf(geoPoint.getLongitude()));
                }
            }
        });
    }
}