package com.aftebi.mynews.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.aftebi.mynews.adapter.AdapterSportNews;
import com.aftebi.mynews.adapter.AdapterTechNews;
import com.aftebi.mynews.databinding.ActivitySportNewsBinding;
import com.aftebi.mynews.model.SportNews;
import com.aftebi.mynews.model.TechNews;
import com.aftebi.mynews.service.AppExecutors;
import com.aftebi.mynews.service.RecyclerItemClickListener;
import com.aftebi.mynews.service.SportNewsParser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SportNewsActivity extends AppCompatActivity {

    private ActivitySportNewsBinding binding;
    private List<SportNews> sportNewsList = new ArrayList<>();
    private SportNewsParser sportNewsParser;
    private AdapterSportNews adapterSportNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySportNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //---- Config Init
        getSupportActionBar().setTitle("Desporto");

        try {
            //https://www.rtp.pt/noticias/rss/desporto/
            //https://desporto.sapo.pt/rss/
            URL url = new URL("https://www.rtp.pt/noticias/rss/desporto/");
            sportNewsParser = new SportNewsParser(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

                sportNewsParser.parseXML();
                sportNewsList = sportNewsParser.getSportNewsList();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadSportNews(sportNewsList);
                    }
                });

            }
        });
    }


    public void loadSportNews(List<SportNews> sn){

        //Config Recycler view
        //---- Config Adapter
        adapterSportNews = new AdapterSportNews(sn, SportNewsActivity.this);
        //---- Comfig Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.sportNewsRecyclerView.setLayoutManager(layoutManager);
        binding.sportNewsRecyclerView.setHasFixedSize(true);
        binding.sportNewsRecyclerView.setAdapter(adapterSportNews);

        binding.sportNewsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                binding.sportNewsRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        SportNews sportNews = sn.get(position);
                        Intent intent = new Intent(SportNewsActivity.this, SportNewsDetailActivity.class);
                        intent.putExtra("sportNewsItem", sportNews);
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

    }

}