package com.spotify.playlistgenerator;

import com.spotify.playlistgenerator.api.SpotifyAuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    static void startPlaylistGeneratorWithAuthentication(){
      try{
          SpotifyAuthService authService = new SpotifyAuthService();
          authService.authenticate();
          logger.info("Authentication successful, ready to proceed with API calls");
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