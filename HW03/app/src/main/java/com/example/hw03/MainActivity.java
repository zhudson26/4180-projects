package com.example.hw03;
//HW3
//MainActivity.Java
//Evan Hemming and Zaccary Hudson
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.midtermapp.Data;
import com.example.hw03.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements CitiesFragment.IListener, WeatherFragment.IListener{

    private final OkHttpClient client = new OkHttpClient();
    CitiesFragment citiesFragment = new CitiesFragment();
    WeatherFragment weatherFragment = new WeatherFragment();
    ForecastFragment forecastFragment = new ForecastFragment();
    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, citiesFragment, "CitiesFragment")
                .commit();
    }

    @Override
    public void goToWeather(Data.City city){
        weatherFragment.setCity(city);
        getWeather(city);
    }


    public void getWeather(Data.City city){
       Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=" + city.getCity() + "&appid=" + API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());

                        JSONObject jsonWeather = json.getJSONArray("weather").getJSONObject(0);
                        JSONObject jsonMain = json.getJSONObject("main");
                        JSONObject jsonSys = json.getJSONObject("sys");
                        JSONObject jsonWind = json.getJSONObject("wind");
                        JSONObject jsonClouds = json.getJSONObject("clouds");

                        Weather weatherInfo = new Weather(json.getString("name"), jsonSys.getString("country"),
                                jsonMain.getString("temp"), jsonMain.getString("temp_min"),
                                jsonMain.getString("temp_max"), jsonWeather.getString("description"),
                                jsonMain.getString("humidity"), jsonWind.getString("speed"),
                                jsonWind.getString("deg"), jsonClouds.getString("all"),
                                jsonWeather.getString("icon"));
                        updateWeather(weatherInfo);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    public void getForecast(Weather weather){
        Request request = new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/forecast?q="+ weather.city + "&appid=" + API_KEY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        ArrayList<Forecast> forecastArrayList = new ArrayList<>();
                        JSONObject json = new JSONObject(response.body().string());
                        JSONArray jsonArray = json.getJSONArray("list");

                        for(int i = 0; i < 6; i++){
                            JSONObject arrObject = jsonArray.getJSONObject(i);
                            JSONObject main = arrObject.getJSONObject("main");
                            JSONObject weather = arrObject.getJSONArray("weather").getJSONObject(0);

                            Forecast forecast = new Forecast(main.getString("temp"),
                                    main.getString("temp_min"), main.getString("temp_max"),
                                    weather.getString("description"), main.getString("humidity"),
                                    weather.getString("icon"), arrObject.getString("dt_txt"));
                            forecastArrayList.add(forecast);
                        }
                        updateForecast(forecastArrayList);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public void updateWeather(Weather weather){
        weatherFragment.setWeather(weather);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, weatherFragment, "WeatherFragment")
                .addToBackStack(null)
                .commit();
    }

    public void goToForecast(Weather weather){
        forecastFragment.setWeather(weather);
        getForecast(weather);
    }

    public void updateForecast(ArrayList<Forecast> forecastArrayList){
        forecastFragment.setForecastArray(forecastArrayList);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, forecastFragment, "ForecastFragment")
                .addToBackStack(null)
                .commit();
    }
}