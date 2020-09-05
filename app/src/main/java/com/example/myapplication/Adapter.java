package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.UserViewHolder> {

    private ArrayList<ModelMobil> dataList;
    View viewku;

    public Adapter(ArrayList<ModelMobil> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        viewku = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(viewku);

    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.txtemail.setText(dataList.get(position).getEmail());
        holder.txtnama.setText(dataList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView txtemail, txtnama;
        CardView cardview;

        UserViewHolder(View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardku);
            txtemail = itemView.findViewById(R.id.txtemail);
            txtnama = itemView.findViewById(R.id.txtnama);
        }
    }

}