package com.example.hw03;
//HW3
//WeatherFragment.Java
//Evan Hemming and Zaccary Hudson
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.midtermapp.Data;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    Data.City city;
    Weather weather;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ((MainActivity) getActivity()).setTitle(R.string.current_weather);

        ImageView imageView = view.findViewById(R.id.weatherIcon);
        TextView cityText = view.findViewById(R.id.cityText);
        TextView tempText = view.findViewById(R.id.tempText);
        TextView tempEdit = view.findViewById(R.id.tempEdit);
        TextView tempMaxText = view.findViewById(R.id.tempMaxText);
        TextView tempMaxEdit = view.findViewById(R.id.tempMaxEdit);
        TextView tempMinText = view.findViewById(R.id.tempMinText);
        TextView tempMinEdit = view.findViewById(R.id.tempMinEdit);
        TextView descText = view.findViewById(R.id.descText);
        TextView descEdit = view.findViewById(R.id.descEdit);
        TextView humidityText = view.findViewById(R.id.humidityText);
        TextView humidityEdit = view.findViewById(R.id.humidityEdit);
        TextView windSpeedText = view.findViewById(R.id.windSpeedText);
        TextView windSpeedEdit = view.findViewById(R.id.windSpeedEdit);
        TextView windDegText = view.findViewById(R.id.windDegText);
        TextView windDegEdit = view.findViewById(R.id.windDegEdit);
        TextView cloudText = view.findViewById(R.id.cloudText);
        TextView cloudEdit = view.findViewById(R.id.cloudEdit);
        Button checkForecastButton = view.findViewById(R.id.checkForecastButton);

        Picasso.get().load("http://openweathermap.org/img/wn/"+weather.icon+"@2x.png").into(imageView);
        cityText.setText(weather.city+", " + weather.country);
        tempEdit.setText(kToF(Double.parseDouble(weather.temp)));
        tempMaxEdit.setText(kToF(Double.parseDouble(weather.temp_max)));
        tempMinEdit.setText(kToF(Double.parseDouble(weather.temp_min)));
        descEdit.setText(weather.desc);
        humidityEdit.setText(weather.humidity + "%");
        windSpeedEdit.setText(weather.wind_speed + " " + getString(R.string.mph));
        windDegEdit.setText(weather.wind_deg + " " + getString(R.string.degrees));
        cloudEdit.setText(weather.cloud + "%");

        checkForecastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToForecast(weather);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    public void setCity(Data.City cit){
        city = cit;
    }

    public void setWeather(Weather weat){
        weather = weat;
    }

    public String kToF(double k){
        DecimalFormat df = new DecimalFormat("#.##");
        String F = df.format((k - 273.15) * (9 / 5) + 32) + " " + getString(R.string.fahrenheit);
        return F;
    }

    WeatherFragment.IListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (WeatherFragment.IListener) context;
    }

    public interface IListener{
        public void goToForecast(Weather weather);
    }
}