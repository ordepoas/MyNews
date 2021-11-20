package com.aftebi.mynews.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.activity.WeatherActivity;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.Weather;
import com.aftebi.mynews.model.WeatherForAdapter;
import com.aftebi.mynews.model.WeatherType;
import com.aftebi.mynews.utils.DayOfTheWeek;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AdapterWeather extends RecyclerView.Adapter<AdapterWeather.MyViewHolder> {

    private WeatherForAdapter weatherForAdapter;
    private List<Weather> weatherList;
    private List<WeatherType> weatherTypeList;
    private Context context;

    public AdapterWeather(WeatherForAdapter weatherForAdapter, Context context) {
        this.weatherForAdapter = weatherForAdapter;
        this.weatherList = weatherForAdapter.getWeatherList();
        this.weatherTypeList = weatherForAdapter.getWeatherTypesList();
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_weather, parent, false);
        return new MyViewHolder(itemList);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        WeatherType weatherType = weatherTypeList.stream()
                .filter(x ->x.getIdWeatherType() == weather.getIdWeatherType())
                .findAny().get();

        String weatherDesc = weatherType.getDescIdWeatherTypePT();
        //get image for weather
        int weatherTypeId = weather.getIdWeatherType();
        String weatherId;
        if(weatherTypeId < 10){weatherId = "ic_w_ic_d_0" + weatherTypeId;}
        else {
            weatherId = "ic_w_ic_d_" + weatherTypeId;
        }

        int t = context.getResources().getIdentifier(weatherId, "drawable", context.getPackageName());

        /*
        if(weatherTypeId == 0 || weatherTypeId ==1 || weatherTypeId == -99 )
                holder.weatherImageView.setImageResource(R.drawable.sun2);
        if(weatherTypeId > 1 && weatherTypeId < 6 )
                holder.weatherImageView.setImageResource(R.drawable.cloud2);
        if(weatherTypeId > 5 && weatherTypeId < 16 )
                holder.weatherImageView.setImageResource(R.drawable.rain2);
        if(weatherTypeId == 16 || weatherTypeId == 17 || weatherTypeId == 24 || weatherTypeId == 26)
                holder.weatherImageView.setImageResource(R.drawable.fog2);
        if(weatherTypeId == 18)

         */
        holder.weatherImageView.setImageResource(t);
        //set descripton to date instead numbers
        DayOfTheWeek d = new DayOfTheWeek(context);
        String dayOfTheWeek = d.getWeek(weather.getForecastDate());
            holder.dateTextView.setText(dayOfTheWeek);


        holder.weatherDescTextView.setText(weatherDesc);
        holder.tMaxTextView.setText(weather.gettMax()+"º");
        holder.tMinTextView.setText(weather.gettMin()+"º");

    }


    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTextView, weatherDescTextView, tMaxTextView, tMinTextView;
        ImageView weatherImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            weatherDescTextView = itemView.findViewById(R.id.weatherDescTextView);
            tMaxTextView = itemView.findViewById(R.id.tMaxTextView);
            tMinTextView = itemView.findViewById(R.id.tMinTextView);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);

        }

    }

}