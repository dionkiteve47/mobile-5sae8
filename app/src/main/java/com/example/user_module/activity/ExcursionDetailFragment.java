package com.example.user_module.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.user_module.Adapter.CommentaireAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Commentaire;

import java.util.List;

public class ExcursionDetailFragment extends Fragment {

    private TextView nameTextView;
    private TextView regionTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private int excursionId;
    private CommentaireAdapter commentaireAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_excursion_detail, container, false);

        // Initialize UI components
        nameTextView = rootView.findViewById(R.id.excursion_name);
        regionTextView = rootView.findViewById(R.id.excursion_region);
        descriptionTextView = rootView.findViewById(R.id.excursion_description);
        imageView = rootView.findViewById(R.id.excursion_image);
        recyclerView = rootView.findViewById(R.id.recycler_view_comments);

        // Get excursion data passed from the parent activity or fragment
        if (getArguments() != null) {
            String excursionName = getArguments().getString("excursion_name", "N/A");
            String excursionRegion = getArguments().getString("excursion_region", "N/A");
            String excursionDescription = getArguments().getString("excursion_description", "No description available.");
            int excursionImage = getArguments().getInt("excursion_image", R.drawable.ic_launcher_background);
            excursionId = getArguments().getInt("excursion_id", -1);

            // Set the excursion data to the UI elements
            nameTextView.setText(excursionName);
            regionTextView.setText(excursionRegion);
            descriptionTextView.setText(excursionDescription);
            imageView.setImageResource(excursionImage);
        }

        // Set up RecyclerView for displaying comments
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        commentaireAdapter = new CommentaireAdapter();
        recyclerView.setAdapter(commentaireAdapter);

        // Load the comments for this excursion
        loadComments();

        return rootView;
    }

    // Public method to load comments from the database
    public void loadComments() {
        // Ensure excursionId is valid before querying the database
        if (excursionId != -1) {
            // Observe the LiveData object and update the UI when comments change
            AppDatabase.getInstance(getActivity()).commentaireDao().getLatestCommentairesByExcursionId(excursionId)
                    .observe(getViewLifecycleOwner(), new Observer<List<Commentaire>>() {
                        @Override
                        public void onChanged(List<Commentaire> comments) {
                            if (comments != null && !comments.isEmpty()) {
                                // Update the RecyclerView adapter with the new list of comments
                                commentaireAdapter.setComments(comments);
                            } else {
                                // Show a message if no comments are found
                                Toast.makeText(getActivity(), "No comments available for this excursion.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Handle case where excursionId is invalid or not passed
            Toast.makeText(getActivity(), "Invalid excursion ID.", Toast.LENGTH_SHORT).show();
        }
    }
}
