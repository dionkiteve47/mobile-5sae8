package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Accommodation;

import java.util.List;

@Dao
public interface AccommodationDao {
    @Query("SELECT * FROM accommodations WHERE location = :location AND capacity >= :numPeople")
    List<Accommodation> searchByLocationAndCapacity(String location, int numPeople);

    @Query("SELECT * FROM accommodations WHERE id = :id")
    Accommodation getAccommodationById(int id);

    @Insert
    void insertAccommodation(Accommodation accommodation);

    @Update
    void updateAccommodation(Accommodation accommodation);
}
