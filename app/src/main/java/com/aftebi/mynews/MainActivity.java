package com.aftebi.mynews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.aftebi.mynews.activity.LoginActivity;
import com.aftebi.mynews.activity.NewsActivity;
import com.aftebi.mynews.activity.SportActivity;
import com.aftebi.mynews.activity.SportNewsActivity;
import com.aftebi.mynews.activity.TechNewsActivity;
import com.aftebi.mynews.activity.TodoActivity;
import com.aftebi.mynews.activity.WeatherActivity;
import com.aftebi.mynews.databinding.ActivityMainBinding;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.service.FirebaseConfig;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.newsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
        });

        binding.weatherButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
        });

        binding.todoButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TodoActivity.class);
            startActivity(intent);
        });

        binding.techButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, TechNewsActivity.class);
            startActivity(intent);
        });

        binding.sportButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, SportActivity.class);

            //Intent intent = new Intent(this, SportNewsActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                FirebaseConfig.getAuth().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseConfig.getAuth().getCurrentUser();
        if(user == null)
            startActivity(new Intent(this, LoginActivity.class));

    }
}