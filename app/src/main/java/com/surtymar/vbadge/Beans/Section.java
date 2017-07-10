package com.surtymar.vbadge.Beans;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anass on 10/07/2017.
 */
public class Section extends RealmObject{

    @PrimaryKey
    private int id;
    private String name;
    private Coordinates coordinates;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
