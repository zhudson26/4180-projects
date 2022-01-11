package com.example.hw03;
//HW3
//ForecastFragment.Java
//Evan Hemming and Zaccary Hudson
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ForecastFragment extends Fragment {
    ForecastAdapter forecastAdapter;
    Weather weather;
    ArrayList<Forecast> forecastArrayList = new ArrayList<>();

    public ForecastFragment() {
        // Required empty public constructor
    }

    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        ((MainActivity) getActivity()).setTitle(R.string.weather_forecast);
        TextView cityName = view.findViewById(R.id.cityName);
        ListView forecastList = view.findViewById(R.id.forecastList);
        forecastAdapter = new ForecastAdapter(getContext(), R.layout.forecast_row_item, forecastArrayList);
        forecastList.setAdapter(forecastAdapter);

        cityName.setText(weather.city + ", " + weather.country);
        return view;
    }

    public void setWeather(Weather weatherInput){weather = weatherInput;}

    public void setForecastArray(ArrayList<Forecast> forecastArr){
        forecastArrayList = forecastArr;
    }

    public class ForecastAdapter extends ArrayAdapter<Forecast> {
        public ForecastAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Forecast> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_row_item, parent, false);
            }

            Forecast forecast = getItem(position);

            TextView timeEdit = convertView.findViewById(R.id.timeEdit);
            TextView forecastTempEdit = convertView.findViewById(R.id.forecastTempEdit);
            TextView forecastMaxEdit = convertView.findViewById(R.id.forecastMaxEdit);
            TextView forecastMinEdit = convertView.findViewById(R.id.forecastMinEdit);
            TextView forecastHumidityEdit = convertView.findViewById(R.id.forecastHumidityEdit);
            TextView forecastDescEdit = convertView.findViewById(R.id.forecastDescEdit);
            ImageView forecastImageView = convertView.findViewById(R.id.forecastImageView);

            timeEdit.setText(forecast.timestamp);
            forecastTempEdit.setText(kToF(Double.parseDouble(forecast.temp)));
            forecastMaxEdit.setText(kToF(Double.parseDouble(forecast.temp_max)));
            forecastMinEdit.setText(kToF(Double.parseDouble(forecast.temp_min)));
            forecastHumidityEdit.setText(forecast.humidity + "%");
            forecastDescEdit.setText(forecast.desc);

            Picasso.get().load("http://openweathermap.org/img/wn/"+forecast.icon+"@2x.png").into(forecastImageView);



            return convertView;
        }
    }

    public String kToF(double k){
        DecimalFormat df = new DecimalFormat("#.##");
        String F = df.format((k - 273.15) * (9 / 5) + 32) + " " + getString(R.string.fahrenheit);
        return F;
    }
}