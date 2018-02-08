/**
 * Created by David Mahen
 *
 * This interface contains abstract methods to be used for a listener for the OpenWeatherConsultant
 * class event.
 *
 **/
package com.example.x.davidsweatherapp;


public interface OpenWeatherListener<T> {
    public void onDataReturn(T object);
    public void onErrorReturn(Exception e);
}
