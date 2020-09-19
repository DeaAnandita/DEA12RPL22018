package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    String email, password;
    TextView register;
    private ProgressDialog progressBar;

    SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = new ProgressDialog(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        register = findViewById(R.id.txtblmakun);


        mLogin = getSharedPreferences("login",MODE_PRIVATE);
        mLogin.edit().putString("logged", mLogin.getString("logged", "missing")).apply();

        String admin = mLogin.getString("logged", "missing");
        String customer = mLogin.getString("logged", "missing");

        if(customer.equals("customer")){
            Intent intent = new Intent(Login.this, CostumerActivity.class);
            startActivity(intent);
            finish();
        }else if (admin.equals("admin")){
            Intent intent = new Intent(Login.this, AdminActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString().trim();
                progressBar.setTitle("Logging In...");
                progressBar.show();
                AndroidNetworking.post("http://192.168.6.159/RentalMobil/LoginCostumer.php")
                        .addBodyParameter("email" , email)
                        .addBodyParameter("password" , password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject PAYLOAD = response.getJSONObject("PAYLOAD");
                                    boolean sukses = PAYLOAD.getBoolean("respon");
                                    String roleuser = PAYLOAD.getString("roleuser");
                                    Log.d("PAYLOAD", "onResponse: " + PAYLOAD);
                                    if (sukses && roleuser.equals("admin")) {
                                        mLogin.edit().putString("logged","admin").apply();
                                        Intent intent = new Intent(Login.this, AdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                        progressBar.dismiss();
                                    } else if (sukses && roleuser.equals("customer")){
                                        mLogin.edit().putString("logged","customer").apply();
                                        Intent intent = new Intent(Login.this, CostumerActivity.class);
                                        startActivity(intent);
                                        finish();
                                        progressBar.dismiss();
                                    } else {
                                        Toast.makeText(Login.this, "gagal", Toast.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                progressBar.dismiss();
                                Log.d("dea", "onError: " + anError.getErrorDetail());
                                Log.d("dea", "onError: " + anError.getErrorBody());
                                Log.d("dea", "onError: " + anError.getErrorCode());
                            }
                        });

            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registrasi.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);                    }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
