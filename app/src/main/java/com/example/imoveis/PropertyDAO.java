package com.example.imoveis;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PropertyDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Property property);

    @Delete
    void delete(Property property);

    @Update
    void update(Property property);

    @Query("SELECT * FROM property_table")
    List<Property> queryAll();
}

