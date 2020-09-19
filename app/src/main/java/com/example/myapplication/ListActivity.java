package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;

    ArrayList<Model> datalist;

    CardView cardview;

    TextView txtnama, txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        txtnama = findViewById(R.id.txtemail);
        txtemail = findViewById(R.id.txtnama);

        cardview = findViewById(R.id.cardku);
        recyclerView = findViewById(R.id.list);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Data Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();

    }

    private void getData() {
        datalist = new ArrayList<>();
        Log.d("dea", "onCreate: ");

        AndroidNetworking.post("http://192.168.6.159/RentalMobil/ViewData.php")
                .addBodyParameter("roleuser", "2")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("result");

                            for (int i = 0; i < data.length(); i++) {

                                Model model = new Model();
                                JSONObject object = data.getJSONObject(i);
                                model.setId(object.getString("id"));
                                model.setEmail(object.getString("email"));
                                model.setNama(object.getString("nama"));
                                model.setNoHp(object.getString("nohp"));
                                model.setAlamat(object.getString("alamat"));
                                model.setNoKtp(object.getString("noktp"));
                                datalist.add(model);

                            }

                            adapter = new Adapter(datalist);

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListActivity.this);

                            recyclerView.setLayoutManager(layoutManager);

                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("dea", "onResponse: " + anError.toString());
                        Log.d("dea", "onResponse: " + anError.getErrorBody());
                        Log.d("dea", "onResponse: " + anError.getErrorCode());
                        Log.d("dea", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getData();
            Toast.makeText(this, "deadea", Toast.LENGTH_SHORT).show();

        }
    }

}