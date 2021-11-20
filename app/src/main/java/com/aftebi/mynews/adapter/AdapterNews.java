package com.aftebi.mynews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.aftebi.mynews.R;
import com.aftebi.mynews.model.News;
import com.aftebi.mynews.service.AppExecutors;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.MyViewHolder> {

    List<News> news;
    Context context;

    public AdapterNews(List<News> transactions, Context context) {
        this.news = transactions;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news, parent, false);
        return new MyViewHolder(itemList);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News n = news.get(position);

        Thread t = new Thread(new Runnable() {
            Bitmap bitmap = null;
            @Override
            public void run() {

                try {
                    // Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(" http://corporate2.bdjobs.com/21329.jpg").getContent());
                    bitmap = BitmapFactory.decodeStream((InputStream) new URL(n.getImage()).getContent());
                    //  http://corporate2.bdjobs.com/21329.jpg
                    //logo.setImageBitmap(bitmap);
                    //convertView.setBackgroundResource(R.drawable.cardlayout);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(bitmap != null){
                            holder.newsimage.setImageBitmap(bitmap);
                        } else holder.newsimage.setImageResource(R.drawable.mynews);

                    }
                });

            }

        });
        t.start();
        holder.titleTextView.setText(n.getTitle());
        holder.newsTextView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        holder.newsTextView.setText(n.getLead());
        holder.tagTextView.setText(n.getTag());
    }


    @Override
    public int getItemCount() {
        return news.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, tagTextView, newsTextView;
        ImageView newsimage;

        public MyViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextView);
            newsTextView = itemView.findViewById(R.id.newsTextView);
            tagTextView = itemView.findViewById(R.id.tagTextView);
            newsimage = itemView.findViewById(R.id.newsImage);
        }

    }

}