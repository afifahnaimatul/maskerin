package com.example.maskerin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.maskerin.class_object.History;
import com.example.maskerin.class_object.Pharmacy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderInfoActivity extends AppCompatActivity {
    private TextView namaApotik,namaPengguna,tanggalPembelian, maskerDewasa, maskerAnak, totalPembayaran;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);

        namaApotik=findViewById(R.id.tv_nama_apotik);

        tanggalPembelian=findViewById(R.id.tv_tanggal_beli);
        namaPengguna=findViewById(R.id.tv_nama_pengguna);
        maskerDewasa=findViewById(R.id.tv_jumlah_dewasa);
        maskerAnak=findViewById(R.id.tv_jumlah_anak);
        totalPembayaran=findViewById(R.id.tv_info_total_pembayaran);

        getData();
    }

    private void getData(){
        Intent intent = getIntent();
        String getIdPemesanan = intent.getStringExtra("id_pemesanan");
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference().child("Pesanan").child(firebaseAuth.getUid()).child(getIdPemesanan);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   History history = dataSnapshot.getValue(History.class);
                    tanggalPembelian.setText(history.getTanggal());
                    maskerAnak.setText(String.valueOf(history.getMasker_anak()));
                    maskerDewasa.setText(String.valueOf(history.getMasker_dewasa()));
                    namaApotik.setText(history.getNama_apotik());
                    totalPembayaran.setText("Rp." + String.valueOf(history.getTotal_harga()));


                    firebaseAuth = FirebaseAuth.getInstance();
                    databaseReference = firebaseDatabase.getInstance().getReference("Pengguna").child(firebaseAuth.getUid());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Pengguna pengguna = dataSnapshot.getValue(Pengguna.class);
                            namaPengguna.setText(pengguna.getNama());

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

}
