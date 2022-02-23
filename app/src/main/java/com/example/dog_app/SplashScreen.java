package com.example.dog_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {



    private static final int TRANSITION_TIME = 5000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView welcome = findViewById(R.id.welcome_text);
        welcome.setAlpha(0);
        welcome.animate()
                .setDuration(TRANSITION_TIME)
                .alpha(1)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(i);
                    }
                });
    }
}
