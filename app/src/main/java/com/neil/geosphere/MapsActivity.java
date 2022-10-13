package com.neil.geosphere;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.neil.geosphere.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //TODO: Get Device Location and set it to the map on load

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
/**
 * CameraPosition cp = new CameraPosition.Builder()
 *             .target(initialLatLng) // your initial co-ordinates here. like, LatLng initialLatLng
 *             .zoom(zoom_level)
 *             .build();*/
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-29.7718824, 31.0370521);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //https://stackoverflow.com/questions/14157536/how-do-i-set-default-location-and-zoom-level-for-google-map-api-v2
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(sydney, 17);
        mMap.moveCamera(cameraPosition);


    }

}