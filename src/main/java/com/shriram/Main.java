package com.shriram;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        // Create and start a new Javalin web server on port 7071
        // We use a different port (7071) so it doesn't conflict with our other project.
        Javalin app = Javalin.create(config -> {
            // We will add a public folder for our frontend later
        }).start(7071);

        System.out.println("Weather server has started on port 7071!");

        // This is our new endpoint for fetching weather.
        // It listens for GET requests to the "/api/weather" address.
        app.get("/api/weather", ctx -> {
            // Get the city name from the "query parameter" in the URL.
            String city = ctx.queryParam("city");

            // Print the city name to the console to confirm we received it.
            System.out.println("Received weather request for city: " + city);

            // Send a confirmation message back to the browser.
            ctx.result("Received request for city: " + city);
        });
    }
}