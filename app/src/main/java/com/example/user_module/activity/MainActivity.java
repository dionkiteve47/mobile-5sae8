package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.Adapter.DestinationAdapter;
import com.example.user_module.Adapter.RestaurantAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Destination;
import com.example.user_module.entity.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DestinationAdapter.OnItemClickListener,RestaurantAdapter.OnItemClickListener {

    private List<Destination> destinations;
    private List<Destination> filteredDestinations;
    private DestinationAdapter destinationAdapter;

    private List<Restaurant> restaurants;
    private List<Restaurant> filteredRestaurants;
    private RestaurantAdapter restaurantAdapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Ensure this is configured if necessary
        setContentView(R.layout.activity_main);

        ImageView addIcon = findViewById(R.id.add_icon); // Find the add icon
        addIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListRestaurantsActivity.class); // Create the intent
            startActivity(intent); // Start the AddEditRestaurantActivity
        });

        // Initialize Search Box for restaurants
        EditText searchBoxRestaurant = findViewById(R.id.search_box_Restaurant);

        // Initialize RecyclerView and Search Box for destinations
        EditText searchBox = findViewById(R.id.search_box);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Destination data
        destinations = new ArrayList<>();
        filteredDestinations = new ArrayList<>();
        populateDestinations(); // Separate method for populating destinations
        filteredDestinations.addAll(destinations);

        // Set up the adapter for destinations
        destinationAdapter = new DestinationAdapter(filteredDestinations, this);
        recyclerView.setAdapter(destinationAdapter);

        // Search functionality for destinations
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterDestinations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Add a TextWatcher to filter restaurants based on search query
        searchBoxRestaurant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRestaurants(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Hotel and Restaurant section toggle
        LinearLayout restaurantSection = findViewById(R.id.restaurant_section);
        LinearLayout restaurantView = findViewById(R.id.restaurant_view);
        LinearLayout hotelSection = findViewById(R.id.hotel_section);
        LinearLayout searchSection = findViewById(R.id.search_section);

        searchSection.setVisibility(View.VISIBLE);
        restaurantView.setVisibility(View.GONE);

        hotelSection.setOnClickListener(v -> {
            searchSection.setVisibility(View.VISIBLE);
            restaurantView.setVisibility(View.GONE);
        });

        restaurantSection.setOnClickListener(v -> {
            restaurantView.setVisibility(View.VISIBLE);
            searchSection.setVisibility(View.GONE);
        });

        // Initialize RecyclerView for restaurants
        RecyclerView recyclerViewRestaurant = findViewById(R.id.recyclerViewRestaurant);
        recyclerViewRestaurant.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize Restaurant data
        restaurants = new ArrayList<>();
        filteredRestaurants = new ArrayList<>();
        populateRestaurants();
        filteredRestaurants.addAll(restaurants);

        // Set up the adapter for restaurants with OnItemClickListener
        restaurantAdapter = new RestaurantAdapter();
        restaurantAdapter.setOnItemClickListener(this); // Pass the listener
        recyclerViewRestaurant.setAdapter(restaurantAdapter);
    }

    private void populateDestinations() {
        destinations.add(new Destination(R.drawable.ic_hammamet, "Hammamet"));
        destinations.add(new Destination(R.drawable.ic_sousse, "Sousse"));
        destinations.add(new Destination(R.drawable.ic_djerba, "Djerba"));
        destinations.add(new Destination(R.drawable.ic_tbarka, "Tabarka"));
        destinations.add(new Destination(R.drawable.ic_tozeur, "Tozeur"));
        destinations.add(new Destination(R.drawable.ic_mahdia, "Mahdia"));
    }

    private void populateRestaurants() {
        // Observe the LiveData to get the list of restaurants asynchronously
        LiveData<List<Restaurant>> allRestaurants = AppDatabase.getInstance(this).restaurantDao().getAllRestaurants();
        allRestaurants.observe(this, restaurantList -> {
            // When the LiveData is updated, clear and update the filtered list
            filteredRestaurants.clear();
            filteredRestaurants.addAll(restaurantList); // Populate the filtered list
            restaurantAdapter.submitList(filteredRestaurants); // Update the adapter with the filtered list
        });
    }



    private void filterDestinations(String query) {
        filteredDestinations.clear();
        if (query.isEmpty()) {
            filteredDestinations.addAll(destinations);
        } else {
            for (Destination destination : destinations) {
                if (destination.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredDestinations.add(destination);
                }
            }
        }
        destinationAdapter.notifyDataSetChanged();
    }

    private void filterRestaurants(String query) {
        List<Restaurant> filteredList = new ArrayList<>();
        if (query.isEmpty()) {
            filteredList.addAll(filteredRestaurants); // Show all restaurants if query is empty
        } else {
            for (Restaurant restaurant : filteredRestaurants) {
                if (restaurant.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(restaurant);
                }
            }
        }
        restaurantAdapter.submitList(filteredList); // Submit the filtered list to the adapter
    }

    @Override
    public void onItemClick(Destination destination) {
        Intent intent = new Intent(this, DestinationDetailActivity.class);
        intent.putExtra("imageResId", destination.getImageResId());
        intent.putExtra("name", destination.getName());
        startActivity(intent);
    }

    @Override
    public void onViewClick(Restaurant restaurant) {
        // Handle view action for restaurant
    }

    @Override
    public void onEditClick(Restaurant restaurant) {
        // Handle edit action for restaurant
    }

    @Override
    public void onDeleteClick(Restaurant restaurant) {
        // Handle delete action for restaurant
    }
  /*  @Override
    public void onItemClick(Restaurant restaurant) {
        Log.d("MainActivity", "Restaurant clicked: " + restaurant.getName());
        Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
        intent.putExtra("name", restaurant.getName());
        intent.putExtra("location", restaurant.getLocation());
        intent.putExtra("type", restaurant.getType());
        intent.putExtra("capacity", restaurant.getCapacite());
        intent.putExtra("imageResId", restaurant.getImageUri());
        startActivity(intent);
    }*/
}
