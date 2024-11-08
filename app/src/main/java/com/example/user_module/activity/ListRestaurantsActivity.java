package com.example.user_module.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.user_module.R;
import com.example.user_module.Adapter.RestaurantAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Restaurant;
import java.util.List;
import java.util.concurrent.Executors;

public class ListRestaurantsActivity extends AppCompatActivity {

    private RestaurantAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_restaurants);

        // Set up RecyclerView and adapter
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RestaurantAdapter();
        recyclerView.setAdapter(adapter);

        // Set up the adapter's listener for menu actions
        adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onViewClick(Restaurant restaurant) {
                Intent intent = new Intent(ListRestaurantsActivity.this, ViewRestaurantActivity.class);
                intent.putExtra("restaurantId", restaurant.getId());
                startActivity(intent);
            }

            @Override
            public void onEditClick(Restaurant restaurant) {
                Intent intent = new Intent(ListRestaurantsActivity.this, AddEditRestaurantActivity.class);
                intent.putExtra("restaurantId", restaurant.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Restaurant restaurant) {
                // Execute delete in the background using AsyncTask
                new DeleteRestaurantTask().execute(restaurant);
            }
        });

        // Load all restaurants and observe for changes
        LiveData<List<Restaurant>> allRestaurants = AppDatabase.getInstance(this).restaurantDao().getAllRestaurants();
        allRestaurants.observe(this, adapter::submitList);

        // Set up the "Add Restaurant" button
        findViewById(R.id.button_add_restaurant).setOnClickListener(v -> {
            Intent intent = new Intent(ListRestaurantsActivity.this, AddEditRestaurantActivity.class);
            startActivity(intent);
        });
    }

    // AsyncTask for deleting a restaurant in the background
    private class DeleteRestaurantTask extends AsyncTask<Restaurant, Void, Void> {
        @Override
        protected Void doInBackground(Restaurant... restaurants) {
            AppDatabase.getInstance(ListRestaurantsActivity.this).restaurantDao().delete(restaurants[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ListRestaurantsActivity.this, "Restaurant deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
