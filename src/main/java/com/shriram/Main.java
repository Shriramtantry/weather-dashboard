package com.shriram;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import io.javalin.Javalin;

public class Main {
    // Add your API key here!
    private static final String API_KEY = "1e3d61384c240b311338d33b295b3214";

    public static void main(String[] args) {
        // ... rest of the code
        // Create and start a new Javalin web server on port 7071
        // We use a different port (7071) so it doesn't conflict with our other project.
        Javalin app = Javalin.create(config -> {
            // We will add a public folder for our frontend later
        }).start(7071);

        System.out.println("Weather server has started on port 7071!");

        // This is our new endpoint for fetching weather.
        // It listens for GET requests to the "/api/weather" address.
        // This is our endpoint for fetching weather.
        app.get("/api/weather", ctx -> {
            // Get the city name from the user's request.
            String city = ctx.queryParam("city");

            // --- This is the new logic for calling the external API ---

            // 1. Build the full URL for the OpenWeatherMap API call.
            String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;

            // 2. Create a new HttpClient. This is the "phone" we will use to make the call.
            HttpClient client = HttpClient.newHttpClient();

            // 3. Create a new HttpRequest. This is the "message" we are sending.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(weatherApiUrl)) // Set the address we are calling.
                    .build(); // Build the request.

            // 4. Send the request and get the response.
            // The response body (the actual data) will be a String.
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 5. Get the raw JSON data from the response.
            String weatherJson = response.body();

            // 6. Print the raw JSON to our own console to confirm we received it.
            System.out.println("Received weather data: " + weatherJson);

            // 7. Send the raw JSON data back to the user's browser.
            ctx.header("Content-Type", "application/json"); // Tell the browser this is JSON data.
            ctx.result(weatherJson);
        });
    }
}