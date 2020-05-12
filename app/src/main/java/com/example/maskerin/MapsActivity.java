package com.example.maskerin;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;
    public static final int ROUND = 10;
    private ChildEventListener mChildEventListener;
    private DatabaseReference mUsers;
    Marker marker;
    public FusedLocationProviderClient fusedLocationProviderClient;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    private ArrayList<Pharmacy> dataApotik;

    private TextView nama_apotik, alamat, jam, hari, lintang, bujur,jumlah_stock_dewasa,jumlah_stock_anak,harga_masker_anak, harga_masker_dewasa,total_harga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ChildEventListener mChildEventListener;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        mUsers = FirebaseDatabase.getInstance().getReference();
//        mUsers.push().setValue(marker);


        // Add a marker and move the camera
            FirebaseDatabase.getInstance().getReference().child("Apotik").addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
//                LatLng sydney = new LatLng(-33.852, 151.211);
//                mMap.addMarker(new MarkerOptions().position(sydney)
//                        .title("Marker in Sydney"));
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                dataApotik = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                    Pharmacy apotik = snapshot.getValue(Pharmacy.class);
                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    apotik.setKey(snapshot.getKey());
                    dataApotik.add(apotik);

                    final LatLng location = new LatLng(apotik.getLintang(), apotik.getBujur());
                    Marker marker = mMap.addMarker(new MarkerOptions().position(location).title(apotik.getNama()).snippet("Jam Buka Apotik : " + apotik.getJam() + "\n" + "Alamat : " + apotik.getAlamat() + "\n"
                            + "Sisa Masker anak : " + apotik.getStock_anak() + "\n" + "Sisa Masker dewasa : " + apotik.getStock_dewasa()));
                    marker.setTag(apotik.getKey());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

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



    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}