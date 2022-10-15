package com.neil.geosphere.Objects;

public class Settings {
    private String uid;
    private String landmarkType;
    private String unitOfMeasurement;

    public Settings(String uid, String landmarkType, String unitOfMeasurement) {
        this.uid = uid;
        this.landmarkType = landmarkType;
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Settings() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLandmarkType() {
        return landmarkType;
    }

    public void setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
    }

    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }
}
