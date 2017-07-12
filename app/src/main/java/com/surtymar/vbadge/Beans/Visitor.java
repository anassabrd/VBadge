package com.surtymar.vbadge.Beans;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anass on 10/07/2017.
 */

public class Visitor extends RealmObject {

    @PrimaryKey
    private int id;
    private String first_name;
    private String last_name;
    private String badge_state;
    private String date_in;
    private String date_out;
    private String image_url;
    private RealmList<Section> sections;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBadge_state() {
        return badge_state;
    }

    public void setBadge_state(String badge_state) {
        this.badge_state = badge_state;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public String getDate_out() {
        return date_out;
    }

    public void setDate_out(String date_out) {
        this.date_out = date_out;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public RealmList<Section> getSections() {
        return sections;
    }

    public void setSections(RealmList<Section> sections) {
        this.sections = sections;
    }
}
