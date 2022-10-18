package com.neil.geosphere.Objects;

import com.google.android.gms.maps.model.LatLng;

public class FavouriteLocation {
    private String Title;

    private String latitude;
    private String longitude;
    private String userID;

    public FavouriteLocation(String title, String latitude, String longitude, String userID) {
        this.Title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userID = userID;
    }

    public FavouriteLocation() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
