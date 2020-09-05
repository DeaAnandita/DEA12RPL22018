package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

public class Registrasi extends AppCompatActivity {

    TextView txtRegristrasi;
    EditText edEmail;
    EditText edPassword;
    EditText edNmaLengkap;
    EditText edNoKtp;
    EditText edNoHp;
    EditText edAlamat;
    Button btnDaftar;
    TextView txtsdhakun;

    private ProgressDialog progressBar;
    SharedPreferences mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        mLogin = getSharedPreferences("login",MODE_PRIVATE);

        progressBar = new ProgressDialog(this);

        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edNmaLengkap = findViewById(R.id.edNmaLengkap);
        edNoKtp = findViewById(R.id.edNoKtp);
        edNoHp = findViewById(R.id.edNoHp);
        edAlamat = findViewById(R.id.edAlamat);
        btnDaftar = findViewById(R.id.btnDaftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String nama = edNmaLengkap.getText().toString();
                String noktp = edNoKtp.getText().toString();
                String nohp = edNoHp.getText().toString();
                String alamat = edAlamat.getText().toString().trim();
                progressBar.setTitle("Register In...");
                progressBar.show();
                AndroidNetworking.post("https://192.168.43.32/RentalMobil/Registrasi.php")
                        .addBodyParameter("email", email)
                        .addBodyParameter("password", password)
                        .addBodyParameter("namalengkap", nama)
                        .addBodyParameter("noktp", noktp)
                        .addBodyParameter("nphp", nohp)
                        .addBodyParameter("alamat", alamat)
                        .addBodyParameter("roleuser", "1")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("hasil", "onResponse: ");
                                try {
                                    JSONObject status = response.getJSONObject("STATUS");
                                    JSONObject message = response.getJSONObject("MESSAGE");
                                    Log.d("STATUS", "onResponse: " + status);
                                    if (status.equals("SUCCESS")) {
                                        mLogin.edit().putBoolean("logged",true).apply();
                                        Intent intent = new Intent(Registrasi.this, DashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                        progressBar.dismiss();
                                    } else {
                                        Toast.makeText(Registrasi.this, message.toString(), Toast.LENGTH_SHORT).show();
                                        progressBar.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });

            }
        });

    }
}