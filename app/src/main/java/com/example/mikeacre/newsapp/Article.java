package com.example.mikeacre.newsapp;

import java.net.URL;

/**
 * Created by mikeacre on 11/21/2016.
 */

public class Article {
    private String mTitle;
    private String mDate;
    private String mUrl;
    private String mSection;

    public Article(String title, String date, String link, String section){
        mTitle = title;
        mDate = date;
        mUrl = link;
        mSection = section;

    }

    public String getTitle(){
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getSection () {return mSection;}
}
