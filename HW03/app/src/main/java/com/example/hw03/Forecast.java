package com.example.hw03;
//HW3
//Forecast.Java
//Evan Hemming and Zaccary Hudson

public class Forecast {
    public String temp;
    public String temp_min;
    public String temp_max;
    public String desc;
    public String humidity;
    public String timestamp;
    public String icon;

    public Forecast(String temp, String temp_min, String temp_max, String desc, String humidity, String icon, String timestamp) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.desc = desc;
        this.humidity = humidity;
        this.icon = icon;
        this.timestamp = timestamp;
    }
}
