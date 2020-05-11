package com.example.maskerin.class_object;

public class History {

    public String id_apotik, nama_apotik, id_pengguna, tanggal, key ;
    public int masker_dewasa;
    public int masker_anak;
    public int total_harga;

    public History(){

    }

    public History(String id_apotik, String nama_apotik, String id_pengguna, String tanggal, int masker_dewasa, int masker_anak, int total_harga) {
        this.id_apotik = id_apotik;
        this.nama_apotik = nama_apotik;
        this.id_pengguna = id_pengguna;
        this.tanggal = tanggal;
        this.masker_dewasa = masker_dewasa;
        this.masker_anak = masker_anak;
        this.total_harga = total_harga;
    }

    public String getId_apotik() {
        return id_apotik;
    }

    public void setId_apotik(String id_apotik) {
        this.id_apotik = id_apotik;
    }

    public String getId_pengguna() {
        return id_pengguna;
    }

    public void setId_pengguna(String id_pengguna) {
        this.id_pengguna = id_pengguna;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getMasker_dewasa() {
        return masker_dewasa;
    }

    public void setMasker_dewasa(int masker_dewasa) {
        this.masker_dewasa = masker_dewasa;
    }

    public int getMasker_anak() {
        return masker_anak;
    }

    public void setMasker_anak(int masker_anak) {
        this.masker_anak = masker_anak;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama_apotik() {
        return nama_apotik;
    }

    public void setNama_apotik(String nama_apotik) {
        this.nama_apotik = nama_apotik;
    }
}
