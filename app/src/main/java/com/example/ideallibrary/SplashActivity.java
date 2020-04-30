package com.example.ideallibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ideallibrary.entities.Favorite;
import com.example.ideallibrary.entities.Favorites;
import com.example.ideallibrary.entities.FilterPreferences;
import com.example.ideallibrary.repository.SharedPreferencesRepository;
import com.example.ideallibrary.utilities.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        FilterPreferences filter = getFilterPreferences();

        if (filter == null) {
            createDefaultFilterPreferences();
        }

        Favorites favorites = getFavorites();

        if (favorites == null) {
            createEmptyFavorites();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);


    }


    private FilterPreferences getFilterPreferences() {
        String filterJson = SharedPreferencesRepository.getSharedPreferences(SplashActivity.this, Constants.SHARED_FILTER);
        Gson gson = new Gson();
        FilterPreferences filter = gson.fromJson(filterJson, FilterPreferences.class);
        return filter;
    }

    private Favorites getFavorites() {
        String favoritesJson = SharedPreferencesRepository.getSharedPreferences(SplashActivity.this, Constants.SHARED_FAVORITE);
        Gson gson = new Gson();
        Favorites favorites = gson.fromJson(favoritesJson, Favorites.class);
        return favorites;
    }

    private void createDefaultFilterPreferences() {

        FilterPreferences defaultFilter = new FilterPreferences();
        defaultFilter.setByAuthor(false);
        defaultFilter.setByCountry(false);
        defaultFilter.setByPages(false);
        defaultFilter.setByYear(true);
        defaultFilter.setNbMaxOfPages(Constants.MAX_PAGES);
        defaultFilter.setShowRead(true);
        defaultFilter.setShowNotRead(true);
        defaultFilter.setOrderUp(true);
        defaultFilter.setOrderDown(false);

        Gson gson = new Gson();
        String filterJson = gson.toJson(defaultFilter);
        SharedPreferencesRepository.insertSharedPreferences(SplashActivity.this, Constants.SHARED_FILTER, filterJson);
    }

    private void createEmptyFavorites() {

        Favorites favorites = new Favorites();
        Gson gson = new Gson();
        String favoritesJson = gson.toJson(favorites);
        SharedPreferencesRepository.insertSharedPreferences(SplashActivity.this, Constants.SHARED_FAVORITE, favoritesJson);
    }
}
