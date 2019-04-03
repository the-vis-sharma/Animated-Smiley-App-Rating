package com.techbuddy.animatedsmileyapprating;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techbuddy.animatedsmileyrating.AnimatedSmileyRating;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimatedSmileyRating smileyRating = new AnimatedSmileyRating(this, 5, "cs.techbuddy@gmail.com");
        smileyRating.setInitialSession(3);
        smileyRating.setThreshold(3);
        smileyRating.setFeedbackEmailSubject("Recovery Bin - Feedback");
        smileyRating.show();

    }
}
