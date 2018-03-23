package com.example.tejas.smartcityapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends OnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnboarderPage onboarderPage1 = new OnboarderPage("BUILD THE SMART CITY OF YOUR DREAMS", "A UNIQUE MATCHMAKING PLATFORM ", R.drawable.img3);
                OnboarderPage onboarderPage2 = new OnboarderPage("PARTICIPATE.INNOVATE", "VIEW PROJECTS\n\nPROPOSE SMART SOLUTIONS\n\nRECEIVE EMERGENCY ALERTS", R.drawable.img6);
        OnboarderPage onboarderPage3 = new OnboarderPage("NEW FEATURES!", "PERSONALIZED NEWS UPDATES\n\nCHAT WITH THE GOVT\n\nCITIZEN REPORTING", R.drawable.img8);

        onboarderPage1.setBackgroundColor(R.color.onboarder_bg_1);
        onboarderPage2.setBackgroundColor(R.color.onboarder_bg_2);
        onboarderPage3.setBackgroundColor(R.color.onboarder_bg_3);

        List<OnboarderPage> pages = new ArrayList<>();

        pages.add(onboarderPage1);
        pages.add(onboarderPage2);
        pages.add(onboarderPage3);

        for (OnboarderPage page : pages) {
            page.setTitleColor(R.color.primary_text);
            page.setDescriptionColor(R.color.secondary_text);
            page.setMultilineDescriptionCentered(true);
        }

        setSkipButtonTitle("Skip");
        setFinishButtonTitle("Finish");

        setOnboardPagesReady(pages);

    }

    @Override
    public void onSkipButtonPressed() {
        super.onSkipButtonPressed();
        Toast.makeText(this, "Skip button was pressed!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);


    }

    @Override
    public void onFinishButtonPressed() {
        Toast.makeText(this, "Finish button was pressed", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

}
