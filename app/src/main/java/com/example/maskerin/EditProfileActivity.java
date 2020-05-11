package com.example.maskerin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maskerin.nav_ui.profile.ProfileFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etNama, etEmail, etNik, etPassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etNama = findViewById(R.id.profile_et_name);
        etEmail = findViewById(R.id.profile_et_email);
        etNik = findViewById(R.id.profile_et_nik);
        etPassword = findViewById(R.id.profile_et_password);

        auth();
        updateValueToView();
    }

    public void auth(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = firebaseDatabase.getReference("Pengguna").child(firebaseAuth.getUid());
    }

    public void updateValueToView(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pengguna userProfile = dataSnapshot.getValue(Pengguna.class);
                etNama.setText(userProfile.getNama());
                etNik.setText(userProfile.getNik());
                etEmail.setText(userProfile.getEmail());

                //etPassword.setText(userProfile.getPassword);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickButtonSave(View v){
        //nik should not be edited actually. check again!
        String nama = etNama.getText().toString();
        String email = etEmail.getText().toString();
        String nik = etNik.getText().toString();
        String password = etPassword.getText().toString();

        Pengguna pengguna = new Pengguna(email, nama, nik);
        //user update name gada?
        user.updateEmail(email);
        databaseReference.setValue(pengguna);
        if(!etPassword.getText().toString().isEmpty()){
            //user update name gada?
            user.updatePassword(password);
        }

            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();




    }

}
