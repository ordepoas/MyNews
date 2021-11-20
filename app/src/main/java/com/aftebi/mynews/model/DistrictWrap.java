package com.aftebi.mynews.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DistrictWrap {
    private String owner;
    private String country;
    private List<District> data = new ArrayList<>();

    public DistrictWrap() {
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

    public List<District> getDistricts() {
        return data;
    }

    public void setDistricts(List<District> districts) {
        this.data = data;
    }
}
