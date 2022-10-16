package com.neil.geosphere.Util;

import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.CameraUpdateFactory;
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

    String nearbyPlaces;
    GoogleMap googleMap;
    String url;

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
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject getLocation = jsonObject1.getJSONObject("geometry").getJSONObject("location");
                                String lat = getLocation.getString("lat");
                                String lng = getLocation.getString("lng");

                                JSONObject getName = jsonArray.getJSONObject(i);
                                String name = getName.getString("name");
                                LatLng latLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.title(name);
                                markerOptions.position(latLng);
                                googleMap.addMarker(markerOptions);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
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
