package com.example.maskerin.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskerin.OrderInfoActivity;
import com.example.maskerin.PemesananActivity;
import com.example.maskerin.R;
import com.example.maskerin.class_object.History;
import com.example.maskerin.class_object.Pharmacy;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    ArrayList<History> histories;
    ArrayList<Pharmacy> pharmacy;
    Context context;

    public HistoryAdapter(ArrayList<History> histories, Context context) {
        this.histories = histories;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namaApotik, tanggalPembelian, totalPembelian;
        private ConstraintLayout ListItem2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namaApotik = itemView.findViewById(R.id.tv_order_nama_apotik);
            tanggalPembelian = itemView.findViewById(R.id.tv_tanggal_pembelian);
            totalPembelian = itemView.findViewById(R.id.tv_total);
            ListItem2 = itemView.findViewById(R.id.listItem2);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String namaApotik = histories.get(position).getNama_apotik();
        String tanggalPembelian = histories.get(position).getTanggal();
        int totalPembelian = histories.get(position).getTotal_harga();

        holder.namaApotik.setText(namaApotik);
        holder.tanggalPembelian.setText("Tanggal Pembelian : " + tanggalPembelian);
        holder.totalPembelian.setText(String.valueOf("Total Pembayaran : Rp." + totalPembelian));

        holder.ListItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id_pemesanan", histories.get(position).getKey());
                Intent intent = new Intent(v.getContext(), OrderInfoActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

}