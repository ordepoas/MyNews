package com.aftebi.mynews.adapter;

import android.content.Context;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.model.SportNews;

import java.util.List;

public class AdapterSportNews extends RecyclerView.Adapter<AdapterSportNews.MyViewHolder>{


    List<SportNews> sportNewsList;
    Context context;

    public AdapterSportNews(List<SportNews> sportNewsList, Context context) {
        this.sportNewsList = sportNewsList;
        this.context = context;
    }

    @Override
    public AdapterSportNews.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sport_news, parent, false);
        return new AdapterSportNews.MyViewHolder(itemList);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(AdapterSportNews.MyViewHolder holder, int position) {
        SportNews sn = sportNewsList.get(position);

        String title = sn.getTitle();
        String description = sn.getDescription();
        title.replaceAll("&quot;", "\"");
        description.replaceAll("&quot;", "\"");

        holder.titleSportNews.setText(Html.fromHtml(title));
        holder.descriptionSportNews.setText(Html.fromHtml(description));
        holder.tagSportNews.setText(sn.getCategory());

    }

    @Override
    public int getItemCount() {
        return sportNewsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleSportNews, tagSportNews, descriptionSportNews;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleSportNews = itemView.findViewById(R.id.titleSportNewsTextView);
            tagSportNews = itemView.findViewById(R.id.tagSportNewsTextView);
            descriptionSportNews = itemView.findViewById(R.id.sportNewsTextView);
        }

    }

}
