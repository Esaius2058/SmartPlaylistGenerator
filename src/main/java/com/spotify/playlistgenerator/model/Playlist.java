package com.spotify.playlistgenerator.model;

import java.io.Serializable;

public class Playlist implements Serializable{
    private String id;
    private String name;
    private String ownerId;

    public Playlist(String id, String name, String ownerId){
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
    }

    public String getId(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getOwnerId(){
        return ownerId;
    }

    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }
}