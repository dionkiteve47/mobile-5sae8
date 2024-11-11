package com.example.user_module.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.user_module.entity.Commentaire;

import java.util.List;

@Dao
public interface CommentaireDao {

    @Insert
    void insert(Commentaire commentaire);

    @Query("SELECT * FROM commentaire_table WHERE excursionId = :excursionId ORDER BY timestamp DESC LIMIT 4")
    LiveData<List<Commentaire>> getLatestCommentairesByExcursionId(int excursionId);
}
