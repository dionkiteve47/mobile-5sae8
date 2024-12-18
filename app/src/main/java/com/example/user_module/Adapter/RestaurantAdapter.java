package com.example.user_module.Adapter;// RestaurantAdapter.java
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Restaurant;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantHolder> {

    private OnItemClickListener listener;

    public RestaurantAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant>() {
        @Override
        public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getLocation().equals(newItem.getLocation()) &&
                    oldItem.getType().equals(newItem.getType()) &&
                    oldItem.getCapacite().equals(newItem.getCapacite());
        }
    };

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        Restaurant currentRestaurant = getItem(position);
        holder.textViewName.setText(currentRestaurant.getName());
        holder.textViewLocation.setText(currentRestaurant.getLocation());
        holder.textViewType.setText(currentRestaurant.getType());
        holder.textViewCapacite.setText(currentRestaurant.getCapacite());

        // Set up the options button to show a popup menu
        holder.imageViewOptions.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.imageViewOptions);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_restaurant_item, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> handleMenuItemClick(item, currentRestaurant));
            popupMenu.show();
        });
    }

    private boolean handleMenuItemClick(MenuItem item, Restaurant restaurant) {
        if (listener != null) {
            if (item.getItemId() == R.id.menu_view) {
                listener.onViewClick(restaurant);
                return true;
            } else if (item.getItemId() == R.id.menu_edit) {
                listener.onEditClick(restaurant);
                return true;
            } else if (item.getItemId() == R.id.menu_delete) {
                listener.onDeleteClick(restaurant);
                return true;
            }
        }
        return false;
    }


    class RestaurantHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewLocation;
        private final TextView textViewType;
        private final TextView textViewCapacite;
        private final ImageView imageViewOptions;

        public RestaurantHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewLocation = itemView.findViewById(R.id.text_view_location);
            textViewType = itemView.findViewById(R.id.text_view_type);
            textViewCapacite = itemView.findViewById(R.id.text_view_capacite);
            imageViewOptions = itemView.findViewById(R.id.image_view_options);
        }
    }

    public interface OnItemClickListener {
        void onViewClick(Restaurant restaurant);
        void onEditClick(Restaurant restaurant);
        void onDeleteClick(Restaurant restaurant);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
