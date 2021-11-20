package com.aftebi.mynews.api;

import com.aftebi.mynews.model.District;
import com.aftebi.mynews.model.DistrictWrap;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.Weather;
import com.aftebi.mynews.model.WeatherType;
import com.aftebi.mynews.model.WeatherTypeWrap;
import com.aftebi.mynews.model.WeatherWrap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataService {


    //get JSON List and Objects

    //https://observador.pt/wp-json/obs_api/v4/news/widget
    @GET("widget")
    Call<List<News>> getNews();

    //https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/{globalIdLocal}.json
    @GET("forecast/meteorology/cities/daily/{globalIdLocal}.json")
    Call<WeatherWrap> getWeather(@Path("globalIdLocal") int globalIdLocal);
    //Call<WeatherWrap> getWeather();

    //https://api.ipma.pt/open-data/distrits-islands.json
    @GET("distrits-islands.json")
    Call<DistrictWrap> getDistrict();

    //https://api.ipma.pt/open-data/weather-type-classe.json
    @GET("weather-type-classe.json")
    Call<WeatherTypeWrap> getWeatherType();


}
