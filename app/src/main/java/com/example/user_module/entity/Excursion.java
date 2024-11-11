package com.example.user_module.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion_table")
public class Excursion {

    @PrimaryKey
    private int id;  // Manually set primary key, no auto-generation
    private String region;  // Region of the excursion
    private String name;  // Name of the excursion
    private String description;  // Description of the excursion
    private int imageResourceId;  // Image resource ID (reference to drawable)

    // Constructor that accepts the id to be manually set
    public Excursion(int id, String region, String name, String description, int imageResourceId) {
        this.id = id;
        this.region = region;
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
