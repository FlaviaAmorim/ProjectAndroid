package com.example.imoveis;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Property.class}, version = 1, exportSchema = false)
public abstract class PropertyDataBase extends RoomDatabase {

    public abstract PropertyDAO propertyDAO();

    private static volatile PropertyDataBase INSTANCE;
    private  static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecuter =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PropertyDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PropertyDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            PropertyDataBase.class, "property_database").allowMainThreadQueries().build();
                }
            }
        }
        return  INSTANCE;
    }
}
