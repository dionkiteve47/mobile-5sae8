package com.example.user_module.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.user_module.Adapter.ReservationnAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Reservationn;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewReservationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservationnAdapter adapter;
    private final Executor databaseExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservations);

        recyclerView = findViewById(R.id.recycler_view_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load reservations from database
        databaseExecutor.execute(() -> {
            // Change List<ReservationnAdapter> to List<Reservationn>
            List<Reservationn> reservations = AppDatabase.getInstance(getApplicationContext()).reservationnDao().getAllReservations();
            runOnUiThread(() -> {
                // Pass the delete listener to the adapter
                adapter = new ReservationnAdapter(reservations, new ReservationnAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(Reservationn reservation, int position) {
                        // Delete reservation from the database in a background thread
                        databaseExecutor.execute(() -> {
                            AppDatabase.getInstance(getApplicationContext()).reservationnDao().delete(reservation);
                            // Remove reservation from the list and update the RecyclerView
                            reservations.remove(position);
                            runOnUiThread(() -> {
                                adapter.notifyItemRemoved(position);
                                adapter.notifyItemRangeChanged(position, reservations.size());
                            });
                        });
                    }
                });
                recyclerView.setAdapter(adapter);
            });
        });
    }
}
