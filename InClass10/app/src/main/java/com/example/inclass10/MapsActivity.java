package com.example.inclass10;

import static android.content.ContentValues.TAG;

import androidx.annotation.Dimension;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.inclass10.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import java.util.stream.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//InClass10
//Zaccary Hudson and Evan Hemming
//MapsActivity.java

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
   Point[] points;

    LatLng firstPoint;
    LatLng lastPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String json = null;
        try {
            InputStream inputStream = getAssets().open("trip.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray points = jsonObject.getJSONArray("points");
            PolylineOptions polylineOptions = new PolylineOptions();
            double minLat = 999;
            double maxLat = -999;
            double minLon = 999;
            double maxLon = -999;
            for(int i = 0; i < points.length(); i++){
                JSONObject point = points.getJSONObject(i);
                LatLng pointLatLng = new LatLng(point.getDouble("latitude"), point.getDouble("longitude"));
                polylineOptions.add(pointLatLng);
                if(i == 0){
                    mMap.addMarker(new MarkerOptions().position(pointLatLng));
                } else if(i == points.length()-1){
                    mMap.addMarker(new MarkerOptions().position(pointLatLng));
                }

                if(point.getDouble("latitude") < minLat){
                    minLat = point.getDouble("latitude");
                }
                if(point.getDouble("latitude") > maxLat){
                    maxLat = point.getDouble("latitude");
                }
                if(point.getDouble("longitude") < minLon){
                    minLon = point.getDouble("longitude");
                }
                if(point.getDouble("longitude") > maxLon){
                    maxLon = point.getDouble("longitude");
                }
            }
            firstPoint = new LatLng(minLat, minLon);
            lastPoint = new LatLng(maxLat, maxLon);
            Polyline polyline = mMap.addPolyline(polylineOptions);
            LatLngBounds pointBounds = new LatLngBounds(firstPoint, lastPoint);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    Log.i("OnMapLoaded", "Reached map load");
                    mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(pointBounds,5));
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }
}