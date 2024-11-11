package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.user_module.entity.Reservationn;

import java.util.List;

@Dao
public interface ReservationnDao {

    @Insert
    void insert(Reservationn reservation);

    @Update
    void update(Reservationn reservation);

    @Delete
    void delete(Reservationn reservation);

    @Query("SELECT * FROM `reservation-table`")
    List<Reservationn> getAllReservations();

    // Query to delete all reservations (optional, in case you need to clear all data)
    @Query("DELETE FROM `reservation-table`")
    void deleteAllReservations();
}
