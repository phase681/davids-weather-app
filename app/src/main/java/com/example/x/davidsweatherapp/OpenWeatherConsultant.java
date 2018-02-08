/**
 * Created by David Mahen
 *
 * This class executes an Asynchronous task to send an HTTP GET request to OpenWeatherMap's
 * server and receive a JSON string in return.  Then a local broadcast is made to the MapActivity
 * to send the JSON string to be parsed and displayed in a modal dialog.
 *
 **/
package com.example.x.davidsweatherapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class OpenWeatherConsultant extends AsyncTask<String, Integer, String> {
    private Context appContext;

    /**
     * @param context
     * The app context is passed in to facilitate with sending the broadcast message.
     **/
    public OpenWeatherConsultant(Context context){
        appContext = context;
    }

    /**
     * @param urls
     * @return
     * This method opens an HTTP connection to OpenWeatherMap's server and uses a buffered reader
     * to read in the return stream and return a String result.
     *
     * The main routine in this method was adapted from the implementation found here:
     * https://www.spaceotechnologies.com/implement-openweathermap-api-android-app-tutorial/
     **/
    protected String doInBackground(String... urls){
        HttpURLConnection urlConnection = null;
        InputStream returnStream = null;
        String url = urls[0];

        try{
            urlConnection = (HttpURLConnection) (new URL(url)).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();

            StringBuffer returnBuffer = null;
            String line = null;
            try{
                returnBuffer = new StringBuffer();
                returnStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(returnStream));
                while( (line = reader.readLine()) != null){returnBuffer.append(line + "\r\n");}

            }catch (IOException e){
                e.printStackTrace();
            }
            returnStream.close();
            urlConnection.disconnect();
            return returnBuffer.toString();

        }catch(Throwable t){
            t.printStackTrace();
        }finally{
            try{returnStream.close();}catch(Throwable t){}
            try{urlConnection.disconnect();}catch(Throwable t){}
        }

        return null;
    }

    /**
     * @param result
     * This method is run after the execution of the background asynchronous task.  It takes the
     * returned String (passed in automatically) and folds it into an intent and broadcasts
     * it specifically to broadcast receivers within the app.
     **/
    protected void onPostExecute(String result){
        Intent intent = new Intent("com.example.x.davidsweatherapp");
        intent.putExtra("jsonData", result);
        LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent);
    }
}
