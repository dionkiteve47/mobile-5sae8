package com.example.user_module.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Restaurant;

import java.util.List;

@Dao
public interface RestaurantDao {
    @Insert
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("SELECT * FROM Restaurant_table ORDER BY name ASC")
    LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM Restaurant_table WHERE id = :id")
    Restaurant getRestaurantById(int id);


}
