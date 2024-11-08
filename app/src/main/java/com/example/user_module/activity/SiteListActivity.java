package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.Adapter.SiteAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;

public class SiteListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSites;
    private Button buttonAddSite;
    private SiteAdapter siteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_site);

        recyclerViewSites = findViewById(R.id.recyclerViewSites);
        buttonAddSite = findViewById(R.id.buttonAddSite);

        recyclerViewSites.setLayoutManager(new LinearLayoutManager(this));
        loadSites();

        buttonAddSite.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditSiteActivity.class);
            startActivity(intent);
        });
    }

    private void loadSites() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.siteDao().getAllSites().observe(this, sites -> {
            siteAdapter = new SiteAdapter(this, sites);
            recyclerViewSites.setAdapter(siteAdapter);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSites(); // Refresh the list when returning to this activity
    }
}
