package com.mer.plamer.entities;
import java.util.ArrayList;

public class Track {
    String artist;
    String title;
    double length;
    String genre;
    ArrayList<String> comments;

    public Track(String artist, String title, double length, String genre){
        this.artist = artist;
        this.title = title;
        this.length = length;
        this.genre = genre;
        this.comments = new ArrayList<String>();
    }
}
