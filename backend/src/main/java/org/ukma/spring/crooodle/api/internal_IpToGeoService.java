package org.ukma.spring.crooodle.api;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Service to get geographic coordinates (latitude and longitude)
 * from an external API based on the IP address
 *
 * <p>Coordinates are returned in the form of a string array:
 * [latitude, longitude].</p>
 */
@Service
public class internal_IpToGeoService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.key}")
    private String apiKey;


    public String[] getCoordinates() throws IOException {

        String longitude = "0";
        String latitude = "0";

        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    connection
                        .getInputStream()
                )
            );

            StringBuilder response = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JsonObject jsonObject = JsonParser
                                        .parseString(
                                            response.toString()
                                        )
                                        .getAsJsonObject();

            latitude = jsonObject.get("latitude").getAsString();
            longitude = jsonObject.get("longitude").getAsString();

        } else {
            System.out.println("HTTP GET request failed: " + connection.getResponseCode());
        }
        connection.disconnect();

        return new String[]{
            latitude,
            longitude
        };




    }




}










