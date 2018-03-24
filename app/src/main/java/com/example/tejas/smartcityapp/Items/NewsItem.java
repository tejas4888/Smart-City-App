package com.example.tejas.smartcityapp.Items;

/**
 * Created by Ritgvika Iyer on 23-03-2018.
 */

public class NewsItem {

   public String news_id;
   public String news_heading;
   public String news_content;

   public String date;



    public NewsItem(String news_id, String news_heading,String news_content, String date) {
        this.news_id = news_id;
        this.news_heading=news_heading;
        this.news_content = news_content;
        this.date = date;
    }
}
