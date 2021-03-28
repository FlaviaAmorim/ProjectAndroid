package com.example.imoveis;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "realtor_table")
public class Realtor {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "realtor")

    int id;
    String name;
    String phone;
    String email;
    String password;

    public Realtor(){

    }

    public Realtor(int _id, String _name, String _phone, String _email, String _password){
        this.id          = _id;
        this.name        = _name;
        this.phone       = _phone;
        this.email       = _email;
        this.password    = _password;
    }

    public Realtor(String _name, String _phone, String _email, String _password){
        this.name        = _name;
        this.phone       = _phone;
        this.email       = _email;
        this.password    = _password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public  String toString() { return getName();}
}


