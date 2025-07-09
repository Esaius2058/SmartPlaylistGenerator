package com.spotify.playlistgenerator.api;

import com.spotify.playlistgenerator.model.AudioFeaturesCustom;
import com.spotify.playlistgenerator.model.Playlist;
import com.spotify.playlistgenerator.model.Track;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.*;
import se.michaelthelin.spotify.requests.data.player.GetCurrentUsersRecentlyPlayedTracksRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetListOfCurrentUsersPlaylistsRequest;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForSeveralTracksRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class SpotifyPlaylistService{
    private static final Logger logger = LogManager.getLogger(SpotifyPlaylistService.class);
    private  SpotifyApi spotifyApi = new SpotifyApi.Builder().build();

    public SpotifyPlaylistService(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;
        logger.info("Initialized SpotifyPlaylistService");
    }

    public List<Playlist> getUserPlaylists(){
        try{
            List<Playlist> playlists = new ArrayList<>();
            int offset = 0;
            int limit = 50;

            while (true) {
                GetListOfCurrentUsersPlaylistsRequest request = spotifyApi.getListOfCurrentUsersPlaylists()
                        .limit(limit)
                        .offset(offset)
                        .build();

                Paging<PlaylistSimplified> response = request.execute();

                for (PlaylistSimplified playlistItem : response.getItems()) {
                    String ownerId = playlistItem.getOwner() != null ? playlistItem.getOwner().getId() : "unknown";
                    Playlist playlist = new Playlist(
                            playlistItem.getId(),
                            playlistItem.getName(),
                            ownerId
                    );
                    if (playlistItem.getName() != null && !playlistItem.getName().isBlank()) {
                        playlists.add(playlist);
                    }
                }

                if (response.getNext() == null) {
                    break;
                }

                offset += limit;
            }

            logger.info("Fetched {} playlists for user.", playlists.size());
            return playlists;
        }catch(Exception e){
            logger.error("Failed to fetch playlists: {}", e.getMessage());
            throw new RuntimeException("Playlist fetch failed", e);
        }
    }

    public List<Track> getPlaylistTracks(String playlistId) {
        try {
            List<Track> tracks = new ArrayList<>();
            Instant after = null;
            int offset = 0;
            int limit = 100;

            while (true) {
                GetPlaylistsItemsRequest requestBuilder = spotifyApi
                        .getPlaylistsItems(playlistId)
                        .limit(limit)
                        .offset(offset)
                        .build();

                Paging<PlaylistTrack> response = requestBuilder.execute();

                List<PlaylistTrack> trackItems = Arrays.asList(response.getItems());

                if (trackItems.isEmpty()) {
                    break;
                }

                for (PlaylistTrack trackItem : trackItems) {
                    Track track = new Track(
                            trackItem.getTrack().getId(),
                            trackItem.getTrack().getName(),
                            trackItem.getAddedBy().getDisplayName()
                    );
                    tracks.add(track);
                }

                logger.info("Fetched {} tracks for playlist {}", response.getItems().length, playlistId);
                if (response.getNext() == null) {
                    break;
                }
                offset += limit;
                // Optional: sleep 200ms to avoid hammering the API
                Thread.sleep(200);
            }

            logger.info("Fetched {} tracks from playlist {}", tracks.size(), playlistId);
            return tracks;
        } catch (Exception e) {
            logger.error("Failed to fetch tracks for playlist {}: {}", playlistId, e.getMessage());
            throw new RuntimeException("Track fetch failed", e);
        }
    }

    private List<Track> getRecentlyPlayedTracks(String playlistId) {
        try {
            List<Track> tracks = new ArrayList<>();
            Instant after = null;

            while (true) {
                GetCurrentUsersRecentlyPlayedTracksRequest.Builder requestBuilder = spotifyApi
                        .getCurrentUsersRecentlyPlayedTracks()
                        .limit(50); // max Spotify allows

                Date afterDate = new Date(after.toEpochMilli());
                if (after != null) {
                    requestBuilder.after(afterDate);
                }

                GetCurrentUsersRecentlyPlayedTracksRequest request = requestBuilder.build();
                PagingCursorbased<PlayHistory> response = request.execute();

                List<PlayHistory> trackItems = Arrays.asList(response.getItems());

                if (trackItems.isEmpty()) {
                    break;
                }

                for (PlayHistory trackItem : trackItems) {
                    Track track = new Track(
                            trackItem.getTrack().getId(),
                            trackItem.getTrack().getName(),
                            trackItem.getTrack().getArtists()[0].getName()
                    );
                    tracks.add(track);
                }

                // Get timestamp of the last track played to use in the next request
                PlayHistory lastTrack = trackItems.get(trackItems.size() - 1);
                after = Instant.parse((CharSequence) lastTrack.getPlayedAt());

                // Exit loop if less than 50 trackItems fetched, no more to page
                if (trackItems.size() < 50) {
                    break;
                }

                // Optional: sleep 200ms to avoid hammering the API
                Thread.sleep(200);
            }

            logger.info("Fetched {} recently played tracks", tracks.size());
            return tracks;
        } catch (Exception e) {
            logger.error("Failed to fetch recently played tracks: {}", e.getMessage());
            throw new RuntimeException("Track fetch failed", e);
        }
    }

    public List<AudioFeaturesCustom> getAudioFeatures(List<String> trackIds){
       try{
           List<AudioFeaturesCustom> audioFeatures = new ArrayList<>();

           if(trackIds.isEmpty()){
               return audioFeatures;
           }

           String[] trackIdsArray = trackIds.toArray(new String[0]);

           GetAudioFeaturesForSeveralTracksRequest request = spotifyApi
                   .getAudioFeaturesForSeveralTracks(trackIdsArray)
                   .build();

           AudioFeatures[] response = request.execute();

           for(AudioFeatures audioFeature: response){
               if(audioFeature != null){
                   AudioFeaturesCustom features = new AudioFeaturesCustom(
                           audioFeature.getId(),
                           audioFeature.getTempo(),
                           audioFeature.getDanceability(),
                           audioFeature.getEnergy(),
                           audioFeature.getValence()
                   );
                   audioFeatures.add(features);
               }
           }

           return audioFeatures;
       }catch(Exception e){
           logger.error("Failed to fetch audio features for tracks: {}", e.getMessage());
           throw new RuntimeException("Failed to fetch audio features", e);
       }
    }
}