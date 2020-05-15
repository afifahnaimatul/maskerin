package com.example.maskerin.nav_ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskerin.LoginActivity;
import com.example.maskerin.R;
import com.example.maskerin.adapter.HistoryAdapter;
import com.example.maskerin.adapter.PharmacyAdapter;
import com.example.maskerin.class_object.History;
import com.example.maskerin.class_object.Pharmacy;
import com.example.maskerin.nav_ui.pharmacy.PharmacyViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference;
    private ArrayList<History> dataPesanan;
    private ArrayList<Pharmacy> dataApotik;
    private String strDate = "12/05/0000";
    private String lastOrder = "00/05/0000";
    private Button button_pesan_masker;
    private boolean output;
    private TextView bolehpesan;

    public Button button;
    public TextView loading;

    private HistoryViewModel historyViewModel;
    View root;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        root = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = root.findViewById(R.id.rv_order_history);

        loading = root.findViewById(R.id.tv_loading);
        loading.setVisibility(View.VISIBLE);

        button_pesan_masker = root.findViewById(R.id.btn_pesan_masker);
        bolehpesan = root.findViewById(R.id.bolehpesan);

        GetData();
        return root;
    }

    private void GetData(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference.child("Pesanan").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //setButtonOn();

                dataPesanan = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    History history = snapshot.getValue(History.class);
                        history.setKey(snapshot.getKey());
                        dataPesanan.add(history);
                }

                adapter = new HistoryAdapter(dataPesanan, getActivity());
                loading.setVisibility(View.GONE);
                //Memasang Adapter pada RecyclerView
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setButtonOn() {
        if (isCanOrder()){
            button_pesan_masker.setEnabled(true);
            bolehpesan.setVisibility(View.GONE);
        } else{
            button_pesan_masker.setEnabled(false);
            bolehpesan.setVisibility(View.VISIBLE);
            bolehpesan.setText("* Anda hanya diperbolehkan memesan sebanyak satu kali dalam 7 hari");
        }
    }

    public boolean isCanOrder(){
        String newOrder[] = strDate.split("/");
        String lasOrder[] = lastOrder.split("/");

        if(Integer.parseInt(newOrder[1]) - Integer.parseInt(lasOrder[1]) == 0 ){
            int delta = Integer.parseInt(newOrder[0]) - Integer.parseInt(lasOrder[0]);
            if (delta <= 7){
                //Toast.makeText(getApplicationContext(), "jangan 1", Toast.LENGTH_SHORT).show();
                output = false;
            } else {
                //Toast.makeText(getApplicationContext(), "gas 1", Toast.LENGTH_SHORT).show();
                output = true;
            }
        } else if (Integer.parseInt(newOrder[0]) <= 7) {
            //Toast.makeText(getApplicationContext(), "jangan 2", Toast.LENGTH_SHORT).show();
            output = false;
        } else{
            //Toast.makeText(getApplicationContext(), "gas 2", Toast.LENGTH_SHORT).show();
            output = true;
        }
        return output;
    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

