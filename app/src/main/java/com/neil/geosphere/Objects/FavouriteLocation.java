package com.neil.geosphere.Objects;

import com.google.android.gms.maps.model.LatLng;

public class FavouriteLocation {
    private String Title;
    private LatLng position;
    private String userID;

    public FavouriteLocation(String title, LatLng position, String userID) {
        Title = title;
        this.position = position;
        this.userID = userID;
    }

    public FavouriteLocation() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

}
