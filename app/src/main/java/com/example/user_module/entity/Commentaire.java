package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "commentaire_table")
public class Commentaire {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int excursionId;  // Foreign key to the excursion
    private String texte;
    private float note;
    private long timestamp;


    public Commentaire(int excursionId, String texte, float note, long timestamp) {
        this.excursionId = excursionId;
        this.texte = texte;
        this.note = note;
        this.timestamp = timestamp;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
