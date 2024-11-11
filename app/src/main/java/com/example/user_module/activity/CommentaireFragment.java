package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Commentaire;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommentaireFragment extends Fragment {

    private EditText editTextCommentaire;
    private RatingBar ratingBar;
    private Button buttonSubmitCommentaire;
    private int excursionId;

    // Reference to parent fragment
    private ExcursionDetailFragment excursionDetailFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commentaire, container, false);

        if (getArguments() != null) {
            excursionId = getArguments().getInt("excursion_id", -1);
        }

        // Initialize UI elements
        editTextCommentaire = rootView.findViewById(R.id.edit_text_commentaire);
        ratingBar = rootView.findViewById(R.id.rating_bar);
        buttonSubmitCommentaire = rootView.findViewById(R.id.button_submit_commentaire);

        // Initialize the parent fragment reference (ExcursionDetailFragment)
        if (getParentFragment() instanceof ExcursionDetailFragment) {
            excursionDetailFragment = (ExcursionDetailFragment) getParentFragment();
        }

        // Button click listener for submitting the commentaire
        buttonSubmitCommentaire.setOnClickListener(v -> {
            String commentaireText = editTextCommentaire.getText().toString();
            float rating = ratingBar.getRating();

            if (!commentaireText.isEmpty()) {
                long timestamp = System.currentTimeMillis(); // Get the current timestamp

                // Create a new Commentaire object
                Commentaire newCommentaire = new Commentaire(excursionId, commentaireText, rating, timestamp);

                // Insert the new commentaire into the database in the background thread
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    AppDatabase.getInstance(getActivity()).commentaireDao().insert(newCommentaire);

                    // Notify the parent fragment to refresh the comment list
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Commentaire ajouté", Toast.LENGTH_SHORT).show();
                        editTextCommentaire.setText(""); // Clear the comment field
                        ratingBar.setRating(0); // Reset the rating bar

                        // Refresh the comments in the parent fragment
                        if (excursionDetailFragment != null) {
                            excursionDetailFragment.loadComments();
                        }
                    });
                });
            } else {
                Toast.makeText(getActivity(), "Veuillez entrer un commentaire", Toast.LENGTH_SHORT).show();
            }
        });

        // Initialize the "Réserver" button
        Button buttonReserver = rootView.findViewById(R.id.button_reserver);

        // Set an OnClickListener to navigate to ReservationExActivity
        buttonReserver.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReservationExActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}
