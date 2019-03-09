package com.techbuddy.animatedsmileyapprating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techbuddy.animatedsmileyrating.AnimatedSmileyRating;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimatedSmileyRating smileyRating = new AnimatedSmileyRating(this);
        smileyRating.setSession(3);
        smileyRating.setThreshold(3);
        smileyRating.show();

    }
}
