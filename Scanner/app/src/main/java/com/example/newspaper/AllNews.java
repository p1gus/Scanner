package com.example.newspaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AllNews extends AppCompatActivity {

    DataBaseHelper databaseHelper;
    RecyclerAdapter adapter;
    RecyclerView recyclerNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        recyclerNews = findViewById(R.id.recyclerNews);
        databaseHelper = new DataBaseHelper(this);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerNews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, databaseHelper.getNewsData());
        recyclerNews.setAdapter(adapter);
    }
}