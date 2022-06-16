package com.example.newspaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class Registration extends AppCompatActivity {

    DataBaseHelper databaseHelper;
    Spinner spnRoles;
    EditText txtUserName, txtLogin, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txtUserName = findViewById(R.id.txtUserName);
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        spnRoles = findViewById(R.id.spnRoles);
        databaseHelper = new DataBaseHelper(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.roles));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnRoles.setAdapter(adapter);
    }

    public void registerClick(View view) {
        Boolean checkInsertData = databaseHelper
                .insertUser(txtUserName.getText().toString().trim(),
                        txtLogin.getText().toString().trim(), txtPassword.getText().toString().trim(), spnRoles.getSelectedItem().toString());
        if (checkInsertData) {
            Cursor res = databaseHelper.getData(txtLogin.getText().toString().trim(), txtPassword.getText().toString().trim());
            if (res.getCount() == 0) {
                return;
            }
            while (res.moveToNext()) {
                if (res.getString(4).equals("Администратор")) {
                    startActivity(new Intent(this, AllNewsAdmin.class)
                            .putExtra("Id", res.getInt(0)));
                } else {
                    startActivity(new Intent(this, AllNews.class));
                }
                finish();
            }
        }
    }
}