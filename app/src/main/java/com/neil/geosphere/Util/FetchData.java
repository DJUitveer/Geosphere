package com.neil.geosphere.Util;

import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FetchData {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();
    //https://stackoverflow.com/questions/32937838/google-maps-api-and-custom-polyline-route-between-markers
    String nearbyPlaces;
    String route;
    GoogleMap googleMap;
    String url;
    LatLng startDestination;
    LatLng endDestination;

    //background method to get nearby places from your location
    public void doOnThreads(Object... object) {
        //background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    googleMap = (GoogleMap) object[0];
                    url = (String) object[1];
                    DowloadUrl dowloadUrl = new DowloadUrl();
                    nearbyPlaces = dowloadUrl.retrieveURL(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //UI thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(nearbyPlaces);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getResults = jsonArray.getJSONObject(i);
                                String placeID = getResults.getString("place_id");

                                JSONObject getLocation = getResults.getJSONObject("geometry").getJSONObject("location");
                                String lat = getLocation.getString("lat");
                                String lng = getLocation.getString("lng");

                                JSONObject getName = jsonArray.getJSONObject(i);
                                String name = getName.getString("name");

                                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.title(name);
                                markerOptions.position(latLng);
                                markerOptions.snippet(placeID);
                                googleMap.addMarker(markerOptions);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    //todo:get routes coords and make polyline appear on maps fragment (for final POE #INCOMPLETE)
    //https://developers.google.com/maps/documentation/android-sdk/polygon-tutorial
    //https://developers.google.com/maps/documentation/navigation/android-sdk/v2/route#add_a_navigation_fragment
    public void GetDirections(LatLng... latLngParamaters) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    startDestination = latLngParamaters[0];
                    endDestination = latLngParamaters[1];
                    DowloadUrl dowloadUrl = new DowloadUrl();
                    route = dowloadUrl.retrieveURL(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(route);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getResults = jsonArray.getJSONObject(i);
                                JSONObject getLocation = getResults.getJSONObject("geometry").getJSONObject("location");

                                String lat = getLocation.getString("lat");
                                String lng = getLocation.getString("lng");

                                JSONObject getName = jsonArray.getJSONObject(i);
                                String name = getName.getString("name");

                                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.title(name);
                                markerOptions.position(latLng);
                                googleMap.addMarker(markerOptions);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}
