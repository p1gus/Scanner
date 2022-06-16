package com.example.newspaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddNews extends AppCompatActivity {

    DataBaseHelper databaseHelper;
    EditText txtContent, txtTitle;
    Bundle bundle;
    Calendar date;
    private int IDUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        bundle = getIntent().getExtras();
        txtContent = findViewById(R.id.txtContent);
        txtTitle = findViewById(R.id.txtTitle);
        databaseHelper = new DataBaseHelper(this);
        date = Calendar.getInstance();
        IDUser = bundle.getInt("Id");
    }

    public void addNewsClick(View view) {
        databaseHelper
                .insertNews(txtTitle.getText().toString().trim(), txtContent.getText().toString().trim(),
                        DateUtils.formatDateTime(getApplicationContext(), date.getTimeInMillis(),
                                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME), IDUser);
        startActivity(new Intent(this, AllNewsAdmin.class).putExtra("Id", IDUser));
        finish();
    }

    public void exitClick(View view) {
        startActivity(new Intent(this, AllNewsAdmin.class).putExtra("Id", IDUser));
        finish();
    }
}