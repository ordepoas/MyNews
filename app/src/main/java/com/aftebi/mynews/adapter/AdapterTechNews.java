package com.aftebi.mynews.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.activity.TechNewsActivity;
import com.aftebi.mynews.model.Task;
import com.aftebi.mynews.model.TechNews;

import java.util.List;

public class AdapterTechNews extends RecyclerView.Adapter<AdapterTechNews.MyViewHolder> {

    List<TechNews> techNewsList;
    Context context;

    public AdapterTechNews(List<TechNews> techNewsList, Context context) {
        this.techNewsList = techNewsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tech_news, parent, false);
        return new MyViewHolder(itemList);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TechNews t = techNewsList.get(position);

        String title = t.getTitle();
        title.replaceAll("&quot;", "\"");

        holder.titleTechNews.setText(Html.fromHtml(title));
        holder.descriptionTechNews.setText(Html.fromHtml(t.getDescription()));
        holder.tagTechNews.setText(t.getCategory());

    }


    @Override
    public int getItemCount() {
        return techNewsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTechNews, descriptionTechNews, tagTechNews;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleTechNews = itemView.findViewById(R.id.titleTechNewsTextView);
            descriptionTechNews = itemView.findViewById(R.id.techNewsTextView);
            tagTechNews = itemView.findViewById(R.id.tagTechNewsTextView);
        }

    }

}