package com.example.tejas.smartcityapp.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.example.tejas.smartcityapp.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

public class ColorsArrayBuilder {

    public static Integer[] getPageBackgroundColors(Context context, List<OnboarderPage> pages) {
        List<Integer> colorsList = new ArrayList<>();
        for (OnboarderPage page : pages) {
            colorsList.add(ContextCompat.getColor(context, page.getBackgroundColor()));
        }
        return colorsList.toArray(new Integer[pages.size()]);
    }

}
