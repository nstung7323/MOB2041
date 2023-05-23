package com.example.thvinphngnam.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.thvinphngnam.R;

public class HelloActivity extends AppCompatActivity {
    ImageView bg, logo, text;
    LottieAnimationView lottieAnimationView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        bg = findViewById(R.id.iv_hello_background);
        logo = findViewById(R.id.iv_hello_logo);
        text = findViewById(R.id.iv_hello_name);
        lottieAnimationView = findViewById(R.id.lottie);

        bg.animate().translationY(-2300).setDuration(1000).setStartDelay(4000);
        logo.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        text.animate().translationY(2300).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(2300).setDuration(1000).setStartDelay(4000);

        handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(HelloActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 5000);

    }
}