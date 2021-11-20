package com.aftebi.mynews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.aftebi.mynews.model.SportNews;

public class SportNewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Desporto");

        WebView webView = new WebView(this);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        setContentView(webView);

        SportNews sportNews = (SportNews) getIntent().getSerializableExtra("sportNewsItem");

        try{
            webView.loadUrl(sportNews.getLink());
        } catch (Exception e){
            Toast.makeText(this, "Erro ao obter a página", Toast.LENGTH_LONG);
            Log.e("Error","Error: "+e.getMessage() );
        }


    }
}