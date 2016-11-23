package com.example.mikeacre.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikeacre on 11/21/2016.
 */

public class ArticleLoader extends AsyncTaskLoader<List<Article>>{

    String apiURL = null;

    public ArticleLoader(Context context, String url){
        super(context);
        apiURL = url;
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground(){
        ArrayList<Article> articles = ArticleQuery.fetchArticleData(apiURL);
        return articles;
    }
}
