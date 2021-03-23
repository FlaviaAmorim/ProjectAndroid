package com.example.imoveis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PropertyDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Property property);

    @Query("DELETE FROM property_table")
    void deleteAll();

    @Query("SELECT * FROM property_table ORDER BY property ASC")
    List<Property> queryAll();
}

