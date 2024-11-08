package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Restaurant_table")
public class Restaurant {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String location;
    private String type;
    private String capacite;

    public Restaurant(int id, String name, String location, String type, String capacite) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.capacite = capacite;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getType() { return type; }
    public String getCapacite() { return capacite; } // Corrected getter method

    @Override
    public String toString() {
        return name + " - " + type + " - " + location + " - capacite: " + capacite;
    }
}
