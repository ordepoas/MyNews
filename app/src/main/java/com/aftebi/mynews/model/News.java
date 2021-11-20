package com.aftebi.mynews.model;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.aftebi.mynews.api.DataService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News implements Serializable {

    private int id;
    private String title;
    private String publish_date;
    private String tag;
    private String lead;
    private String image;
    private String image16x9;
    private String url;
    private List<NewsAuthors> authors;

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage16x9() {
        return image16x9;
    }

    public void setImage16x9(String image16x9) {
        this.image16x9 = image16x9;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NewsAuthors> getAuthors() {
        return authors;
    }

    public void setAuthors(List<NewsAuthors> authors) {
        this.authors = authors;
    }
}
