package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class WeatherForecastActivity extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "WeatherForecastActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = this.getIntent().getExtras();
        Log.i(ACTIVITY_NAME, "in onCreate()");

        setContentView(R.layout.activity_weather_forecast2);




        WeatherForecast firstFragment = new WeatherForecast();
        firstFragment.setArguments(b);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.fullscreen_frame, firstFragment);
        ft.commit();
    }
}
