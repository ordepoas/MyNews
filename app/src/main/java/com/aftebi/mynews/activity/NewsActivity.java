package com.aftebi.mynews.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aftebi.mynews.R;
import com.aftebi.mynews.adapter.AdapterNews;
import com.aftebi.mynews.api.AsyncTask;
import com.aftebi.mynews.api.DataService;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.service.RecyclerItemClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private AdapterNews newsAdapter;
    private RecyclerView recyclerView;
    private List<News> news = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().setTitle("Últimas Notícias");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_layout);

        AlertDialog dialog = builder.create();

        dialog.show();

        recyclerView = findViewById(R.id.recyclerNews);
        //use retrofit
        String baseUrl = "https://observador.pt/wp-json/obs_api/v4/news/";

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataService dataService = retrofit.create(DataService.class);
        Call<List<News>> call = dataService.getNews();
        call.enqueue(new Callback<List<News>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if(response.code() == 200){
                    news = response.body();
                    loadNews(news);
                    dialog.dismiss();

                }

            }
            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Problema a obter dados", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


    public void loadNews(List<News> news){

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        News newsItem = news.get(position);
                        Intent intent = new Intent(NewsActivity.this, NewsDetailActivity.class);
                        intent.putExtra("newsItem", newsItem);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        //Config Recycler view
        //---- Config Adapter
        newsAdapter = new AdapterNews(news, NewsActivity.this);
        //---- Comfig Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(newsAdapter);

    }

    public InputStream getInputStream(URL url){

        try {
            InputStream inputStream = url.openConnection().getInputStream();
            return inputStream;

        } catch (IOException e) {
            return null;
        }
    }
}