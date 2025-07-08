package com.spotify.playlistgenerator.model;

import java.io.Serializable;

public class Track implements Serializable{
    private String id;
    private String name;
    private String artist;


    public Track(String id, String name, String artist){
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    public String getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getArtist(){
        return artist;
    }


    public void setId(String id){
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
}