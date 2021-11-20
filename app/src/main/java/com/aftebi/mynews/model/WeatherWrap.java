package com.aftebi.mynews.model;

import java.util.List;

public class WeatherWrap {

    private String owner;
    private String country;
    private List<Weather> data;
    private int globalIdLocal;
    private String dataUpdate;

    public WeatherWrap() {
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

    public List<Weather> getData() {
        return data;
    }

    public void setData(List<Weather> data) {
        this.data = data;
    }

    public int getGlobalIdLocal() {
        return globalIdLocal;
    }

    public void setGlobalIdLocal(int globalIdLocal) {
        this.globalIdLocal = globalIdLocal;
    }

    public String getDataUpdate() {
        return dataUpdate;
    }

    public void setDataUpdate(String dataUpdate) {
        this.dataUpdate = dataUpdate;
    }
}
