package com.example.user_module.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.Site;

import java.util.List;

@Dao
public interface SiteDao {

    @Insert
    void insert(Site site);

    @Update
    void update(Site site);

    @Delete
    void delete(Site site);

    @Query("SELECT * FROM site ORDER BY name ASC")
    LiveData<List<Site>> getAllSites();

    @Query("SELECT * FROM site WHERE id = :siteId")
    LiveData<Site> getSiteById(int siteId);
}
