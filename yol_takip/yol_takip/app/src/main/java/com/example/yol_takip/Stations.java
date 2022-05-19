package com.example.yol_takip;

public class Stations {
    private int id;


    private String durakAdi;
    private String latitude;
    private String longitude;
    private int yolcuSayisi;

    public Stations(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDurakAdi() {
        return durakAdi;
    }

    public void setDurakAdi(String durakAdi) {
        this.durakAdi = durakAdi;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getYolcuSayisi() {
        return yolcuSayisi;
    }

    public void setYolcuSayisi(int yolcuSayisi) {
        this.yolcuSayisi = yolcuSayisi;
    }
}
