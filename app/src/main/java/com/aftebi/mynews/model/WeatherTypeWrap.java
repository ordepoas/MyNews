package com.aftebi.mynews.model;

import java.util.List;

public class WeatherTypeWrap {

    private String owner;
    private String country;
    private List<WeatherType> data;

    public WeatherTypeWrap() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<WeatherType> getData() {
        return data;
    }

    public void setData(List<WeatherType> data) {
        this.data = data;
    }
}
