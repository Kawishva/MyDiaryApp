package com.example.githmidiaryapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Add_new_diary_interface extends AppCompatActivity {

    EditText diary_text_id,enter_title_id;
    ImageView data_image_id;
    Button date_btn_id,data_add_btn_id;
    ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_diary_interface);

        //assigning XML components with class variables
        diary_text_id = findViewById(R.id.diary_text_id);
        enter_title_id = findViewById(R.id.enter_title_id);
        data_image_id = findViewById(R.id.data_image_id);
        date_btn_id = findViewById(R.id.date_btn_id);
        data_add_btn_id = findViewById(R.id.data_add_btn_id);
        pickImage();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);


        Date currentDate = new Date(); // Get the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");// Customize the format
        String formattedDate = dateFormat.format(currentDate);
        date_btn_id.setText(formattedDate);//set current date to date add btn text


        //set picked date to date add btn text
        date_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Add_new_diary_interface.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                month = month+1;
                                String date =  day + "/" + month + "/" + year;
                                date_btn_id.setText(date);
                            }
                        },year,month,day);
                datePickerDialog.show();
            }
        });

        data_image_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagepicker();
            }
        });

        data_add_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_data_to_db();
                Toast.makeText(Add_new_diary_interface.this,"Diary saved..!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), Main_menue_interface.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void pickImage() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_CANCELED) {
                            Toast.makeText(Add_new_diary_interface.this, "No image selected", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (result.getResultCode() == RESULT_OK) {
                            // Check if there is an image selected
                            if (result.getData() != null) {
                                Uri imageUri = result.getData().getData();
                                data_image_id.setImageURI(imageUri);
                                data_image_id.setImageTintList(null);
                            } else {
                                Toast.makeText(Add_new_diary_interface.this, "No image selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    void add_data_to_db(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bitmap u_image_bitmap = ((BitmapDrawable) data_image_id.getDrawable()).getBitmap();//read image as bytes

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                u_image_bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded_image_to_string = Base64.encodeToString(byteArray, Base64.DEFAULT);


                Sql_Lite_DB_Helper diaryDB = new Sql_Lite_DB_Helper(Add_new_diary_interface.this);
                diaryDB.add_diary_data(
                        enter_title_id.getText().toString(),
                        encoded_image_to_string,
                        date_btn_id.getText().toString(),
                        diary_text_id.getText().toString()

                );
            }
        }).start();
    }

    private void imagepicker(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }
}