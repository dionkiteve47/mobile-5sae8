package com.example.user_module.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.user_module.R;
import com.example.user_module.entity.Reservationn;

import java.util.List;


public class ReservationnAdapter extends RecyclerView.Adapter<ReservationnAdapter.ViewHolder> {

    private final List<Reservationn> reservations;
    private final OnDeleteClickListener deleteClickListener;

    // Constructor now accepts an OnDeleteClickListener to handle delete actions
    public ReservationnAdapter(List<Reservationn> reservations, OnDeleteClickListener deleteClickListener) {
        this.reservations = reservations;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservationn reservation = reservations.get(position);
        holder.dateText.setText(reservation.getDate());
        holder.timeText.setText(reservation.getTime());
        holder.participantsText.setText(String.valueOf(reservation.getNumParticipants()));
        holder.notesText.setText(reservation.getNotes());

        // Set the delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            // Call the deleteClickListener to handle the delete action
            deleteClickListener.onDeleteClick(reservation, position);
        });
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    // ViewHolder class now includes the delete button
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateText, timeText, participantsText, notesText;
        private final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.text_reservation_date);
            timeText = itemView.findViewById(R.id.text_reservation_time);
            participantsText = itemView.findViewById(R.id.text_reservation_participants);
            notesText = itemView.findViewById(R.id.text_reservation_notes);
            deleteButton = itemView.findViewById(R.id.delete_button);  // Initialize delete button
        }
    }

    // Interface to handle delete button click
    public interface OnDeleteClickListener {
        void onDeleteClick(Reservationn reservation, int position);
    }
}
