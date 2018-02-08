/**
 * Created by David Mahen
 *
 * This class is a dialog fragment for setting up and handling events for the modal dialog
 * that will report local weather.
 *
 **/

package com.example.x.davidsweatherapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * This method sets arguments for the modal dialog that are passed in from the caller.
 **/
public class WeatherDialog extends DialogFragment {
    public static WeatherDialog newInstance(String title, WeatherData data){
        WeatherDialog w = new WeatherDialog();
        Bundle args = new Bundle();

        DecimalFormat df = new DecimalFormat(".#");
        args.putString("temp", df.format(data.getTemp()));
        args.putString("location", data.getName());
        args.putString("lat", data.getLat().toString());
        args.putString("lon", data.getLon().toString());
        args.putString("weatherMain", data.getWeatherMain());
        args.putString("weatherDescription", data.getWeatherDescription());
        args.putString("humidity", data.getHumidity().toString());
        args.putString("wind", data.getWindSpeed().toString());
        w.setArguments(args);
        return w;
    }

    /**
     * @param savedInstanceState
     * @return
     * This method setups up the modal dialog, creating event handling for closing the dialog and
     * and assign local weather data to dialog's view.
     **/
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}});

        // Create layout inflater to tie the weather_dialog layout to the alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View layout = inflater.inflate(R.layout.weather_dialog, null);

        TextView locationText = (TextView) layout.findViewById(R.id.locationText);
        TextView descriptionText = (TextView) layout.findViewById(R.id.descriptionText);
        TextView humidityText = (TextView) layout.findViewById(R.id.humidityText);
        TextView windText = (TextView) layout.findViewById(R.id.windText);
        TextView tempText = (TextView) layout.findViewById(R.id.tempText);

        // Create Strings that will directly set text views.
        String location = getArguments().getString("location") + " ("
                + getArguments().getString("lat") + "/" + getArguments().getString("lon")
                + ")";
        String description = getArguments().getString("weatherMain") + ":"
                + getArguments().getString("weatherDescription");
        String humidity = "Humidity: " + getArguments().getString("humidity") + "%";
        String wind = "Wind: " + getArguments().getString("wind") + "mph";
        String temp = getArguments().getString("temp");

        // Set the dialog text views.
        locationText.setText(location);
        descriptionText.setText(description);
        humidityText.setText(humidity);
        windText.setText(wind);
        tempText.setText(temp);
        // Tie the prepared layout to the dialog.
        builder.setView(layout);

        return builder.create();
    }
}
