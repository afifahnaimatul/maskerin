package com.example.maskerin.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskerin.PemesananActivity;
import com.example.maskerin.R;
import com.example.maskerin.class_object.Pharmacy;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyViewHolder> {

    private ArrayList<Pharmacy> listApotik;
    private Context context;



    public PharmacyAdapter(ArrayList<Pharmacy> listApotik, Context context) {
        this.listApotik = listApotik;
        this.context = context;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama, alamat, stock_dewasa, stock_anak, hari, jam,harga_dewasa,harga_anak;
      //Button pesan;
       private ConstraintLayout ListItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_name);
            alamat = itemView.findViewById(R.id.tv_address);
            stock_dewasa = itemView.findViewById(R.id.tv_stock_dewasa);
            stock_anak = itemView.findViewById(R.id.tv_stock_anak);
            hari = itemView.findViewById(R.id.tv_date);
            jam = itemView.findViewById(R.id.tv_time);
            harga_anak=itemView.findViewById(R.id.tv_harga_anak);
            harga_dewasa=itemView.findViewById(R.id.tv_harga_dewasa);
            ListItem = itemView.findViewById(R.id.list_item2);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pharmacy, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String nama = listApotik.get(position).getNama();
        String alamat = listApotik.get(position).getAlamat();
        int stock_dewasa = listApotik.get(position).getStock_dewasa();
        int stock_anak = listApotik.get(position).getStock_anak();
        int harga_dewasa= listApotik.get(position).getHarga_dewasa();
        int harga_anak=listApotik.get(position).getHarga_anak();
        String hari = listApotik.get(position).getHari();
        String jam = listApotik.get(position).getJam();

        holder.nama.setText(nama);
        holder.alamat.setText(alamat);
        holder.stock_dewasa.setText(String.valueOf(stock_dewasa));
        holder.stock_anak.setText(String.valueOf(stock_anak));
        holder.hari.setText(hari);
        holder.jam.setText(" - " + jam);
        holder.harga_anak.setText(" - Rp." + String.valueOf(harga_anak) + "/masker");
        holder.harga_dewasa.setText( " - Rp." + String.valueOf(harga_dewasa) +"/masker");

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( final View v) {
                Bundle bundle = new Bundle();
//                bundle.putString("nama", listApotik.get(position).getNama());
//                bundle.putInt("stock_dewasa", listApotik.get(position).getStock_dewasa());
//                bundle.putInt("stock_anak", listApotik.get(position).getStock_anak());
//                bundle.putInt("harga_dewasa", listApotik.get(position).getHarga_dewasa());
//                bundle.putInt("harga_anak", listApotik.get(position).getHarga_anak());
                bundle.putString("id_apotik", listApotik.get(position).getKey());
                Intent intent = new Intent(v.getContext(), PemesananActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

                    }
                });
            }

    @Override
   public int getItemCount() {
       return this.listApotik.size();
   }
}


