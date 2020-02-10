package com.example.matatustageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DriverQueue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_queue);
        getSupportActionBar().setTitle("Driver Queue");
    }
}
