package com.spotify.playlistgenerator.model;

import java.io.Serializable;

public class AudioFeaturesCustom implements Serializable {
    private String id;
    private double tempo;
    private double danceability;
    private double energy;
    private double valence;

    public AudioFeaturesCustom(String id, double tempo, double danceability, double energy, double valence){
        this.id = id;
        this.tempo = tempo;
        this.danceability = danceability;
        this.energy = energy;
        this.valence = valence;
    }

    public String getId() {return id;}
    public double getTempo() {
        return tempo;
    }
    public double getDanceability(){
        return danceability;
    }
    public double getEnergy(){
        return energy;
    }
    public double getValence(){return valence; }

    public void setId(String id) {this.id = id; }
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
    public void setDanceability(double danceability){
        this.danceability = danceability;
    }
    public void setEnergy(double energy){ this.energy = energy; }
    public void setValence(double valence){ this.valence = valence; }
}
