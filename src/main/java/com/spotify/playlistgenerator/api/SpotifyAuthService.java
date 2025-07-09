package com.spotify.playlistgenerator.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;

public class SpotifyAuthService {
    private static final Logger logger = LogManager.getLogger(SpotifyAuthService.class);
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(System.getProperty("spotify.client.id"))
            .setClientSecret(System.getProperty("spotify.client.secret"))
            .setRedirectUri(SpotifyHttpManager.makeUri("http://127.0.0.1:8888/callback"))
            .build();
    private String authCode;
    private static final long TIMEOUT_SECONDS = 60;

    public void authenticate(){
        try{
            //Start HTTP server for callback
            Server server = new Server(8888);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            context.addServlet(new ServletHolder(new CallbackServlet()), "/callback");
            server.start();
            logger.info("Started HTTP server on http://127.0.0.1:8888/callback");

            //Get authorization URL
            AuthorizationCodeUriRequest authRequest = spotifyApi
                    .authorizationCodeUri()
                    .scope( "playlist-read-private " +
                            "playlist-modify-private " +
                            "playlist-modify-public " +
                            "user-read-recently-played " +
                            "user-read-private"
                    ).build();
            URI uri = authRequest.execute();
            logger.info("Open this URL in your browser: {}", uri);

            // Wait for callback with timeout
            long startTime = System.currentTimeMillis();
            while(authCode == null && (System.currentTimeMillis() - startTime) < TimeUnit.SECONDS.toMillis(TIMEOUT_SECONDS)) {
                Thread.sleep(1000);
            }

            if (authCode == null) {
                logger.error("Authentication timed out after {} seconds. No authorization code received.", TIMEOUT_SECONDS);
                throw new RuntimeException("Authentication timed out");
            }

            server.stop();
            logger.info("Stopped HTTP server");

            // Exchange code for access token
            AuthorizationCodeRequest codeRequest = spotifyApi.authorizationCode(authCode).build();
            AuthorizationCodeCredentials credentials = codeRequest.execute();
            spotifyApi.setAccessToken(credentials.getAccessToken());
            logger.info("Access token obtained: {}", credentials.getAccessToken());
        } catch (Exception e) {
            logger.error("Authentication failed: {}", e.getMessage(), e);
        }
    }

    public SpotifyApi getSpotifyApi() {
        return spotifyApi;
    }

    private class CallbackServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            authCode = req.getParameter("code");
            if (authCode != null) {
                logger.info("Received authorization code: {}", authCode);
                resp.getWriter().write("Authentication successful! You can close this window.");
            } else {
                logger.error("No authorization code received");
                resp.getWriter().write("Authentication failed.");
            }
        }
    }
}
