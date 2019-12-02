package com.example.firebasedb;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Track {

    private String id;
    private String trackName;
    private  int rating;


    public Track(){

    }

    public Track(String id, String trackName, int rating) {
        this.id = id;
        this.trackName = trackName;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}


