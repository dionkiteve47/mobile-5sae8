package com.example.user_module.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Commentaire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentaireAdapter extends RecyclerView.Adapter<CommentaireAdapter.CommentaireViewHolder> {

    private List<Commentaire> commentaires = new ArrayList<>(); // Initialize to avoid null

    @Override
    public CommentaireViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commentaire, parent, false);
        return new CommentaireViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentaireViewHolder holder, int position) {
        Commentaire commentaire = commentaires.get(position);

        // Bind data to UI components
        holder.textViewCommentaire.setText(commentaire.getTexte());

        // Display the rating as a numeric value in the TextView (you can customize as needed)
        holder.textViewRating.setText(String.valueOf(commentaire.getNote()));
    }

    @Override
    public int getItemCount() {
        return commentaires != null ? commentaires.size() : 0;  // Safe null check
    }

    // Method to update the list of comments
    public void setComments(List<Commentaire> commentaires) {
        // Update list only if it's different from the existing one
        if (commentaires != null) {
            this.commentaires.clear();

            // Sort the comments by date, assuming Commentaire has a date or timestamp field to sort by
            // For example, using `commentaire.getDate()` or `commentaire.getTimestamp()` depending on your model
            Collections.sort(commentaires, (c1, c2) -> Long.compare(c2.getTimestamp(), c1.getTimestamp()));

            // Get only the latest 4 comments
            List<Commentaire> latestComments = commentaires.size() > 4 ? commentaires.subList(0, 4) : commentaires;

            // Add the latest comments to the list
            this.commentaires.addAll(latestComments);
            notifyDataSetChanged();
        }
    }

    // ViewHolder class to hold the views
    public static class CommentaireViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCommentaire;
        TextView textViewRating; // Changed from RatingBar to TextView for rating

        public CommentaireViewHolder(View itemView) {
            super(itemView);
            textViewCommentaire = itemView.findViewById(R.id.commentaire_text);
            textViewRating = itemView.findViewById(R.id.commentaire_rating_text); // Initialize the TextView for rating
        }
    }
}
