package com.example.imoveis;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "property_table")
public class Property {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "property")

    int id;
    String title;
    String address;
    String number;
    String typeProperty;
    String value;
    String phone;
    String typeContract;
    String description;

    public Property(){

    }

    public Property(@NonNull int _id, @NonNull String _title, @NonNull String _address, @NonNull String _number, @NonNull  String _value, @NonNull  String _phone, @NonNull  String _description){

        this.id                 = _id;
        this.title              = _title;
        this.address            = _address;
        this.number             = _number;
        this.value              = _value;
        this.phone              = _phone;
        this.description        = _description;
    }

    public Property(@NonNull String _title, @NonNull  String _address, @NonNull  String _number,@NonNull String _value, @NonNull  String _phone,@NonNull String _description){

        this.title              = _title;
        this.address            = _address;
        this.number             = _number;
        this.value              = _value;
        this.phone              = _phone;
        this.description        = _description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        return getTitle();
    }
}
