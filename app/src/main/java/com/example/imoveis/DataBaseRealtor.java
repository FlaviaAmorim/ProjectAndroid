package com.example.imoveis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseRealtor extends SQLiteOpenHelper {

    public static final int VERSION_DATABASE = 1;
    public static final String DATABASE_REALTOR = "bd_realtor";

    public static final String TABLE_REALTOR = "td_realtor";
    public static final String COLLUM_ID= "id";
    public static final String COLLUM_NAME = "nome";
    public static final String COLLUM_PHONE = "telefone";
    public static final String COLLUM_EMAIL = "email";
    public static final String COLLUM_PASSWORD = "senha";


    public DataBaseRealtor(Context context) {
        super(context, DATABASE_REALTOR, null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY = "CREATE TABLE " + TABLE_REALTOR + "("
                + COLLUM_ID + " INTEGER PRIMARY KEY, " + COLLUM_NAME + " TEXT, "
                + COLLUM_PHONE + " TEXT, " + COLLUM_EMAIL + " TEXT, " + COLLUM_PASSWORD + " TEXT) ";

        db.execSQL(QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addClient (Realtor client){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLLUM_NAME, client.getName());
        values.put(COLLUM_PHONE, client.getPhone());
        values.put(COLLUM_EMAIL, client.getEmail());
        values.put(COLLUM_PASSWORD, client.getPassword());

        db.insert(TABLE_REALTOR,null,values);
        db.close();
    }

    public  List<Realtor> clientList(){
        List<Realtor> list = new ArrayList<Realtor>();

        String query = "SELECT * FROM " + TABLE_REALTOR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                Realtor client = new Realtor();
                client.setId(Integer.parseInt(c.getString(0)));
                client.setName(c.getString(1));
                client.setPhone(c.getString(2));
                client.setEmail(c.getString(3));
                client.setPassword(c.getString(4));

                list.add(client);
            } while (c.moveToNext());
        }
        return list;
    }

    public  void deleteClient(String client){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_REALTOR );
        db.close();

    }
}
