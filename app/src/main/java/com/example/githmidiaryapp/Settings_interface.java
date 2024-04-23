package com.example.githmidiaryapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Settings_interface extends AppCompatActivity {

    EditText enter_name_text_id,enter_pswd_text_id;
    static TextView password_text,name_text;
    Button name_save_btn_id,pswd_save_btn_id;

    static String user_name,user_password;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_interface);

        //assigning XML components with class variables
        enter_name_text_id = findViewById(R.id.enter_name_text_id);
        name_text = findViewById(R.id.name_text);
        name_save_btn_id = findViewById(R.id.name_save_btn_id);
        password_text = findViewById(R.id.password_text);
        enter_pswd_text_id = findViewById(R.id.enter_pswd_text_id);
        pswd_save_btn_id = findViewById(R.id.pswd_save_btn_id);

        sharedPreferences = getSharedPreferences("USER_DATA",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        change_user_name_and_title();
        change_user_password_and_title();

        name_save_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("User_name",enter_name_text_id.getText().toString());
                editor.apply();
                change_user_name_and_title();

                if(user_name.isEmpty()){
                    Toast.makeText(Settings_interface.this,"Your name is removed..!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(Settings_interface.this,"Your name is to\t"+user_name, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        pswd_save_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("User_password",enter_pswd_text_id.getText().toString());
                editor.apply();
                change_user_password_and_title();

                if(user_password.isEmpty()){
                    Toast.makeText(Settings_interface.this,"Your password is removed..!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(Settings_interface.this,"Your password is changed..!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    void change_user_password_and_title(){
        user_password = sharedPreferences.getString("User_password","");
        if(user_password.isEmpty()){
            enter_pswd_text_id.setHint("enter password");
            password_text.setText("Add a Password");
        }else {
            enter_pswd_text_id.setHint(user_password);
            password_text.setText("Change Password");
        }
    }

    void change_user_name_and_title(){
        user_name = sharedPreferences.getString("User_name","");
        if(user_name.isEmpty()){
            enter_name_text_id.setHint("enter name");
            name_text.setText("Add Your name");
        }else {
            enter_name_text_id.setHint(user_name);
            name_text.setText("Edit Name");
        }
    }




}