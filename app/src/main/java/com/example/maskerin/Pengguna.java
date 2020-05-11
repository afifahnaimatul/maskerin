package com.example.maskerin;

public class Pengguna {
    public String nama, email, nik;

    public Pengguna(){

    }

    public Pengguna(String email, String nama, String nik) {
        this.email = email;
        this.nama = nama;
        this.nik=nik;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getNik() {
        return nik;
    }

}