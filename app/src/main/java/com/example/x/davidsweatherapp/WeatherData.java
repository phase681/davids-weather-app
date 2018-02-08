/**
 * Created by David Mahen
 *
 * This class contains all the values from the JSON String received from OpenStreetMap as an object.
 * The raw JSON String is parsed into objects, their nested objects and arrays.  The relevant
 * data is saved able to be accessed for displaying in the modal dialog.
 *
 **/
package com.example.x.davidsweatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherData {
    private Double temp;
    private Double windSpeed;
    private Double lat;
    private Double lon;
    private Double humidity;
    private String weatherMain;
    private String weatherDescription;
    private String name;

    /**
     * @param rawJson
     * This constructor parses the raw JSON and initializes the class variables.
     **/
    public WeatherData(String rawJson){
        Double kelvinTemp = 0.0;
        Double windMps = 0.0;
        try{
            JSONObject report = new JSONObject(rawJson);
            JSONArray weather = new JSONArray(report.getString("weather"));
            name = report.get("name").toString();
            lat = Double.parseDouble(new JSONObject(report.getString("coord"))
                    .getString("lat"));
            lon = Double.parseDouble(new JSONObject(report.getString("coord"))
                    .getString("lon"));
            windMps = Double.parseDouble(new JSONObject(report.getString("wind"))
                    .getString("speed"));
            kelvinTemp = Double.parseDouble(new JSONObject(report.getString("main"))
                    .getString("temp"));
            humidity = Double.parseDouble(new JSONObject(report.getString("main"))
                    .getString("humidity"));
            weatherMain = weather.getJSONObject(0).getString("main");
            weatherDescription = weather.getJSONObject(0).getString("description");
        }catch(JSONException e){
            e.printStackTrace();
        }
        // The temperature is received as Kelvin and thus needs to be converted to Fahrenheit since
        // the app is focused on areas of the continental United States.
        temp = (((kelvinTemp-273)*(9/5))+32);
        // The wind speed is received as meters per second and is converted to miles per hour for
        // use in the continental United States.
        windSpeed = windMps*(3600/1600);
    }

    public Double getTemp() {return temp;}

    public Double getWindSpeed() {return windSpeed;}

    public Double getLat() {return lat;}

    public Double getLon() {return lon;}

    public Double getHumidity() {return humidity;}

    public String getWeatherMain() {return weatherMain;}

    public String getWeatherDescription() {return weatherDescription;}

    public String getName() {return name;}
}
