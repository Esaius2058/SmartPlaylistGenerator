package com.spotify.playlistgenerator.model;

public class AudioFeatures {
    private String id;
    private double tempo;
    private double danceability;
    private double energy;

    public AudioFeatures(String id, double tempo, double danceability, double energy){
        this.id = id;
        this.tempo = tempo;
        this.danceability = danceability;
        this.energy = energy;
    }
}
