package com.example.githmidiaryapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class Main_menue_interface extends AppCompatActivity {

    static String welcome_text_user_name;
    Button view_diary_btn_id, add_entry_btn_id, settings_btn_id;
    TextView welcome_text_id;
    ImageView user_image_id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menue_interface);

        //assigning XML components with class variables
        view_diary_btn_id = findViewById(R.id.view_diary_btn_id);
        add_entry_btn_id = findViewById(R.id.add_entry_btn_id);
        settings_btn_id = findViewById(R.id.settings_btn_id);
        welcome_text_id = findViewById(R.id.welcome_text_id);
        user_image_id = findViewById(R.id.user_image_id);

        sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        change_user_name_and_title();
        random_image();


        view_diary_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), View_diary_interface.class);
                startActivity(intent);

            }
        });

        add_entry_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_new_diary_interface.class);
                startActivity(intent);

            }
        });

        settings_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Settings_interface.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update welcome text when activity resumes
        change_user_name_and_title();
    }

    void random_image() {

        Random random = new Random();// Create an instance of Random class

        int randomNumber = random.nextInt(5) + 1; // Generate a random integer from 1 to 5

        String drawableName = "pb" + randomNumber; // Construct the drawable name
        int resourceId = getResources().getIdentifier(drawableName, "drawable", getPackageName());// Get the resource identifier for the drawable
        user_image_id.setImageResource(resourceId);// Set the drawable to the ImageView
    }

    void change_user_name_and_title() {
        welcome_text_user_name = sharedPreferences.getString("User_name", "");
        if (welcome_text_user_name.isEmpty()) {
            welcome_text_id.setText("Welcome\t!");
        } else {
            welcome_text_id.setText("Welcome\t " + welcome_text_user_name + " \t!");
        }
    }
}