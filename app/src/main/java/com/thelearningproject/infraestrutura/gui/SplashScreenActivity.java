package com.thelearningproject.infraestrutura.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.thelearningproject.R;

/**
 * The type Splash screen activity.
 */
public class SplashScreenActivity extends AppCompatActivity {
    private static final int DOIS_MIL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, DOIS_MIL);
    }

    private void mostrarLogin() {
        Intent intent = new Intent(SplashScreenActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }
}
