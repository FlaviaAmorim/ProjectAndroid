package com.example.imoveis;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

    @Database(entities = {Realtor.class}, version = 1, exportSchema = false)
    public abstract class RealtorDataBase extends RoomDatabase {

        public abstract RealtorDAO realtorDAO();

        private static volatile com.example.imoveis.RealtorDataBase INSTANCE;
        private  static final int NUMBER_OF_THREADS = 10;
        static final ExecutorService databaseWriteExecuter =
                Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        static com.example.imoveis.RealtorDataBase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (com.example.imoveis.RealtorDataBase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context,
                                com.example.imoveis.RealtorDataBase.class, "realtor_database").allowMainThreadQueries().build();
                    }
                }
            }
            return  INSTANCE;
        }
    }

