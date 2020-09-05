package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class EditActivity extends AppCompatActivity {

    Button btn_edit;
    EditText txtemail, txtnama, txtnoktp, txtnohp, txtalamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle extras = getIntent().getExtras();

        final String id = extras.getString("id");
        final String email = extras.getString("email");
        final String nama = extras.getString("nama");
        final String noktp = extras.getString("noktp");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");

        txtemail.setText(email);
        txtnama.setText(nama);
        txtnoktp.setText(noktp);
        txtnohp.setText(nohp);
        txtalamat.setText(alamat);

        txtemail = findViewById(R.id.txtemail);
        txtnama = findViewById(R.id.txtnama);
        txtnoktp = findViewById(R.id.txtnoktp);
        txtnohp = findViewById(R.id.txtnohp);
        txtalamat = findViewById(R.id.txtalamat);
        btn_edit = findViewById(R.id.btn_edit);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post("https://192.168.43.32/RentalMobil/EditData.php")
                        .addBodyParameter("id", id)
                        .addBodyParameter("email", txtemail.getText().toString())
                        .addBodyParameter("nama", txtnama.getText().toString())
                        .addBodyParameter("noktp", txtnoktp.getText().toString())
                        .addBodyParameter("nohp", txtnohp.getText().toString())
                        .addBodyParameter("alamat", txtalamat.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.e("", response.toString());
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent();
                                        returnIntent.putExtra("refresh", "refresh");
                                        setResult(23, returnIntent);
                                        finish();
                                        Toast.makeText(EditActivity.this, "Edit Suskses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditActivity.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println("ttttt" + e.getMessage());
                                    Toast.makeText(EditActivity.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(EditActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}