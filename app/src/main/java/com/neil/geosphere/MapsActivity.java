package com.neil.geosphere;

import static android.content.ContentValues.TAG;
import static com.neil.geosphere.Util.FetchData.ETA;
import static com.neil.geosphere.Util.FetchData.distance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.Objects.FavouriteLocation;
import com.neil.geosphere.Objects.Settings;
import com.neil.geosphere.Util.FetchData;
import com.neil.geosphere.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 101;
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static DirectionsRoute route;
    private static String userFilter;
    private static MapboxMap map;
    private static MapView mapView;
    private static NavigationMapRoute navigationMapRoute;
    private final LatLng defaultLocation = new LatLng(-29, 31);
    public LocationManager locationManager;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private boolean locationPermissionGranted = false;
    private Location lastKnownLocation;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private StringBuilder stringBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
    private SearchView searchView;
    private String toFavouritePlaceID = "";
    private boolean navigateTo = false;
    private int selectedPolylineCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getApplicationContext());
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        getUnitOfMeasurement();
        searchView = findViewById(R.id.sv_Find_Places);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (location != null || location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    String placeID = address.getAddressLine(0);
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location).snippet(placeID));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mMap != null) {
            getLocationPermission();
            updateLocationUI();
            getDeviceLocation();
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    String markerName = marker.getTitle();
                    LatLng favouriteLocation = marker.getPosition();
                    AlertDialogForMarkerClick(markerName, favouriteLocation);
                    LatLng starting = new LatLng(CurrentUser.deviceLocationForRoute.latitude(), CurrentUser.deviceLocationForRoute.longitude());
                    AlertDialogForMarkerClickNavigation(markerName, favouriteLocation, starting);
                    return false;
                }
            });
            mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(@NonNull Polyline polyline) {
                    if (selectedPolylineCount == 0) {
                        Toast.makeText(MapsActivity.this, "Distance: " + distance + " ETA: " + ETA, Toast.LENGTH_SHORT).show();
                        selectedPolylineCount++;
                    } else {
                        AlertDialog.Builder navBuilder = new AlertDialog.Builder(MapsActivity.this);
                        navBuilder.setTitle("Navigation");
                        navBuilder.setMessage("Do you want to remove this navigation?");
                        navBuilder.setCancelable(false);
                        navBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (Dialog, which) -> {
                            polyline.remove();
                        });
                        navBuilder.setNegativeButton("No", (DialogInterface.OnClickListener) (Dialog, which) -> {
                            selectedPolylineCount = 0;
                        });
                        AlertDialog navAlertDialog = navBuilder.create();
                        navAlertDialog.show();
                    }

                }
            });

        } else {
            return;
        }
        //https://maps.googleapis.com/maps/api/directions/json?origin=31.037042,-29.771891&destination=31.0356,-29.7968&key=AIzaSyAAzrbFwZnHFud_k-kqD5OSuT_OUnZNVE8
    }//https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key=AIzaSyAAzrbFwZnHFud_k-kqD5OSuT_OUnZNVE8

    //gets the route for the user
    private void getPolylines(LatLng origin, LatLng destination, String directionMode) {
        String str_origin = "origin=" + origin.longitude + "," + origin.latitude;
        String str_destination = "destination=" + destination.latitude + "," + destination.longitude;
        String mode = "mode=" + directionMode;
        String unit = "units=" + CurrentUser.unitOfMeasurement.toLowerCase();
        String paramaters = str_origin + "&" + str_destination + "&" + mode + "&" + unit;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + paramaters + "&key=" + getString(R.string.GoogleMaps_API_KEY);

        Object datafetch[] = new Object[2];
        datafetch[0] = mMap;
        datafetch[1] = url;

        FetchData fetchData = new FetchData();
        fetchData.GetDirections(datafetch);
    }

    //method to display an alert dialog when a marker is selected
    private void AlertDialogForMarkerClick(String markerName, LatLng favourite) {
        //Alert Dialog for Favouriting a location
        AlertDialog.Builder favBuilder = new AlertDialog.Builder(MapsActivity.this);
        favBuilder.setTitle("Favourite");
        favBuilder.setMessage("Do you want to add " + markerName + " to Favourites?");
        favBuilder.setCancelable(false);
        favBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (Dialog, which) -> {
            //adds favourite to database
            saveFavourite(markerName, favourite);
        });
        favBuilder.setNegativeButton("No", (DialogInterface.OnClickListener) (Dialog, which) -> {

        });
        AlertDialog favAlertDialog = favBuilder.create();
        favAlertDialog.show();
    }

    //method to display an alert dialog for navigation when a marker is selected
    private void AlertDialogForMarkerClickNavigation(String markerName, LatLng destination, LatLng starting) {
        //Alert Dialog for Navigating
        AlertDialog.Builder navBuilder = new AlertDialog.Builder(MapsActivity.this);
        navBuilder.setTitle("Navigation");
        navBuilder.setMessage("Do you want to navigate to " + markerName + "?");
        navBuilder.setCancelable(false);
        Point start = Point.fromLngLat(starting.longitude, starting.latitude);
        Point dest = Point.fromLngLat(destination.latitude, destination.longitude);
        navBuilder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (Dialog, which) -> {
            //getRoute(start, dest);
            getPolylines(starting, destination, "driving");

        });
        navBuilder.setNegativeButton("No", (DialogInterface.OnClickListener) (Dialog, which) -> {

        });
        AlertDialog navAlertDialog = navBuilder.create();
        navAlertDialog.show();
    }

    //saves the location to bookmarks for the user
    private void saveFavourite(String markerName, LatLng favourite) {
        String userID = fAuth.getCurrentUser().getUid();
        String latitude = String.valueOf(favourite.latitude);
        String longitude = String.valueOf(favourite.longitude);
        FavouriteLocation newFavourite = new FavouriteLocation(markerName, latitude, longitude, userID);
        DocumentReference reference = fStore.collection("FavouriteLocations").document();
        reference.set(newFavourite).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MapsActivity.this, "Location Added to Favourites", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "Error! Could not save location to Favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //gets all nearby locations based on the user's saved settings
    private void getFilteredLocations() {
        stringBuilder.append("location=" + lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude());
        stringBuilder.append("&radius=5000");
        stringBuilder.append("&type=" + CurrentUser.userFilterSetting);
        stringBuilder.append("&sensor=true");
        stringBuilder.append("&key=" + getString(R.string.GoogleMaps_API_KEY));//hardcoded because its looses the apikey on pull form github
        String url = stringBuilder.toString();
        //https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-29.7718778,31.0370528&radius=5000&type=Popular&sensor=true&key=AIzaSyAAzrbFwZnHFud_k-kqD5OSuT_OUnZNVE8
        Object datafetch[] = new Object[2];
        datafetch[0] = mMap;
        datafetch[1] = url;

        FetchData fetchData = new FetchData();
        fetchData.doOnThreads(datafetch);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        locationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    //checks to see if the device has given permission in order to access the device's location
    public void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //get the current devices location
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                                CurrentUser.deviceLocationForRoute = Point.fromLngLat(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                                getFilteredLocations();
                            }
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    @SuppressLint("MissingPermission")
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    //gets the unit of measurement that the user has saved
    private void getUnitOfMeasurement() {
        String uid = fAuth.getCurrentUser().getUid();
        fStore.collection("UserSettings").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Settings getSettings = document.toObject(Settings.class);
                    CurrentUser.unitOfMeasurement = getSettings.getUnitOfMeasurement().toString();
                }
            }
        });
    }
}