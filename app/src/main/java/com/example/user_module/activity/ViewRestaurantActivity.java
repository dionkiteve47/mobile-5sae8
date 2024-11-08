package com.example.user_module.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Restaurant;

public class ViewRestaurantActivity extends AppCompatActivity {

    private TextView textViewName, textViewLocation, textViewType, textViewCapacite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant);

        // Initialize TextViews
        textViewName = findViewById(R.id.text_view_name);
        textViewLocation = findViewById(R.id.text_view_location);
        textViewType = findViewById(R.id.text_view_type);
        textViewCapacite = findViewById(R.id.text_view_capacite);

        // Get the restaurant ID from the Intent
        int restaurantId = getIntent().getIntExtra("restaurantId", -1);

        // Load restaurant details in a background thread
        new Thread(() -> {
            Restaurant restaurant = AppDatabase.getInstance(this).restaurantDao().getRestaurantById(restaurantId);
            if (restaurant != null) {
                runOnUiThread(() -> {
                    textViewName.setText(restaurant.getName());
                    textViewLocation.setText(restaurant.getLocation());
                    textViewType.setText(restaurant.getType());
                    textViewCapacite.setText(restaurant.getCapacite());
                });
            }
        }).start();
    }
}
