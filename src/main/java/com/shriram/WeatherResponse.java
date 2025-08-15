package com.shriram;

// This class holds the main temperature data.
// The variable name "main" must exactly match the key in the JSON.
public class WeatherResponse {
    private MainData main;

    public MainData getMain() { return main; }
    public void setMain(MainData main) { this.main = main; }
}