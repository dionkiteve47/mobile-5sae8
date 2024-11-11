package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.user_module.Adapter.ExcursionAdapter;
import com.example.user_module.R;
import com.example.user_module.entity.Excursion;

import java.util.ArrayList;
import java.util.List;

public class ExcursionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExcursionAdapter excursionAdapter;
    private List<Excursion> excursionList;
    private View fabReservation;  // Declare the FAB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.recycler_view_excursions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of excursions
        excursionList = new ArrayList<>();

        // Adding some sample data with unique excursionId
        excursionList.add(new Excursion(1, "Safari Adventure", "South Africa", "Explore the wild safari.", R.drawable.sutha));  // Replace with actual image
        excursionList.add(new Excursion(2, "Beach Escape", "Thailand", "Relax on a beautiful beach.", R.drawable.thai));  // Replace with actual image
        excursionList.add(new Excursion(3, "Mountain Hiking", "Switzerland", "Climb the Swiss Alps.", R.drawable.switz));  // Replace with actual image

        // Initialize the adapter with data and listener
        excursionAdapter = new ExcursionAdapter(excursionList, this, new ExcursionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Excursion excursion) {
                // Handle item click to navigate to the detail activity
                Intent intent = new Intent(ExcursionActivity.this, ExcursionDetailActivity.class);
                intent.putExtra("excursion_id", excursion.getId());  // Pass the excursion ID
                intent.putExtra("excursion_name", excursion.getName());
                intent.putExtra("excursion_region", excursion.getRegion());
                intent.putExtra("excursion_description", excursion.getDescription());
                intent.putExtra("excursion_image", excursion.getImageResourceId());  // Pass the image resource ID
                startActivity(intent);
            }
        });

        // Set the adapter to the RecyclerView
        recyclerView.setAdapter(excursionAdapter);

        // Initialize the Floating Action Button
        fabReservation = findViewById(R.id.fab_reservation);

        // Set the FAB click listener to navigate to Reservation List Activity
        fabReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ReservationListActivity
                Intent intent = new Intent(ExcursionActivity.this, ViewReservationsActivity.class);
                startActivity(intent);  // Start the Reservation List Activity
            }
        });
    }
}
