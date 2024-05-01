package com.example.githmidiaryapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class Splash_screen_interface extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_interface);

        Handler handler = new Handler();
        sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sharedPreferences.getString("User_password", "").isEmpty()) {
                    startActivity(new Intent(Splash_screen_interface.this, Main_menue_interface.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash_screen_interface.this, Login_interface.class));
                    finish();
                }
            }
        }, 2000);
        //starts with splash screen "Welcome to my Diary" and waits for 2000 ms and then directed to main menue screen


    }
}