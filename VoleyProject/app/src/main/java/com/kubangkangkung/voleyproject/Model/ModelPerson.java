package com.kubangkangkung.voleyproject.Model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class ModelPerson {
    String id,nama,alamat,phone;
@ParcelConstructor
    public ModelPerson(String id, String nama, String alamat, String phone) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
