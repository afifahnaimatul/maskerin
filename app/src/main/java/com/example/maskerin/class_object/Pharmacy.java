package com.example.maskerin.class_object;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class Pharmacy {
    public String nama, alamat, hari, jam;
    public int stock_dewasa, stock_anak, harga_dewasa, harga_anak;
    private String key;
    public Double bujur,lintang;


    public Pharmacy() {

    }

    public Pharmacy(String nama, String alamat, String hari, String jam, int stock_dewasa, int stock_anak, int harga_dewasa, int harga_anak) {
        this.nama = nama;
        this.alamat = alamat;
        this.hari = hari;
        this.jam = jam;
        this.stock_dewasa = stock_dewasa;
        this.stock_anak = stock_anak;
        this.harga_dewasa = harga_dewasa;
        this.harga_anak = harga_anak;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public void setStock_dewasa(int stock_dewasa) {
        this.stock_dewasa = stock_dewasa;
    }

    public void setHarga_dewasa(int harga_dewasa) {
        this.harga_dewasa = harga_dewasa;
    }

    public void setStock_anak(int stock_anak) {
        this.stock_anak = stock_anak;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getHari() {
        return hari;
    }

    public String getJam() {
        return jam;
    }

    public int getStock_dewasa() {
        return stock_dewasa;
    }

    public int getStock_anak() {
        return stock_anak;
    }

    public int getHarga_dewasa() {
        return harga_dewasa;
    }

    public int getHarga_anak() {
        return harga_anak;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setHarga_anak(int harga_anak) {
        this.harga_anak = harga_anak;
    }

    public Double getBujur() {
        return bujur;
    }

    public void setBujur(Double bujur) {
        this.bujur = bujur;
    }

    public Double getLintang() {
        return lintang;
    }

    public void setLintang(Double lintang) {
        this.lintang = lintang;
    }
}

