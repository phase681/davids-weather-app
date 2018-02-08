/**
 * Created by David Mahen
 *
 * This is the main activity for the weather app.  It handles events for both the map
 * and the zipcode EditText.
 *
 **/
package com.example.x.davidsweatherapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private InputMethodManager inputManager;

    /**
     * @param savedInstanceState
     * This method sets up the view and handles all non-map related UI events.
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final EditText zipText = (EditText) findViewById(R.id.zipText);
        // Instantiate an InputMethodManager to help cleanup the keyboard when not being used.
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // Event handler for executing a weather query when the "Done" keyboard button is pressed
        // or when the "Enter" key is pressed.
        zipText.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    zipText.clearFocus();

                    // This function contains a routine that is executed more than once.
                    searchWeatherByZipcode(zipText);
                    return true;
                }else if(event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    searchWeatherByZipcode(zipText);
                    zipText.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * @param googleMap
     * This method setups up the map and handles map-click events.
     **/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set default city when app loads.
        LatLng reston = new LatLng(38.95830207876414, -77.35782891511917);
        mMap.addMarker(new MarkerOptions().position(reston).title("Marker in Reston"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(reston));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        // Display zoom controls for emulator usage.
        UiSettings settings  = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        // When the map is clicked, pass Lat/Lon to MyWeather class for a weather query.
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Clean up keyboard on search if displayed
                if(getCurrentFocus() != null){
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                final MyWeather myWeather = new MyWeather(getApplicationContext(), getSupportFragmentManager());
                String lat = Double.toString(latLng.latitude);
                String lon = Double.toString(latLng.longitude);
                myWeather.getWeather(lat, lon);
            }
        });

    }


    /**
     * @param zipText
     * This method will perform a weather query with a valid zipcode.  It performs a
     * similar call to clicking on the map.
     **/
    private void searchWeatherByZipcode(EditText zipText){
        if(zipText.length() == 5) {
            Geocoder geo = new Geocoder(getApplicationContext());
            LatLng local = null;
            // Take the entered zipcode and retrieve a Lat/Lon for that area.
            try {
                List<Address> addresses = geo.getFromLocationName(zipText.getText().toString(), 1);
                Address address = addresses.get(0);
                local = new LatLng(address.getLatitude(), address.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Move the map to match the entered zipcode while performing a weather query.
            mMap.moveCamera(CameraUpdateFactory.newLatLng(local));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            final MyWeather myWeather = new MyWeather(getApplicationContext(), getSupportFragmentManager());
            myWeather.getWeather(zipText.getText().toString());
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
