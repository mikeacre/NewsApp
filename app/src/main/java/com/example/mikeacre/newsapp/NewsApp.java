package com.example.mikeacre.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.content.SharedPreferences;


import java.util.ArrayList;
import java.util.List;

public class NewsApp extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>>{

    public static final String API_URL = "http://content.guardianapis.com/search";
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        Log.e("resume","resume");
        getLoaderManager().restartLoader(1, null, this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_app);
        if(checkInternet())
            getLoaderManager().initLoader(1, null, this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkInternet(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String subject = sharedPrefs.getString(
                getString(R.string.news_subject_key),
                getString(R.string.subject_default));
        String andor = sharedPrefs.getString(
                getString(R.string.andor_value_key),
                getString(R.string.andor_value_default)
        );
        boolean exactmatch = sharedPrefs.getBoolean(getString(R.string.exact_match_key), false);

        String secondsearch = sharedPrefs.getString(getString(R.string.second_search_subject_key), getString(R.string.second_search_subject_default));

        String query = subject;
        if(secondsearch != null) {
            if(exactmatch)
                query = "\""+query+"\"" + " " +andor +" "+"\""+secondsearch+"\"";
            else
                query = query + " " +andor +" "+secondsearch;
        }
        else if(exactmatch)
            query = "\""+query+"\"";

        Uri baseUri = Uri.parse(API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", query);
        uriBuilder.appendQueryParameter("api-key", "test");

        if (exactmatch)
            Log.e("yes", "it is checked");
        else
            Log.e("no","it is not");


        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {
        //ProgressBar progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        //progressBar.setVisibility(View.GONE);
        displayArticles(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

    }

    public void displayArticles(List<Article> articles){
        final ArrayList<Article> finArticles = (ArrayList<Article>) articles;
        ListView listView = (ListView) findViewById(R.id.articlelist);
        ArticleAdapter adapter = new ArticleAdapter(this, finArticles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Article earthquake = finArticles.get(position);
                String url = earthquake.getmUrl();
                Intent openMore = new Intent(Intent.ACTION_VIEW);
                openMore.setData(Uri.parse(url));
                startActivity(openMore);
            }
        });

    }

}
