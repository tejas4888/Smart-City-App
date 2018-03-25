package com.example.tejas.smartcityapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejas.smartcityapp.Adapters.PollsSurveyTabAdapter;
import com.example.tejas.smartcityapp.Fragments.AlertsFragment;
import com.example.tejas.smartcityapp.Fragments.MainFragment;
import com.example.tejas.smartcityapp.Fragments.NewsFragment;
import com.example.tejas.smartcityapp.Fragments.PollsFragment;
import com.example.tejas.smartcityapp.Fragments.PollsSurveyTabsFragment;
import com.example.tejas.smartcityapp.Fragments.ProjectTabsFragment;
import com.example.tejas.smartcityapp.Fragments.ReportListFragment;
import com.example.tejas.smartcityapp.HelperClasses.AppConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Stack;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fm;
    String backStageName;

    private FirebaseAuth firebaseAuth;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else
        {
            SharedPreferences prefs = getSharedPreferences(AppConstants.LOGIN_PREFS, MODE_PRIVATE);
            String form_status = prefs.getString(firebaseAuth.getCurrentUser().getEmail(), "0");

            if (form_status.equals("0"))
            {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }
        }

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView navEmail = (TextView) headerView.findViewById(R.id.nav_header_email);
        ImageView navImage = (ImageView) headerView.findViewById(R.id.nav_header_image);

        if (savedInstanceState==null)
        {
            fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            MainFragment mainFragment = MainFragment.newInstance();
            transaction.replace(R.id.main_fragment_container, mainFragment).commit();
        }

        try{
            navUsername.setText(firebaseAuth.getCurrentUser().getDisplayName());
            navEmail.setText(firebaseAuth.getCurrentUser().getEmail());
            Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(navImage);
        }catch (Exception e) {        }
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int itemId = item.getItemId();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (itemId == R.id.nav_home){
            boolean isFragmentInStack = fm.popBackStackImmediate(backStageName, 0);
            if (!isFragmentInStack) {
                MainFragment fragment = MainFragment.newInstance();
                fragmentTransaction.replace(R.id.main_fragment_container, fragment);
                backStageName = fragment.getClass().getName();
                fragmentTransaction.addToBackStack(backStageName).commit();
            }
        }
        else if (itemId == R.id.nav_projects){
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new ProjectTabsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_alerts){
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container,new AlertsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_events){

        }
        else if (itemId == R.id.nav_innovation){

        }
        else if (itemId == R.id.nav_news){
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new NewsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_surveys){
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new PollsSurveyTabsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_reporting) {
            Intent intent = new Intent(MainActivity.this, ReportFillActivity.class);
            startActivity(intent);
        }
        else if (itemId == R.id.nav_myprojects){
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new ProjectTabsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_myreports){
            /*getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new ProjectTabsFragment());
            fragmentTransaction.addToBackStack(null).commit();
            */
        }
        else if (itemId == R.id.nav_watchlist){
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new ProjectTabsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (itemId == R.id.nav_updateprofile){
            /*getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.main_fragment_container, new ProjectTabsFragment());
            fragmentTransaction.addToBackStack(null).commit();
            */
        }
        else if (itemId == R.id.nav_contactus){

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}