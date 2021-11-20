package com.aftebi.mynews.model;

import java.util.List;

public class WeatherForAdapter {
    private List<Weather> weatherList;
    private List<WeatherType> weatherTypeList;

    public WeatherForAdapter() {
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public List<WeatherType> getWeatherTypesList() {
        return weatherTypeList;
    }

    public void setWeatherWraps(List<WeatherType> weatherTypeList) {
        this.weatherTypeList = weatherTypeList;
    }
}
