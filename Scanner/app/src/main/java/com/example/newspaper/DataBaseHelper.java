package com.example.newspaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "NewsPaper.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table Users(" +
                "IDUser Integer PRIMARY KEY AUTOINCREMENT, " +
                "UserName Text, " +
                "Login Text, " +
                "Password Text, " +
                "Role Text)");
        sqLiteDatabase.execSQL("create Table News(" +
                "IDNews Integer PRIMARY KEY AUTOINCREMENT, " +
                "Title Text, " +
                "Content Text, " +
                "DateOfPublication Text, " +
                "UserID Integer, FOREIGN KEY(UserID) REFERENCES Users(IDUser))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists Users");
        sqLiteDatabase.execSQL("drop Table if exists News");
    }

    public Boolean insertUser(String userName,String login, String password, String role) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("UserName", userName);
        contentValues.put("Login", login);
        contentValues.put("Password", password);
        contentValues.put("Role", role);
        long result = DB.insert("Users", null, contentValues);
        return result != -1;
    }

    public void insertNews(String title, String content, String dateOfPublication, int userID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", title);
        contentValues.put("Content", content);
        contentValues.put("DateOfPublication", dateOfPublication);
        contentValues.put("UserID", userID);
        DB.insert("News", null, contentValues);
    }

    public void updateNews(int idNews, String title, String content, String dateOfPublication, int userID) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", title);
        contentValues.put("Content", content);
        contentValues.put("DateOfPublication", dateOfPublication);
        contentValues.put("UserID", userID);
        DB.update("News", contentValues, "IdNews=?", new String[]{Integer.toString(idNews)});
    }

    public void deleteNews(int idNews) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("News", "IdNews=?", new String[]{Integer.toString(idNews)});
    }

    public Cursor getData(String login, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Users where Login = '" + login +
                "' and Password = '" + password + "' LIMIT 1", null);
    }

    public Cursor getData(int idUser) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from Users where IDUser = '" + idUser + "'", null);
    }

    public Cursor getNewsData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News", null);
    }

    public Cursor getNewsData(int idNews) {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from News where IdNews = '" + idNews + "'", null);
    }
}
