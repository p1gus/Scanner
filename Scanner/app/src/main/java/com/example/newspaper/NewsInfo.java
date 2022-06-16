package com.example.newspaper;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewsInfo extends AppCompatActivity {

    DataBaseHelper databaseHelper;
    TextView txtContent, txtTitle;
    Bundle bundle;
    private int IdNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        bundle = getIntent().getExtras();
        txtContent = findViewById(R.id.txtContent);
        txtTitle = findViewById(R.id.txtTitle);
        databaseHelper = new DataBaseHelper(this);
        IdNews = bundle.getInt("IdNews");        setData();
    }

    private void setData() {
        Cursor res = databaseHelper.getNewsData(IdNews);
        while (res.moveToNext()){
            txtContent.setText(res.getString(2));
            txtTitle.setText(res.getString(1));
        }
    }


    public void exitClick(View view) {
        finish();
    }
}