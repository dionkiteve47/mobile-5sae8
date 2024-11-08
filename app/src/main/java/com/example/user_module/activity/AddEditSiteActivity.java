package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Site;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executors;

public class AddEditSiteActivity extends AppCompatActivity {

    private TextInputEditText editTextTitle, editTextDescription;
    private Button buttonSaveSite;
    private int siteId = -1; // Default to -1 to indicate a new site

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_site);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSaveSite = findViewById(R.id.buttonSaveForum); // ID matches the layout

        // Check if we are editing an existing site
        siteId = getIntent().getIntExtra("siteId", -1);
        if (siteId != -1) {
            loadSiteData();
        }

        buttonSaveSite.setOnClickListener(v -> saveSite());
    }

    private void loadSiteData() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.siteDao().getSiteById(siteId).observe(this, site -> {
            if (site != null) {
                editTextTitle.setText(site.getName());
                editTextDescription.setText(site.getDescription());
            }
        });
    }

    private void saveSite() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Site site = new Site();
        site.setName(title);
        site.setDescription(description);

        if (siteId != -1) {
            site.setId(siteId); // Set ID if editing
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            if (siteId == -1) {
                db.siteDao().insert(site); // Insert new site
            } else {
                db.siteDao().update(site); // Update existing site
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Site saved", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish(); // Close activity
            });
        });
    }
}
