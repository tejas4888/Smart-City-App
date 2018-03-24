package com.example.tejas.smartcityapp.Items;

/**
 * Created by dani on 23/03/2018.
 */

public class SurveyItem {

    public String id,question,rating,ans;

    public SurveyItem(String id, String question, String rating, String ans)
    {
        this.id=id;
        this.question=question;
        this.rating = rating;
        this.ans=ans;
    }
}
