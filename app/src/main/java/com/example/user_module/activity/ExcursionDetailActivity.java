package com.example.user_module.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.user_module.R;


public class ExcursionDetailActivity extends AppCompatActivity {

    private int excursionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_detail);

        // Get excursion ID passed from the previous activity or intent
        if (getIntent() != null) {
            excursionId = getIntent().getIntExtra("excursion_id", -1);
        }

        // Create and set the ExcursionDetailFragment
        ExcursionDetailFragment detailFragment = new ExcursionDetailFragment();
        Bundle args = new Bundle();
        args.putInt("excursion_id", excursionId);
        args.putString("excursion_name", getIntent().getStringExtra("excursion_name"));
        args.putString("excursion_region", getIntent().getStringExtra("excursion_region"));
        args.putString("excursion_description", getIntent().getStringExtra("excursion_description"));
        args.putInt("excursion_image", getIntent().getIntExtra("excursion_image", R.drawable.ic_launcher_background));
        detailFragment.setArguments(args);

        // Start the fragment transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.commit();

        // Create and set the CommentaireFragment
        CommentaireFragment commentaireFragment = new CommentaireFragment();
        commentaireFragment.setArguments(args);

        // Start the fragment transaction for commentaires (below the details fragment)
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.fragment_container2, commentaireFragment);
        transaction2.commit();
    }
}
