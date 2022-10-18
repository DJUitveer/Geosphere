package com.neil.geosphere.Util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.neil.geosphere.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routing_Nav {
    private MapboxMap map;
    private MapView mapView;
    private NavigationMapRoute navigationMapRoute;

    public void getRoute(Point startPoint, Point destination, Context context) {
        try {
            NavigationRoute.builder(context)
                    .accessToken(context.getString(R.string.MapBox_Token))
                    .origin(startPoint)
                    .destination(destination)
                    .voiceUnits(DirectionsCriteria.IMPERIAL)
                    .language(Locale.UK)
                    .build()
                    .getRoute(new Callback<DirectionsResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<DirectionsResponse> call, @NonNull Response<DirectionsResponse> response) {

                            if (response.body() == null) {
                                Toast.makeText(context, "No routes found 1", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                if (response.body().routes().size() < 0) {
                                    Toast.makeText(context, "No routes found", Toast.LENGTH_SHORT).show();

                                    return;
                                }
                            }
                            DirectionsRoute route;
                            route = response.body().routes().get(0);
                            NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                                    .directionsRoute(route)
                                    .shouldSimulateRoute(true)
                                    .build();
                            NavigationLauncher.startNavigation((Activity) context, options);
                        }

                        @Override
                        public void onFailure(Call<DirectionsResponse> call, Throwable t) {


                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            String error = e.toString();
        }
    }
}
