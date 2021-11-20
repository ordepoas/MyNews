package com.aftebi.mynews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


import com.aftebi.mynews.model.News;

public class NewsDetailActivity extends AppCompatActivity {

    //private ActivityNewsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        getSupportActionBar().setTitle("Últimas Notícias");

        WebView myWebView = new WebView(this);
        WebSettings ws = myWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        setContentView(myWebView);

        News newsItem = (News) getIntent().getSerializableExtra("newsItem");
        try{
            myWebView.loadUrl(newsItem.getUrl());
        }catch(Exception e){
            Toast.makeText(this, "Erro ao obter a página", Toast.LENGTH_LONG);
            Log.e("NewsDetail", "Erro: " + e.getMessage());
        }
    }
}