package com.example.maskerin;

import java.util.Date;

public class Pengguna {
    public String nama, email, nik;
    public Date lastOrder;

    public Pengguna(){

    }

    public Pengguna(String email, String nama, String nik) {
        this.email = email;
        this.nama = nama;
        this.nik=nik;
    }

    public void setLastOrder(Date lastOrder) {
        this.lastOrder = lastOrder;
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

    public Date getLastOrder() { return lastOrder; }

}