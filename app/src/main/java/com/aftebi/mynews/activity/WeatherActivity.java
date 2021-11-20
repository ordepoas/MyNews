package com.aftebi.mynews.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.aftebi.mynews.R;
import com.aftebi.mynews.adapter.AdapterNews;
import com.aftebi.mynews.adapter.AdapterWeather;
import com.aftebi.mynews.api.DataService;
import com.aftebi.mynews.databinding.ActivityWeatherBinding;
import com.aftebi.mynews.model.District;
import com.aftebi.mynews.model.DistrictWrap;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.Weather;
import com.aftebi.mynews.model.WeatherForAdapter;
import com.aftebi.mynews.model.WeatherType;
import com.aftebi.mynews.model.WeatherTypeWrap;
import com.aftebi.mynews.model.WeatherWrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding;
    private Retrofit retrofitDistricts;
    private Retrofit retrofitWeatherType;
    private Retrofit retrofitWeather;
    private WeatherForAdapter weatherForAdapter = new WeatherForAdapter();
    private int spinnerPosition;
    private List<District> districts = new ArrayList<>();
    private AdapterWeather weatherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Tempo");

        //get init data;
        getDistricts();
        getWeatherType();

        binding.locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerPosition = position;
                getWeather(spinnerPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void loadAdapter(WeatherForAdapter weatherForAdapter){
        //Config Recycler view
        //---- Config Adapter
        weatherAdapter = new AdapterWeather(weatherForAdapter, WeatherActivity.this);
        //---- Comfig Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.weatherRecyclerView.setLayoutManager(layoutManager);
        binding.weatherRecyclerView.setHasFixedSize(true);
        binding.weatherRecyclerView.setAdapter(weatherAdapter);
    }

    public void getWeather(int spinnerPosition){
        int globalIdLocal;
        District district = districts.get(spinnerPosition);
        globalIdLocal = district.getGlobalIdLocal();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_layout);

        AlertDialog dialog = builder.create();

        dialog.show();

        //use retrofit
        String baseUrl = "https://api.ipma.pt/open-data/";

        retrofitWeather = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService dataService = retrofitWeather.create(DataService.class);
        Call<WeatherWrap> call = dataService.getWeather(globalIdLocal);
        call.enqueue(new Callback<WeatherWrap>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WeatherWrap> call, Response<WeatherWrap> response) {
                if(response.isSuccessful()){
                    WeatherWrap weatherWrap = response.body();
                    List<Weather> weatherList = weatherWrap.getData();
                    weatherForAdapter.setWeatherList(weatherList);
                    loadAdapter(weatherForAdapter);
                    dialog.dismiss();
                }

            }
            @Override
            public void onFailure(Call<WeatherWrap> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public void getWeatherType(){
        //use retrofit
        String baseUrl = "https://api.ipma.pt/open-data/";

        retrofitWeatherType = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService dataService = retrofitWeatherType.create(DataService.class);
        Call<WeatherTypeWrap> call = dataService.getWeatherType();
        call.enqueue(new Callback<WeatherTypeWrap>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<WeatherTypeWrap> call, Response<WeatherTypeWrap> response) {
                if(response.isSuccessful()){
                    WeatherTypeWrap weatherTypeWrap = response.body();
                    List<WeatherType> weatherTypeList = weatherTypeWrap.getData();
                    weatherForAdapter.setWeatherWraps(weatherTypeList);
                }

            }
            @Override
            public void onFailure(Call<WeatherTypeWrap> call, Throwable t) {

            }
        });
    }

    public void getDistricts(){
        //use retrofit
        String baseUrl = "https://api.ipma.pt/open-data/";

        retrofitDistricts = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService dataService = retrofitDistricts.create(DataService.class);
        Call<DistrictWrap> call = dataService.getDistrict();
        call.enqueue(new Callback<DistrictWrap>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<DistrictWrap> call, Response<DistrictWrap> response) {
                if(response.isSuccessful()){
                    DistrictWrap districtList = response.body();
                     districts = districtList.getDistricts();
                    setSpinner(districts);
                }

            }
            @Override
            public void onFailure(Call<DistrictWrap> call, Throwable t) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSpinner(List<District> districts){

        final List<String> districtsStringList = new ArrayList<>();

        for (District data: districts) {
            districtsStringList.add(data.getLocal());
        }

        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(WeatherActivity.this,
                android.R.layout.simple_spinner_item, districtsStringList);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.locationSpinner.setAdapter(districtAdapter);

        String myDistrict = "Castelo Branco"; //the value you want the position for
        District initDistrict = districts.stream().filter(x -> x.getLocal().equals(myDistrict))
                .findAny().get();
        int spinnerPosition = districts.indexOf(initDistrict);
        //set the default according to value
        binding.locationSpinner.setSelection(spinnerPosition);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}