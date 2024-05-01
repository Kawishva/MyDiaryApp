package com.example.githmidiaryapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class View_diary_interface extends AppCompatActivity {

    static ArrayList<String> id_list;
    static ArrayList<String> title_list;
    static ArrayList<String> image_list;
    static ArrayList<String> date_list;
    static ArrayList<String> entry_list;
    Button add_btn_id;
    ListView listview_id;
    TextView resent_text_id;
    Sql_Lite_DB_Helper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_diary_interface);

        add_btn_id = findViewById(R.id.add_btn_id);
        listview_id = findViewById(R.id.listview_id);
        resent_text_id = findViewById(R.id.resent_text_id);

        db = new Sql_Lite_DB_Helper(View_diary_interface.this);
        id_list = new ArrayList<>();
        title_list = new ArrayList<>();
        image_list = new ArrayList<>();
        date_list = new ArrayList<>();
        entry_list = new ArrayList<>();

        // Retrieve data from the database
        retrieve_data_from_db();

        add_btn_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Add_new_diary_interface.class);
                startActivity(intent);
            }
        });
    }

    void retrieve_data_from_db() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = db.read_all_data();
                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        id_list.add(cursor.getString(0));
                        title_list.add(cursor.getString(1));
                        image_list.add(cursor.getString(2));
                        date_list.add(cursor.getString(3));
                        entry_list.add(cursor.getString(4));
                    }
                    cursor.close(); // Close cursor after use
                    // Update UI on the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Set the adapter for the ListView
                            MyAdapter adapter = new MyAdapter(View_diary_interface.this);
                            listview_id.setAdapter(adapter);
                        }
                    });
                } else {
                    // Log.d("DB_DATA", "No data retrieved from the database");
                    resent_text_id.setText("No Diary Data...!");
                }
            }
        }).start(); // Start the thread
    }

    void confirm_dialog(String diary_title, String diary_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(View_diary_interface.this);
        builder.setTitle("Delete Diary Entry");
        builder.setMessage("Are you want to delete\t" + diary_title + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                db.delete_selected_data(diary_id);
                id_list.remove(diary_id);
                title_list.remove(diary_id);
                image_list.remove(diary_id);
                date_list.remove(diary_id);
                entry_list.remove(diary_id);

                if (id_list.isEmpty()) {
                    resent_text_id.setText("No Diary Data...!");
                }
                // Notify adapter about the change
                ((MyAdapter) listview_id.getAdapter()).notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private static class ViewHolder {
        Button delete_btn_id;
        TextView list_date_id, list_title_id, list_entry_id;
    }

    private class MyAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public MyAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return id_list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.diary_list, parent, false);
                holder = new ViewHolder();
                holder.list_date_id = convertView.findViewById(R.id.list_date_id);
                holder.list_title_id = convertView.findViewById(R.id.list_title_id);
                holder.list_entry_id = convertView.findViewById(R.id.list_entry_id);
                holder.delete_btn_id = convertView.findViewById(R.id.delete_btn_id);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.list_date_id.setText(date_list.get(position));  // Set data based on position
            holder.list_title_id.setText(title_list.get(position));
            holder.list_entry_id.setText(entry_list.get(position));
            holder.delete_btn_id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirm_dialog(title_list.get(position), id_list.get(position));

                }
            });

            return convertView;
        }
    }
}