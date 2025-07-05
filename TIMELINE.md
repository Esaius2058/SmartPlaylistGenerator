# Smart Playlist Generator Project Timeline

This document outlines the weekly tasks and milestones for completing the **Smart Playlist Generator**, a Java-based application using the Spotify Web API to fetch playlists, analyze track features, generate smart playlists with AI-lite clustering (k-means), and implement cybersecurity features (secure token storage, network monitoring). The project targets completion by **September 30, 2025**, for a polished GitHub portfolio to support an application to Spotify’s 2026 Global Summer Internship.

## Project Setup
- **GroupId**: `com.spotify.playlistgenerator`
- **ArtifactId**: `SmartPlaylistGenerator`
- **Dependencies**: `spotify-web-api-java:8.5.0`, `commons-math3:3.6.1`, `log4j-core:2.20.0`, `junit-jupiter-api:5.11.0`, `jetty-server:11.0.24`, `jetty-servlet:11.0.24`
- **Structure**: `src/main/java/com/spotify/playlistgenerator/{api,model,recommendation,security,ui}`, `src/main/resources/application.properties`, `src/main/resources/log4j2.xml`
- **GitHub**: Repository initialized, `.gitignore` excludes IDE settings, `logs/`, and `application.properties`

## Weekly Milestones and Tasks

### Week 1 (July 4–11, 2025): OAuth Authentication Setup
- **Goal**: Resolve “domain not secure” error and implement Spotify OAuth flow.
- **Tasks**:
  1. Update Spotify Developer Dashboard to use `http://127.0.0.1:8888/callback` as the redirect URI.
  2. Update `src/main/resources/application.properties` with `spotify.redirect.uri=http://127.0.0.1:8888/callback`.
  3. Implement `api/SpotifyAuthService.java` to handle OAuth authorization code grant, using `spotify-web-api-java` and Jetty for callback handling.
  4. Test authentication, logging access token to `logs/app.log`.
  5. Commit changes to GitHub.
- **Deliverables**:
  - Working OAuth flow with access token retrieval.
  - `SpotifyAuthService.java` with logging.
  - Updated `README.md` with OAuth setup instructions.
- **Git Commands**:
  ```bash
  git add .
  git commit -m "Implemented Spotify OAuth with redirect URI http://127.0.0.1:8888/callback"
  git push origin main
  ```

### Week 2–3 (July 12–25, 2025): Spotify API Integration
- **Goal**: Fetch user playlists, tracks, and audio features.
- **Tasks**:
  1. Create `model/Playlist.java`, `model/Track.java`, and `model/User.java` to represent Spotify data.
  2. Implement `api/SpotifyPlaylistService.java` to fetch user playlists and tracks using Spotify API endpoints (`/v1/me/playlists`, `/v1/playlists/{playlist_id}/tracks`).
  3. Implement `api/SpotifyTrackService.java` to fetch audio features (e.g., tempo, danceability) using `/v1/audio-features`.
  4. Test API calls, logging results to `logs/app.log`.
  5. Commit changes to GitHub.
- **Deliverables**:
  - Data models for playlists, tracks, and users.
  - Functional API services to retrieve playlist and track data.
  - Updated `README.md` with API usage instructions.
- **Git Commands**:
  ```bash
  git add .
  git commit -m "Added Spotify API integration for playlists and track audio features"
  git push origin main
  ```

### Week 4–5 (July 26–August 8, 2025): AI-Lite Recommendation Engine
- **Goal**: Build k-means clustering for playlist generation.
- **Tasks**:
  1. Create `recommendation/FeatureExtractor.java` to normalize track audio features (e.g., scale tempo, danceability to 0–1).
  2. Implement `recommendation/ClusteringEngine.java` using `commons-math3` for k-means clustering (3–5 clusters).
  3. Create `recommendation/PlaylistGenerator.java` to select tracks from clusters and create a new playlist via `/v1/playlists`.
  4. Test clustering with a small playlist (10–20 tracks), logging results.
  5. Commit changes to GitHub.
- **Deliverables**:
  - Functional recommendation engine generating playlists based on track feature clusters.
  - Updated `README.md` with recommendation feature overview.
- **Git Commands**:
  ```bash
  git add .
  git commit -m "Implemented AI-lite recommendation engine with k-means clustering"
  git push origin main
  ```

### Week 6–7 (August 9–22, 2025): Cybersecurity Features
- **Goal**: Add secure token storage and network monitoring.
- **Tasks**:
  1. Create `security/TokenManager.java` to encrypt and store OAuth tokens using `javax.crypto` (e.g., AES encryption).
  2. Implement `security/NetworkMonitor.java` to check connectivity (e.g., ping `8.8.8.8`), logging issues to `logs/app.log`.
  3. Integrate token refresh in `SpotifyAuthService.java` with secure storage.
  4. Test token refresh and network checks, logging results.
  5. Commit changes to GitHub.
- **Deliverables**:
  - Secure token management and network monitoring functionality.
  - Updated `README.md` with cybersecurity feature details.
- **Git Commands**:
  ```bash
  git add .
  git commit -m "Added cybersecurity features: secure token storage and network monitoring"
  git push origin main
  ```

### Week 8–9 (August 23–September 5, 2025): Console UI
- **Goal**: Develop a user-friendly console interface.
- **Tasks**:
  1. Create `ui/ConsoleUI.java` to prompt for user input (e.g., select playlist, choose mood like “upbeat” or “chill”).
  2. Integrate with `SpotifyAuthService`, `SpotifyPlaylistService`, and `PlaylistGenerator` for a seamless workflow.
  3. Test the full workflow (auth, fetch, generate, display playlist).
  4. Commit changes to GitHub.
- **Deliverables**:
  - Functional console UI for user interaction.
  - Updated `README.md` with UI usage instructions.
- **Git Commands**:
  ```bash
  git add .
  git commit -m "Implemented console UI for user interaction"
  git push origin main
  ```

### Week 10–12 (September 6–30, 2025): Polish and Documentation
- **Goal**: Finalize tests, documentation, and optional visualization for a professional GitHub repo.
- **Tasks**:
  1. Write unit tests for `SpotifyAuthService`, `ClusteringEngine`, and other key classes in `src/test/java/`.
  2. Update `README.md` with comprehensive setup, usage, and feature documentation for Spotify recruiters.
  3. (Optional) Add visualization (e.g., scatter plot of track features using JFreeChart) in `ui/VisualizationUI.java`.
  4. Ensure a clean GitHub history with clear commit messages.
  5. Commit final changes.
- **Deliverables**:
  - Complete unit tests covering core functionality.
  - Polished `README.md` showcasing features (OAuth, API integration, AI-lite clustering, cybersecurity).
  - Optional visualization feature.
- **Git Commands**:
  ```bash
  git add .
  git commit -m "Finalized project with unit tests, documentation, and optional visualization"
  git push origin main
  ```

## Notes
- **Current Progress (July 4, 2025)**: Working on resolving “domain not secure” error for `http://127.0.0.1:8888/callback` in OAuth flow.
- **GitHub**: Commit changes after each major task to maintain a clean history.
- **Cybersecurity**: Ensure `application.properties` remains in `.gitignore` to protect Spotify API credentials.
- **Support**: For each task, request specific code (e.g., “Provide `SpotifyPlaylistService.java`”), share error logs from `logs/app.log`, or ask for debugging help to stay on track.