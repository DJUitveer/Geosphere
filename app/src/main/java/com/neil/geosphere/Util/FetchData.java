package com.neil.geosphere.Util;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.neil.geosphere.Objects.CurrentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FetchData {
    public static String distance;
    public static String ETA;
    static Polyline polylineToAdd;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Executor executor = Executors.newCachedThreadPool();
    String nearbyPlaces;
    String route;
    GoogleMap googleMap;
    String url;

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

    //background method to get the route for a destination
    public void GetDirections(Object... object) {
        //background thread
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    googleMap = (GoogleMap) object[0];
                    url = (String) object[1];
                    DowloadUrl dowloadUrl = new DowloadUrl();
                    route = dowloadUrl.retrieveURL(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //UI thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Object result = new JSONObject(route);
                            JSONArray routes = ((JSONObject) result).getJSONArray("routes");

                            long distanceForSegment = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value");
                            JSONArray steps = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
                            distance = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("text");
                            ETA = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("duration").getString("text");
                            List<LatLng> lines = new ArrayList<LatLng>();

                            for (int i = 0; i < steps.length(); i++) {
                                String polyline = steps.getJSONObject(i).getJSONObject("polyline").getString("points");
                                for (LatLng p : decodePolyline(polyline)) {
                                    lines.add(p);
                                }
                            }

                            if (polylineToAdd != null) {
                                polylineToAdd.remove();
                            }

                            polylineToAdd = googleMap.addPolyline(new PolylineOptions().addAll(lines).width(10).color(Color.BLUE));
                            polylineToAdd.setClickable(true);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //decoding the polyline from Json in order to display
    private List<LatLng> decodePolyline(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();

        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(p);
        }

        return poly;
    }

}
