package com.example.hw03;
//HW3
//CitiesFragment.Java
//Evan Hemming and Zaccary Hudson
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.midtermapp.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitiesFragment extends Fragment {

    CityAdapter cityAdapter;


    public CitiesFragment() {
        // Required empty public constructor
    }

    public static CitiesFragment newInstance(String param1, String param2) {
        CitiesFragment fragment = new CitiesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);

        ((MainActivity)getActivity()).setTitle(R.string.cities);

        ListView listCities = view.findViewById(R.id.listCities);
        cityAdapter = new CityAdapter(getContext(), R.layout.city_row_item, Data.cities);
        listCities.setAdapter(cityAdapter);
        listCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.goToWeather(Data.cities.get(i));
            }
        });

        return view;
    }

    public class CityAdapter extends ArrayAdapter<Data.City>{
        public CityAdapter(@NonNull Context context, int resource, @NonNull List<Data.City> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.city_row_item, parent, false);
            }

            Data.City city = getItem(position);

            TextView cityNameText = convertView.findViewById(R.id.cityNameText);

            cityNameText.setText(city.getCity() + ", " + city.getCountry());
            return convertView;

        }
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mListener = (CitiesFragment.IListener) context;
    }

    IListener mListener;
    public interface IListener{
        void goToWeather(Data.City city);
    }
}