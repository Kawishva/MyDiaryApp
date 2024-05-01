package com.example.githmidiaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login_interface extends AppCompatActivity {

    EditText entered_password_text_id;
    Button login_btn_id;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_interface);

        //assigning XML components with class variables
        entered_password_text_id = findViewById(R.id.entered_password_text_id);
        login_btn_id = findViewById(R.id.login_btn_id);

        sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);


        login_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("User_password", "").equals(entered_password_text_id.getText().toString())) {
                    Toast.makeText(Login_interface.this, "Password Matched..", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Main_menue_interface.class);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(Login_interface.this, "Incorrect Password..!", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

    }
}