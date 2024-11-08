package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Restaurant;

public class AddEditRestaurantActivity extends AppCompatActivity {

    private EditText editTextName, editTextLocation, editTextType, editTextCapacite;
    private int restaurantId = -1;  // -1 indicates "add" mode, otherwise it stores the existing restaurant ID for "edit" mode.

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_restaurant);

        // Initialize EditText fields
        editTextName = findViewById(R.id.edit_text_name);
        editTextLocation = findViewById(R.id.edit_text_location);
        editTextType = findViewById(R.id.edit_text_type);
        editTextCapacite = findViewById(R.id.edit_text_capacite);

        // Check if we are editing an existing restaurant
        Intent intent = getIntent();
        if (intent.hasExtra("restaurantId")) {
            setTitle("Edit Restaurant");
            restaurantId = intent.getIntExtra("restaurantId", -1);
            loadRestaurantData(restaurantId);  // Load data if editing
        } else {
            setTitle("Add Restaurant");
        }

        // Set click listener on the Save button
        findViewById(R.id.button_save).setOnClickListener(v -> saveRestaurant());
    }

    // Load existing restaurant data for editing
    private void loadRestaurantData(int id) {
        new Thread(() -> {
            // Fetch the restaurant from the database using AppDatabase and the DAO
            Restaurant restaurant = AppDatabase.getInstance(this).restaurantDao().getRestaurantById(id);
            runOnUiThread(() -> {
                if (restaurant != null) {
                    editTextName.setText(restaurant.getName());
                    editTextLocation.setText(restaurant.getLocation());
                    editTextType.setText(restaurant.getType());
                    editTextCapacite.setText(restaurant.getCapacite());
                }
            });
        }).start();
    }

    // Save the restaurant (insert or update based on mode)
    private void saveRestaurant() {
        // Retrieve text from EditText fields
        String name = editTextName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        String type = editTextType.getText().toString().trim();
        String capacite = editTextCapacite.getText().toString().trim();

        // Validate fields
        if (name.isEmpty() || location.isEmpty() || type.isEmpty() || capacite.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Restaurant object
        Restaurant restaurant = new Restaurant(restaurantId == -1 ? 0 : restaurantId, name, location, type, capacite);

        // Perform database operation in a background thread
        new Thread(() -> {
            if (restaurantId == -1) {
                // Insert new restaurant if in "add" mode
                AppDatabase.getInstance(this).restaurantDao().insert(restaurant);
            } else {
                // Update existing restaurant if in "edit" mode
                AppDatabase.getInstance(this).restaurantDao().update(restaurant);
            }

            // Show a toast and finish the activity on the main thread
            runOnUiThread(() -> {
                Toast.makeText(AddEditRestaurantActivity.this, "Restaurant saved", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
