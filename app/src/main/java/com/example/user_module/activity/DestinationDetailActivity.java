package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Accommodation;

import java.util.ArrayList;
import java.util.List;

public class DestinationDetailActivity extends AppCompatActivity {

    private ImageView destinationImage;
    private TextView destinationName;
    private RecyclerView recommendationsRecyclerView;
    private RecommendationsAdapter recommendationsAdapter;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_detail);


        ImageView rightIcon = findViewById(R.id.right_icon);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationDetailActivity.this, ReservationListActivity.class);
                startActivity(intent);
            }
        });




        destinationImage = findViewById(R.id.destination_image);
        destinationName = findViewById(R.id.destination_name);
        //backButton = findViewById(R.id.back_button);
        TextView recommendationsTitle = findViewById(R.id.recommendations_title);
        recommendationsRecyclerView = findViewById(R.id.recommendations_recycler_view);

        if (getIntent() != null) {
            int imageResId = getIntent().getIntExtra("imageResId", 0);
            String name = getIntent().getStringExtra("name");

            destinationImage.setImageResource(imageResId);
            destinationName.setText(name);
            recommendationsTitle.setText("Recommandations à " + name);

            List<Accommodation> accommodations = loadRecommendations(name);
            recommendationsAdapter = new RecommendationsAdapter(this, accommodations);
            recommendationsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));

            recommendationsRecyclerView.setAdapter(recommendationsAdapter);
        }


        // Reference the back icon ImageView
        ImageView backIcon = findViewById(R.id.back_icon);

// Set up back icon click listener
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close this activity and return to the previous one
            }
        });

    }


    private List<Accommodation> loadRecommendations(String destinationName) {
        List<Accommodation> accommodations = new ArrayList<>();

        if ("Hammamet".equalsIgnoreCase(destinationName)) {
            accommodations.add(new Accommodation("Hôtel Amber El Fell", "Hammamet", "Hotel", 100, 150.0, true, "Luxury Hotel", R.drawable.sample_image1));
            accommodations.add(new Accommodation("Guest House Hammamet", "Hammamet", "Guest House", 50, 80.0, true, "Cozy Place", R.drawable.sample_image2));
            accommodations.add(new Accommodation("Traditional Stay Hammamet", "Hammamet", "Traditional", 30, 60.0, true, "Authentic Experience", R.drawable.sample_image3));
            accommodations.add(new Accommodation("Yadis Hammamet", "Hammamet", "Traditional", 30, 60.0, true, "Authentic Experience", R.drawable.sample_image4));
        }

        return accommodations;
    }
}
