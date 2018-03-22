package com.example.tejas.smartcityapp;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class OnboarderPage {

    public String title;
    public String description;
    private Drawable imageResource;
    @StringRes
    private int titleResourceId;
    @StringRes
    private int descriptionResourceId;
    @DrawableRes
    private int imageResourceId;
    @ColorRes
    private int titleColor;
    @ColorRes
    private int descriptionColor;
    @ColorRes
    private int backgroundColor;
    private float titleTextSize;
    private float descriptionTextSize;
    private boolean multilineDescriptionCentered;

    public OnboarderPage(String title, String description) {
        this.title = title;
        this.description = description;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(String title, String description, int imageResourceId) {
        this.title = title;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(String title, String description, Drawable imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(int title, int description) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(int title, int description, int imageResourceId) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.imageResourceId = imageResourceId;
        this.backgroundColor = R.color.black_transparent;
    }

    public OnboarderPage(int title, int description, Drawable imageResource) {
        this.titleResourceId = title;
        this.descriptionResourceId = description;
        this.imageResource = imageResource;
        this.backgroundColor = R.color.black_transparent;
    }

    public String getTitle() {
        return title;
    }

    public int getTitleResourceId() {
        return titleResourceId;
    }

    public String getDescription() {
        return description;
    }

    public int getDescriptionResourceId() {
        return descriptionResourceId;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public int getDescriptionColor() {
        return descriptionColor;
    }

    public void setTitleColor(int color) {
        this.titleColor = color;
    }

    public void setDescriptionColor(int color) {
        this.descriptionColor = color;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    public float getDescriptionTextSize() {
        return descriptionTextSize;
    }

    public void setDescriptionTextSize(float descriptionTextSize) {
        this.descriptionTextSize = descriptionTextSize;
    }

    public boolean isMultilineDescriptionCentered() {
        return multilineDescriptionCentered;
    }

    public void setMultilineDescriptionCentered(boolean multilineDescriptionCentered) {
        this.multilineDescriptionCentered = multilineDescriptionCentered;
    }
}
