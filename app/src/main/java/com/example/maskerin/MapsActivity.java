package com.example.maskerin;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;


import com.example.maskerin.class_object.Pharmacy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    public static final int ROUND = 10;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    public FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    SearchView searchView;

    private ArrayList<Pharmacy> dataApotik;

    private TextView nama_apotik, alamat, jam, hari, lintang, bujur,jumlah_stock_dewasa,jumlah_stock_anak,harga_masker_anak, harga_masker_dewasa,total_harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchView = findViewById(R.id.sv_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        FirebaseDatabase.getInstance().getReference().child("Apotik").addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {
                dataApotik = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Mapping data pada DataSnapshot ke dalam objek aptik
                    final Pharmacy apotik = snapshot.getValue(Pharmacy.class);
                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    apotik.setKey(snapshot.getKey());
                    dataApotik.add(apotik);

                    final LatLng location = new LatLng(apotik.getLintang(), apotik.getBujur());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(apotik.getNama()).snippet("Jam Buka Apotik : " + apotik.getJam() + "\n" + "Alamat : " + apotik.getAlamat() + "\n"
                            + "Sisa Masker anak : " + apotik.getStock_anak() + "\n" + "Sisa Masker dewasa : " + apotik.getStock_dewasa()));
                    marker.setTag(apotik.getKey());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

                    //Untuk Search
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String s) {
                            String locati = searchView.getQuery().toString();
                            List<Address> addressList = null;
                            if (locati != null || !locati.equals("")){
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,30));
                            }
                            return false;
                        }

                        @Override
                        public boolean onQueryTextChange(String s) {
                            return false;
                        }
                    });
                }

                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        Context mContext = getApplicationContext();
                        LinearLayout info = new LinearLayout(mContext);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(mContext);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(mContext);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);
                        return info;
                    }
                });

                /**Untuk Klik ke Halaman selanjutnya*/

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        // call an activity(xml file)
                        Intent intent = new Intent(MapsActivity.this, PemesananActivity.class);
                        intent.putExtra("id_apotik",(String)marker.getTag());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}