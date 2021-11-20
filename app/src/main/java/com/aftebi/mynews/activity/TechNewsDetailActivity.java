package com.aftebi.mynews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.aftebi.mynews.databinding.ActivityTechNewsBinding;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.TechNews;

import java.io.IOException;

public class TechNewsDetailActivity extends AppCompatActivity {

    private ActivityTechNewsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Tecnologia");

        WebView webView = new WebView(this);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        setContentView(webView);

        TechNews techNewsItem = (TechNews) getIntent().getSerializableExtra("techNewsItem");
        try{
            webView.loadUrl(techNewsItem.getLink());
        } catch (Exception e){
            Toast.makeText(this, "Erro ao obter a página", Toast.LENGTH_LONG);
            Log.e("Error","Error: "+e.getMessage() );
        }
        webView.loadUrl(techNewsItem.getLink());


    }
}