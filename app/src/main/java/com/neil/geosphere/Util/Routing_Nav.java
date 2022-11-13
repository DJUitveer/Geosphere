package com.neil.geosphere.Util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.neil.geosphere.MapsActivity;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routing_Nav {
    public static DirectionsRoute route;
    private static MapboxMap map;
    private static MapView mapView;
    private static NavigationMapRoute navigationMapRoute;

//    public void getRoute(Point startPoint, Point destination) {
//        Mapbox.getInstance(MapsActivity.this, getString(R.string.MapBox_Token));
//        MapboxNavigation navigation;
//        navigation = new MapboxNavigation(getApplicationContext(), getString(R.string.MapBox_Token));
//        try {
//            if (CurrentUser.unitOfMeasurement == "Imperial") {
//                NavigationRoute.builder(MapsActivity.this)
//                        .accessToken(getString(R.string.MapBox_Token))
//                        .origin(startPoint)
//                        .destination(destination)
//                        .voiceUnits(DirectionsCriteria.IMPERIAL)
//                        .language(Locale.UK)
//                        .build()
//                        .getRoute(new Callback<DirectionsResponse>() {
//                            @Override
//                            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
//
//                                if (response.body() == null) {
//                                    Toast.makeText(MapsActivity.this, "No routes found 1", Toast.LENGTH_SHORT).show();
//                                    return;
//                                } else if (response.body().routes().size() < 0) {
//                                    Toast.makeText(MapsActivity.this, "No routes found", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                route = response.body().routes().get(0);
//                                navigationMapRoute.addRoute(route);
//                                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                                        .directionsRoute(route)
//                                        .shouldSimulateRoute(true)
//                                        .build();
//                                NavigationLauncher.startNavigation(MapsActivity.this, options);
//                            }
//
//                            @Override
//                            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
//                                Toast.makeText(MapsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            } else if (CurrentUser.unitOfMeasurement == "Metric") {
//                NavigationRoute.builder(MapsActivity.this)
//                        .accessToken(getString(R.string.MapBox_Token))
//                        .origin(startPoint)
//                        .destination(destination)
//                        .voiceUnits(DirectionsCriteria.METRIC)
//                        .language(Locale.UK)
//                        .build()
//                        .getRoute(new Callback<DirectionsResponse>() {
//                            @Override
//                            public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {
//
//                                if (response.body() == null) {
//                                    Toast.makeText(MapsActivity.this, "No routes found 1", Toast.LENGTH_SHORT).show();
//                                    return;
//                                } else if (response.body().routes().size() < 0) {
//                                    Toast.makeText(MapsActivity.this, "No routes found", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                route = response.body().routes().get(0);
//
//                                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                                        .directionsRoute(route)
//                                        .shouldSimulateRoute(true)
//                                        .build();
//                                NavigationLauncher.startNavigation(MapsActivity.this, options);
//                            }
//
//                            @Override
//                            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
//                                Toast.makeText(MapsActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        } catch (Exception e) {
//            String error = e.toString();
//            Toast.makeText(MapsActivity.this, error, Toast.LENGTH_SHORT).show();
//        }
//    }


}
