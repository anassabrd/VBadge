package com.surtymar.vbadge.Beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Coordinates extends RealmObject {

    @PrimaryKey
    private int id;

    private double latitude;
    private double longitude;

    public Coordinates() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Coordinates(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
