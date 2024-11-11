package com.example.user_module.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.user_module.R;
import com.example.user_module.activity.AddEditSiteActivity;
import com.example.user_module.activity.SiteListActivity;
import com.example.user_module.activity.ViewSiteActivity;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Site;

import java.util.List;
import java.util.concurrent.Executors;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteViewHolder> {

    private Context context;
    private List<Site> siteList;

    public SiteAdapter(Context context, List<Site> siteList) {
        this.context = context;
        this.siteList = siteList;
    }

    @NonNull
    @Override
    public SiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_site, parent, false);
        return new SiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SiteViewHolder holder, int position) {
        Site site = siteList.get(position);
        holder.textViewName.setText(site.getName());
        holder.textViewLocation.setText(site.getLocation());

        // Load the image using Glide if imageUri is available
        if (site.getImageUri() != null && !site.getImageUri().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(site.getImageUri())
                    .placeholder(R.drawable.ic_add) // Optional placeholder
                    .into(holder.imageViewSite);  // Load image into ImageView
        }

        // Set up the menu button with options
        holder.buttonMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, holder.buttonMenu);
            popupMenu.inflate(R.menu.menu_site_item);

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_view) {
                    viewSite(site);
                    return true;
                } else if (item.getItemId() == R.id.menu_edit) {
                    editSite(site);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    deleteSite(site);
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }


    @Override
    public int getItemCount() {
        return siteList.size();
    }

    private void viewSite(Site site) {
        Intent intent = new Intent(context, ViewSiteActivity.class);
        intent.putExtra("siteId", site.getId());
        context.startActivity(intent);
    }

    private void editSite(Site site) {
        Intent intent = new Intent(context, AddEditSiteActivity.class);
        intent.putExtra("siteId", site.getId());
        context.startActivity(intent);
    }

    private void deleteSite(Site site) {
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(context);
            db.siteDao().delete(site);

            ((SiteListActivity) context).runOnUiThread(() -> {
                siteList.remove(site);
                notifyDataSetChanged();
                Toast.makeText(context, "Site deleted", Toast.LENGTH_SHORT).show();
            });
        });
    }

    public static class SiteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewLocation;
        ImageButton buttonMenu;
        ImageView imageViewSite; // New ImageView for site image

        public SiteViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            buttonMenu = itemView.findViewById(R.id.buttonMenu);
            imageViewSite = itemView.findViewById(R.id.image_view_site); // Initialize ImageView
        }
    }

}
