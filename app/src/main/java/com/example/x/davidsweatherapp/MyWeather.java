/**
 * Created by David Mahen
 *
 * This class generates a custom URL for the OpenWeatherMap api call using either
 * Lat/Lon or a zipcode.  With the URL, an AsyncTask using the OpenWeatherConsultant class.
 *
 **/

package com.example.x.davidsweatherapp;

import android.content.Context;


public class MyWeather {
    private static String openWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?";
    private final static String KEY = "b852d33e9ae37db698483f6a1b6afc45";

    private static Context appContext;

    /**
     * @param context
     * Constructor for getting the app context from the main activity to be used for
     * the LocalBroadcastManager in the OpenWeatherConsultant class.
     **/
    public MyWeather(Context context){
        appContext = context;
    }

    /**
     * @param lat
     * @param lon
     * This method generates a url and passes it to the asynchronous OpenWeatherConsultant class.
     **/
    public void getWeather(String lat, String lon){
        String url = openWeatherUrl + "lat=" + lat
                + "&lon=" + lon + "&APPID=" + KEY;
        String result = new OpenWeatherConsultant(appContext).execute(url).toString();
    }

    /**
     * @param zip
     * This method generates a url and passes it to the asynchronous OpenWeatherConsultant class.
     **/
    public void getWeather(String zip){
        String url = openWeatherUrl + "zip=" + zip + "&APPID=" + KEY;
        String result = new OpenWeatherConsultant(appContext).execute(url).toString();
    }
}
