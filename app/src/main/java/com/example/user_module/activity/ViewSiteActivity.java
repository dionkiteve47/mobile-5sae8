package com.example.user_module.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.AppDatabase;
import com.example.user_module.R;

public class ViewSiteActivity extends AppCompatActivity {

    private TextView textViewName, textViewLocation, textViewDescription, textViewRating;
    private int siteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_site);

        textViewName = findViewById(R.id.textViewName);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewRating = findViewById(R.id.textViewRating);

        // Retrieve the site ID passed from the adapter
        siteId = getIntent().getIntExtra("siteId", -1);
        loadSiteDetails();
    }

    private void loadSiteDetails() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.siteDao().getSiteById(siteId).observe(this, site -> {
            if (site != null) {
                textViewName.setText(site.getName());
                textViewLocation.setText(site.getLocation());
                textViewDescription.setText(site.getDescription());
                textViewRating.setText(String.valueOf(site.getRating())); // Display rating as needed
            } else {
                Toast.makeText(this, "Site details not found", Toast.LENGTH_SHORT).show();
                finish(); // Close activity if site is not found
            }
        });
    }
}
