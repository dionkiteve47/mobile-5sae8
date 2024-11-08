// MainActivity.java
package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
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

    private ImageView destinationImage;
    private TextView destinationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize UI elements for displaying selected destination
        //destinationImage = findViewById(R.id.destination_image);
       // destinationName = findViewById(R.id.destination_name);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // Set 3 columns

        // Initialize Destination data
        List<Destination> destinations = new ArrayList<>();
        destinations.add(new Destination(R.drawable.ic_hammamet, "Hammamet"));
        destinations.add(new Destination(R.drawable.ic_sousse, "Sousse"));
        destinations.add(new Destination(R.drawable.ic_djerba, "Djerba"));
        destinations.add(new Destination(R.drawable.ic_tbarka, "Tabarka"));
        destinations.add(new Destination(R.drawable.ic_tozeur, "Tozeur"));
        destinations.add(new Destination(R.drawable.ic_mahdia, "Mahdia"));



        // Set the adapter
        DestinationAdapter adapter = new DestinationAdapter(destinations, this);
        recyclerView.setAdapter(adapter);
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
