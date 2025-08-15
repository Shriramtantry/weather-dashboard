package com.shriram;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;

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
        // This is our endpoint for fetching weather.
        app.get("/api/weather", ctx -> {
            String city = ctx.queryParam("city");
            String weatherApiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric"; // Added &units=metric for Celsius

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(weatherApiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String weatherJson = response.body();

            // --- THIS IS THE NEW TRANSLATION LOGIC ---

            // 1. Create a new instance of our Gson translator.
            Gson gson = new Gson();

            // 2. Tell Gson to read the JSON string and fill out a WeatherResponse blueprint.
            WeatherResponse weatherResponse = gson.fromJson(weatherJson, WeatherResponse.class);

            // 3. Now we can easily get the specific data we want from our organized objects.
            double temperature = weatherResponse.getMain().getTemp();

            // 4. Print the clean temperature to our console to confirm it works.
            System.out.println("The temperature in " + city + " is: " + temperature + "°C");

            // 5. Send a clean, simple message back to the browser.
            ctx.result("The temperature in " + city + " is: " + temperature + "°C");
        });
    }
}