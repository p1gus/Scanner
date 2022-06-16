package com.example.newspaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AllNewsAdmin extends AppCompatActivity {

    DataBaseHelper databaseHelper;
    RecyclerAdapter adapter;
    RecyclerView recyclerNews;
    Bundle bundle;
    private int IDUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news_admin);
        recyclerNews = findViewById(R.id.recyclerNews);
        bundle = getIntent().getExtras();
        IDUser = bundle.getInt("Id");
        databaseHelper = new DataBaseHelper(this);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerNews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, databaseHelper.getNewsData());
        recyclerNews.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AllNewsAdmin.this);
                dialog.setTitle("Удаление новости")
                        .setMessage("Вы действительно хотите удалить эту новость?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                databaseHelper.deleteNews((int) viewHolder.itemView.getTag());
                                adapter.swapCursor(databaseHelper.getNewsData());
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.swapCursor(databaseHelper.getNewsData());
                                dialogInterface.cancel();
                            }
                        });
                dialog.create().show();
            }
        }).attachToRecyclerView(recyclerNews);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                startActivity(new Intent(AllNewsAdmin.this, UpdateNews.class)
                        .putExtra("Id", IDUser)
                        .putExtra("IdNews", (int) viewHolder.itemView.getTag()));
                finish();
            }
        }).attachToRecyclerView(recyclerNews);
    }


    public void addNewsClick(View view) {
        startActivity(new Intent(this, AddNews.class).putExtra("Id", IDUser));
        finish();
    }
}