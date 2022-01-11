package com.example.hw03;
//HW3
//Weather.Java
//Evan Hemming and Zaccary Hudson
public class Weather {
    public String city;
    public String country;
    public String temp;
    public String temp_min;
    public String temp_max;
    public String desc;
    public String humidity;
    public String wind_speed;
    public String wind_deg;
    public String cloud;
    public String icon;

    public Weather(String city, String country, String temp, String temp_min, String temp_max, String desc, String humidity, String wind_speed, String wind_deg, String cloud, String icon) {
        this.city = city;
        this.country = country;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.desc = desc;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.cloud = cloud;
        this.icon = icon;
    }

}
