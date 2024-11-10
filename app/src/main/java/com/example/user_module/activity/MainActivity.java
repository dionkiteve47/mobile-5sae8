// MainActivity.java
package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.Adapter.DestinationAdapter;
import com.example.user_module.R;
import com.example.user_module.entity.Destination;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DestinationAdapter.OnItemClickListener {

    private List<Destination> destinations;
    private List<Destination> filteredDestinations;
    private DestinationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView and Search Box
        EditText searchBox = findViewById(R.id.search_box);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Set 3 columns

        // Initialize Destination data
        destinations = new ArrayList<>();
        filteredDestinations = new ArrayList<>();
        destinations.add(new Destination(R.drawable.ic_hammamet, "Hammamet"));
        destinations.add(new Destination(R.drawable.ic_sousse, "Sousse"));
        destinations.add(new Destination(R.drawable.ic_djerba, "Djerba"));
        destinations.add(new Destination(R.drawable.ic_tbarka, "Tabarka"));
        destinations.add(new Destination(R.drawable.ic_tozeur, "Tozeur"));
        destinations.add(new Destination(R.drawable.ic_mahdia, "Mahdia"));

        // Initialize filtered list with all destinations initially
        filteredDestinations.addAll(destinations);

        // Set up the adapter with filtered list
        adapter = new DestinationAdapter(filteredDestinations, this);
        recyclerView.setAdapter(adapter);

        // Search functionality
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

        // Hotel and Restaurant section toggle
        LinearLayout restaurantSection = findViewById(R.id.restaurant_section);
        LinearLayout restaurantview = findViewById(R.id.restaurant_view);
        LinearLayout hotelSection = findViewById(R.id.hotel_section);
        LinearLayout searchSection = findViewById(R.id.search_section);

        // Set default visibility
        searchSection.setVisibility(View.VISIBLE);
        restaurantview.setVisibility(View.GONE);
        hotelSection.performClick(); // Simulate click on hotelSection

        hotelSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchSection.setVisibility(View.VISIBLE);
                restaurantview.setVisibility(View.GONE); // Hide restaurant view
            }
        });

        restaurantSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantview.setVisibility(View.VISIBLE);
                searchSection.setVisibility(View.GONE); // Hide search section
            }
        });
    }

    private void filterDestinations(String query) {
        // Clear the filtered list and search through the original list
        filteredDestinations.clear();
        if (query.isEmpty()) {
            filteredDestinations.addAll(destinations); // Show all if query is empty
        } else {
            for (Destination destination : destinations) {
                if (destination.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredDestinations.add(destination);
                }
            }
        }
        adapter.notifyDataSetChanged(); // Refresh RecyclerView
    }

    @Override
    public void onItemClick(Destination destination) {
        // Create an intent to start the DestinationDetailActivity
        Intent intent = new Intent(this, DestinationDetailActivity.class);
        // Pass the selected destination data to the new Activity
        intent.putExtra("imageResId", destination.getImageResId());
        intent.putExtra("name", destination.getName());
        startActivity(intent); // Start the new Activity
    }
}
