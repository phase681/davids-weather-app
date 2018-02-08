/**
 * Created by David Mahen
 *
 * This class generates a custom URL for the OpenWeatherMap api call using either
 * Lat/Lon or a zipcode.  With the URL, an AsyncTask is launched using the OpenWeatherConsultant
 * class.  A listener callback is used to display the local weather results in a modal dialog
 *
 **/

package com.example.x.davidsweatherapp;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;


public class MyWeather {
    private static String openWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?";
    private final static String KEY = "b852d33e9ae37db698483f6a1b6afc45";

    private static Context appContext;
    private static FragmentManager mainMapFragment;

    /**
     * @param context
     * Constructor for getting the app context and a fragment manager from the main activity
     * to be used for implementing the listener callback interface.
     **/
    public MyWeather(Context context, FragmentManager mapFragment){
        appContext = context;
        mainMapFragment = mapFragment;
    }

    /**
     * @param url
     * This function runs the asynchronous OpenWeatherConsultant task and implements a listener
     * for the callback to parse the raw data results, then launch a modal dialog with those
     * results.
     **/
    private void consultOpenWeather(String url){
        OpenWeatherConsultant owc = new OpenWeatherConsultant(appContext, new OpenWeatherListener<String>(){
            @Override
            public void onDataReturn(String result){
                WeatherData reportData = new WeatherData(result);
                DialogFragment testDialog = WeatherDialog.newInstance("Local Weather", reportData);
                testDialog.show(mainMapFragment, "localWeather");
            }
            @Override
            public void onErrorReturn(Exception e){
                Toast.makeText(appContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        owc.execute(url.toString());
    }

    /**
     * @param lat
     * @param lon
     * This method generates a url for the OpenWeatherConsultant using Lat/Lon.
     **/
    public void getWeather(String lat, String lon){
        String url = openWeatherUrl + "lat=" + lat
                + "&lon=" + lon + "&APPID=" + KEY;
        consultOpenWeather(url);
    }

    /**
     * @param zip
     * This method generates a url for the OpenWeatherConsultant using a zipcode.
     **/
    public void getWeather(String zip){
        String url = openWeatherUrl + "zip=" + zip + "&APPID=" + KEY;
        consultOpenWeather(url);
    }
}
