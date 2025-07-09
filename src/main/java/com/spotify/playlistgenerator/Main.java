package com.spotify.playlistgenerator;

import com.spotify.playlistgenerator.model.Playlist;
import com.spotify.playlistgenerator.model.Track;
import se.michaelthelin.spotify.SpotifyApi;
import com.spotify.playlistgenerator.api.SpotifyAuthService;
import com.spotify.playlistgenerator.api.SpotifyPlaylistService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    static List<String> getTrackIds(List<Track> tracks){
        List<String> trackIds = new ArrayList<>();
        for(Track track: tracks){
            trackIds.add(track.getId());
        }

        return trackIds;
    }


    static void startPlaylistGeneratorWithAuthentication(){
      try{
          SpotifyAuthService authService = new SpotifyAuthService();
          authService.authenticate();
          SpotifyPlaylistService playlistService = new SpotifyPlaylistService(authService.getSpotifyApi());

          List<Playlist> playlists = playlistService.getUserPlaylists();
          logger.info("Retrieved {} playlists", playlists.size());

          if(!playlists.isEmpty()){
              Playlist playlist = playlists.get(0);
              List<Track> tracks = playlistService.getPlaylistTracks(playlist.getId());
              logger.info("Retrieved {} tracks for playlist {}", tracks.size(), playlist.getName());

              List<String> trackIds = new ArrayList<>();

              playlistService.getAudioFeatures(getTrackIds(tracks));
              logger.info("Updated tracks with audio features");
          }
      }catch(Exception e){
          logger.error("Application failed: {}", e.getMessage());
          System.exit(1);
      }
    }

    public static void main (String[] args){
        logger.info("Starting EchoSeed...");
        startPlaylistGeneratorWithAuthentication();
        //Fetch playlists and generate recommendations
    }
}