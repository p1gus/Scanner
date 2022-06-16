package com.example.newspaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newspaper.R;

import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    EditText txtLogin, txtPassword;
    DataBaseHelper databaseHelper;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        databaseHelper = new DataBaseHelper(this);



        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(Login.this,executor,new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Log.e("Ошибка аутентификации",errString.toString());
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("Не тот палец","Ошибка");

            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(Login.this, AllNewsAdmin.class);
                Cursor res = databaseHelper.getData(txtLogin.getText().toString().trim(), txtPassword.getText().toString().trim());s
                if (res.getCount() == 0) {
                    Toast.makeText(Login.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                    return;
                }
                while (res.moveToNext()) {
                    if (res.getString(4).equals("Администратор")) {
                        intent.putExtra("Id", res.getInt(0));
                        startActivity(intent);
                    } else {
                        startActivity(new Intent(Login.this, AllNews.class));
                    }
                }
            }
        });

        promptInfo=new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Прислоните палец")
                .setNegativeButtonText("Отмена")
                .build();
    }

    public void enterClick(View view) {

        biometricPrompt.authenticate(promptInfo);




    }

    public void registrationClick(View view) {
        startActivity(new Intent(this, Registration.class));
    }
}