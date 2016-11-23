package com.example.mikeacre.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mikeacre on 11/21/2016.
 */

public class ArticleAdapter extends ArrayAdapter<Article>{
    public ArticleAdapter(Context context, ArrayList<Article> articles){
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.article_item, parent, false);
        }

        Article currArticle = getItem(position);

        String title = currArticle.getTitle();
        String date = currArticle.getDate();
        String section = currArticle.getSection();

        TextView titleText = (TextView) listItemView.findViewById(R.id.article_title);
        TextView dateText = (TextView) listItemView.findViewById(R.id.article_date);
        TextView sectionText = (TextView) listItemView.findViewById(R.id.article_section);

        titleText.setText(title);
        dateText.setText(date);
        sectionText.setText(section);



        return listItemView;
    }

}
