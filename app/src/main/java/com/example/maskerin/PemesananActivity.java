package com.example.maskerin;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.maskerin.class_object.History;
import com.example.maskerin.class_object.Pharmacy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PemesananActivity extends AppCompatActivity {
    private TextView nama_apotik,jumlah_stock_dewasa,jumlah_stock_anak,harga_masker_anak, harga_masker_dewasa,total_harga;
    private EditText jumlah_dewasa,jumlah_anak;
    private int jumlahDewasa, jumlahAnak;
    private String strDate = "12/05/0000", getId_pengguna, getId_apotik;
    private DatabaseReference databaseReference;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buatpesanan);
        nama_apotik=findViewById(R.id.tvNamaApotik);
        jumlah_stock_dewasa=findViewById(R.id.tv_jumlah_stock_dewasa);
        jumlah_stock_anak=findViewById(R.id.tv_jumlah_stock_anak);
        harga_masker_dewasa=findViewById(R.id.tv_harga_dewasa);
        harga_masker_anak=findViewById(R.id.tv_harga_anak);

        total_harga=findViewById(R.id.tv_total_harga_angka);
        jumlah_dewasa=findViewById(R.id.et_jumlah_dewasa);
        jumlah_anak=findViewById(R.id.et_jumlah_anak);

        jumlah_dewasa.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "3")});
        jumlah_anak.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "3")});

        getData();

        jumlah_dewasa.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                final int getHargaDewasa = Integer.parseInt(harga_masker_dewasa.getText().toString().trim());
                final int getHargaAnak = Integer.parseInt(harga_masker_anak.getText().toString().trim());

                String jumlah_input_dewasa;
                String jumlah_input_anak ;
                int masker_dewasa;
                int masker_anak;
                jumlah_input_dewasa= jumlah_dewasa.getText().toString().trim();
                jumlah_input_anak= jumlah_anak.getText().toString().trim();

                if(jumlah_input_dewasa.isEmpty() ){
                    masker_dewasa=0;
                    jumlahDewasa = 0;
                }else{
                    masker_dewasa = Integer.parseInt(jumlah_input_dewasa);
                    jumlahDewasa = masker_dewasa;
                }

                if( jumlah_input_anak.isEmpty()){
                    masker_anak=0;
                    jumlahAnak = 0;
                }else{
                    masker_anak = Integer.parseInt(jumlah_input_anak);
                    jumlahAnak = masker_anak;
                }

                final int harga_dewasa= masker_dewasa * getHargaDewasa;
                final int harga_anak = masker_anak * getHargaAnak;
                final int total = harga_dewasa + harga_anak ;
                total_harga.setText(String.valueOf(total));
            }

        });

        jumlah_anak.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                final int getHargaDewasa = Integer.parseInt(harga_masker_dewasa.getText().toString().trim());
                final int getHargaAnak = Integer.parseInt(harga_masker_anak.getText().toString().trim());

                String jumlah_input_dewasa;
                String jumlah_input_anak ;
                int masker_dewasa;
                int masker_anak;
                jumlah_input_dewasa= jumlah_dewasa.getText().toString().trim();
                jumlah_input_anak= jumlah_anak.getText().toString().trim();

                if(jumlah_input_dewasa.isEmpty() ){
                    masker_dewasa=0;
                    jumlahDewasa = 0;
                }else{
                    masker_dewasa = Integer.parseInt(jumlah_input_dewasa);
                    jumlahDewasa = masker_dewasa;
                }

                if( jumlah_input_anak.isEmpty()){
                    masker_anak=0;
                    jumlahAnak = 0;
                }else{
                    masker_anak = Integer.parseInt(jumlah_input_anak);
                    jumlahAnak = masker_anak;
                }

                final int harga_dewasa= masker_dewasa * getHargaDewasa;
                final int harga_anak = masker_anak * getHargaAnak;
                final int total = harga_dewasa + harga_anak ;
                total_harga.setText(String.valueOf(total));
            }
        });
    }

    private void getData(){

        final String getId_apotik = getIntent().getExtras().getString("id_apotik");

        FirebaseDatabase.getInstance().getReference("Apotik").child(getId_apotik).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pharmacy apotik = dataSnapshot.getValue(Pharmacy.class);
                nama_apotik.setText(apotik.getNama());
                jumlah_stock_dewasa.setText(String.valueOf(apotik.getStock_dewasa()));
                jumlah_stock_anak.setText( String.valueOf(apotik.getStock_anak()));
                harga_masker_dewasa.setText(String.valueOf(apotik.getHarga_dewasa()) );
                harga_masker_anak.setText(String.valueOf(apotik.getHarga_anak()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void PesanOnClick (View view) {
        nama_apotik=findViewById(R.id.tvNamaApotik);
        final int getJumlahDewasa= Integer.parseInt(jumlah_stock_dewasa.getText().toString().trim());
        final int getJumlahAnak= Integer.parseInt(jumlah_stock_anak.getText().toString().trim());
        final String namaApotik= nama_apotik.getText().toString().trim();
        int total = Integer.parseInt(total_harga.getText().toString().trim());

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        getId_pengguna = user.getUid();

        getId_apotik = getIntent().getExtras().getString("id_apotik");

//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        strDate = dateFormat.format(date);

        final History pesanan = new History(
                getId_apotik,
                namaApotik,
                getId_pengguna,
                strDate,
                jumlahDewasa,
                jumlahDewasa,
                total
        );

        FirebaseDatabase.getInstance().getReference("Pesanan").child(getId_pengguna).push().setValue(pesanan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    int stock_anak = getJumlahAnak-jumlahAnak;
                    int stock_dewasa = getJumlahDewasa - jumlahDewasa;
                    firebaseDatabase.getInstance().getReference("Apotik").child(getId_apotik).child("stock_anak").setValue(stock_anak);
                    firebaseDatabase.getInstance().getReference("Apotik").child(getId_apotik).child("stock_dewasa").setValue(stock_dewasa);

                    Intent intent = new Intent(PemesananActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Gagal Pesan", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}