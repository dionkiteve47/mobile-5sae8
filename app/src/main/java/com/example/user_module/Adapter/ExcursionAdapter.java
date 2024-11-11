package com.example.user_module.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> excursions;
    private Context context;
    private OnItemClickListener listener;

    // Constructor with context and listener
    public ExcursionAdapter(List<Excursion> excursions, Context context, OnItemClickListener listener) {
        this.excursions = excursions;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ExcursionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout for each RecyclerView item
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_excursion, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExcursionViewHolder holder, int position) {
        // Get the excursion data for the current position
        Excursion excursion = excursions.get(position);

        // Set the data to the views
        holder.excursionName.setText(excursion.getName());
        holder.excursionDescription.setText(excursion.getDescription());

        // Set image resource; ensure image resource exists or handle dynamic image loading
        if (excursion.getImageResourceId() != 0) {
            holder.imageView.setImageResource(excursion.getImageResourceId());
        } else {
            // Set a placeholder or default image if no resource ID is available
            holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        }

        // Set click listener for the item
        holder.itemView.setOnClickListener(v -> listener.onItemClick(excursion));
    }

    @Override
    public int getItemCount() {
        return excursions != null ? excursions.size() : 0;
    }

    public static class ExcursionViewHolder extends RecyclerView.ViewHolder {

        public TextView excursionName;
        public TextView excursionDescription;
        public ImageView imageView;

        public ExcursionViewHolder(View itemView) {
            super(itemView);

            // Initialize the views
            excursionName = itemView.findViewById(R.id.text_view_excursion_name);  // Updated ID to match XML
            excursionDescription = itemView.findViewById(R.id.text_view_excursion_description); // Updated ID to match XML
            imageView = itemView.findViewById(R.id.image_view_excursion_placeholder);  // Updated ID to match XML
        }
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(Excursion excursion);
    }
}
