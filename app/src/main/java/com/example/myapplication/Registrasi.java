package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        btnDaftar = findViewById(R.id.btnDaftar);
        txtsdhakun = findViewById(R.id.txtsdhakun);


            }
        });

    }
}
