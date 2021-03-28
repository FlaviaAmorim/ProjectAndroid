package com.example.imoveis;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RealtorDAO {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        void insert(Realtor realtor);

        @Delete
        void delete(Realtor realtor);

        @Update
        void update(Realtor realtor);

        @Query("SELECT * FROM realtor_table")
        List<Realtor> queryAll();
}

