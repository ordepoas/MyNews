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

import com.aftebi.mynews.R;
import com.aftebi.mynews.adapter.AdapterNews;
import com.aftebi.mynews.adapter.AdapterTechNews;
import com.aftebi.mynews.databinding.ActivityTechNewsBinding;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.TechNews;
import com.aftebi.mynews.service.AppExecutors;
import com.aftebi.mynews.service.RecyclerItemClickListener;
import com.aftebi.mynews.service.TechNewsParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

public class TechNewsActivity extends AppCompatActivity {

    private ActivityTechNewsBinding binding;
    private List<TechNews> techNews = new ArrayList<>();
    private TechNewsParser techNewsParser;
    private AdapterTechNews techNewsAdapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTechNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //---- config init
        getSupportActionBar().setTitle("Tecnologia");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_layout);

        AlertDialog dialog = builder.create();

        //https://rss.tecmundo.com.br/feed/
        //https://4gnews.pt/feed/
        //https://www.cnet.com/rss/news/
        //https://wwww.noticiasaominuto.com/rss/tech
        try {
            URL url = new URL("https://wwww.noticiasaominuto.com/rss/tech/");
            techNewsParser = new TechNewsParser(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        dialog.show();

        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {

                //techNews = parseXML();
                techNewsParser.parseXML();
                techNews = techNewsParser.getTechNewsList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadTechNews(techNews);
                                            }
                });
                dialog.dismiss();
            }
        });



    }

    public void loadTechNews(List<TechNews> tn){

        //Config Recycler view
        //---- Config Adapter
        techNewsAdapter = new AdapterTechNews(tn, TechNewsActivity.this);
        //---- Comfig Recycler View
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.techNewRecyclerView.setLayoutManager(layoutManager);
        binding.techNewRecyclerView.setHasFixedSize(true);
        binding.techNewRecyclerView.setAdapter(techNewsAdapter);

        binding.techNewRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                binding.techNewRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        TechNews techNewsItem = tn.get(position);
                        Intent intent = new Intent(TechNewsActivity.this, TechNewsDetailActivity.class);
                        intent.putExtra("techNewsItem", techNewsItem);
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

    /*
    public List<TechNews> parseXML() {
        XmlPullParserFactory parserFactory;
        List<TechNews> techNewsList = new ArrayList<>();

        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();

            URL url = new URL("https://4gnews.pt/feed/");
            InputStream is = url.openConnection().getInputStream();
            //InputStream is = getAssets().open("data.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            techNewsList = processParsing(parser);

        } catch (XmlPullParserException e) {

        } catch (IOException e) {
        }

        return techNewsList;
    }

    private List<TechNews> processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        List<TechNews> techNewsList = new ArrayList<>();
        int eventType = parser.getEventType();
        TechNews currentTechNews = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("item".equals(eltName)) {
                        currentTechNews = new TechNews();
                        techNewsList.add(currentTechNews);
                    } else if (currentTechNews != null) { //title, link, description, category, pubdate
                        if ("title".equals(eltName)) {
                            //currentTechNews.name = parser.nextText(); Para Referência
                            currentTechNews.setTitle(parser.nextText());
                        } else if ("link".equals(eltName)) {
                            currentTechNews.setLink(parser.nextText());
                        } else if ("description".equals(eltName)) {
                            currentTechNews.setDescription(parser.nextText());
                        } else if ("category".equals(eltName)) {
                            currentTechNews.setCategory(parser.nextText());
                        } else if("pubDate".equals(eltName)){
                            currentTechNews.setPubDate(parser.nextText());
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }
        return techNewsList;
    }

     */
}