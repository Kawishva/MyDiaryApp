package com.example.githmidiaryapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Main_menue_interface extends AppCompatActivity {

    Button view_diary_btn_id,add_entry_btn_id,settings_btn_id;
    TextView welcome_text_id;
    SharedPreferences sharedPreferences;
    static String welcome_text_user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menue_interface);

        //assigning XML components with class variables
        view_diary_btn_id = findViewById(R.id.view_diary_btn_id);
        add_entry_btn_id = findViewById(R.id.add_entry_btn_id);
        settings_btn_id = findViewById(R.id.settings_btn_id);
        welcome_text_id = findViewById(R.id.welcome_text_id);

        sharedPreferences = getSharedPreferences("USER_DATA",MODE_PRIVATE);

        change_user_name_and_title();


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

    void change_user_name_and_title(){
        welcome_text_user_name = sharedPreferences.getString("User_name","");
        if(welcome_text_user_name.isEmpty()){
            welcome_text_id.setText("Welcome\t!");
        }else {

            welcome_text_id.setText("Welcome\t"+welcome_text_user_name+"\t!");
        }
    }
}