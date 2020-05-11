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

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference;
    private ArrayList<History> dataPesanan;
    private ArrayList<Pharmacy> dataApotik;

    public Button button;
    public TextView loading;

    private HistoryViewModel historyViewModel;
    View root;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser user;
 //   private String GetUserID;

    boolean check_ScrollingUp = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        root = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = root.findViewById(R.id.rv_order_history);

        loading = root.findViewById(R.id.tv_loading);
        loading.setVisibility(View.VISIBLE);

        GetData();
        //scrollListener();
        return root;
    }
    private void GetData(){
        databaseReference = FirebaseDatabase.getInstance().getReference();

//        user = FirebaseAuth.getInstance().getCurrentUser();
//       final String  GetUserID = user.getUid();
        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference.child("Pesanan").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

//    public void scrollListener(){
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0){
//                    if(check_ScrollingUp){
//                        button.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.trans_downwards));
//                        check_ScrollingUp = false;
//                    }
//                } else {
//                    if(!check_ScrollingUp){
//                        button.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.trans_upwards));
//                        check_ScrollingUp = true;
//                    }
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//        });
//    }

    public void onCreate(@Nullable Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       // inflater.inflate(R.menu.appbar_menu_pharmacy, menu);
       // super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

//        if(id == R.id.app_bar_search){
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }


}

