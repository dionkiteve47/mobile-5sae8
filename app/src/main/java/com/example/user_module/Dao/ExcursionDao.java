package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Update;

import com.example.user_module.entity.Excursion;


@Dao
public interface ExcursionDao {

    @Update
    void update(Excursion excursion);

}
