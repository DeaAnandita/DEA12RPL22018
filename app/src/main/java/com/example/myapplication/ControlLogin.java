package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class ControlLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mLogin;

            mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);

            Log.d("username", "onCreate: " + mLogin.getString("username", "missing"));

            if (mLogin.getString("username", "").equalsIgnoreCase("")
                    || mLogin.getString("email", "") == null
                    || mLogin.getString("email", "").isEmpty()){
                Intent intent = new Intent(ControlLogin.this, Login.class);
                startActivity(intent);
                finish();

            }else {

                Intent intent = new Intent(ControlLogin.this, DashboardActivity.class);
                startActivity(intent);
                finish();

            }

        }
    }

