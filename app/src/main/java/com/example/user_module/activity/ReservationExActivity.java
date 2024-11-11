package com.example.user_module.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.user_module.AppDatabase;
import com.example.user_module.R;
import com.example.user_module.entity.Reservationn;

import java.util.Calendar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ReservationExActivity extends AppCompatActivity {

    private TextView textDate, textTime;
    private EditText numParticipants, editNotes;
    private Button btnReserve;
    private AppDatabase db;
    private final Executor databaseExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Initialize UI elements
        textDate = findViewById(R.id.text_date);
        textTime = findViewById(R.id.text_time);
        numParticipants = findViewById(R.id.num_participants);
        editNotes = findViewById(R.id.edit_notes);
        btnReserve = findViewById(R.id.button_submit_reservation);

        // Initialize Room database
        db = AppDatabase.getInstance(getApplicationContext());

        // Set up listeners for date and time selection
        textDate.setOnClickListener(v -> showDatePickerDialog());
        textTime.setOnClickListener(this::showTimePickerDialog);

        // Handle reservation button click
        btnReserve.setOnClickListener(v -> handleReservationClick());
    }

    // Show date picker dialog for date selection
    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    textDate.setText(selectedDate);
                },
                year, month, day);

        // Disable past dates
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    // Show time picker dialog for time selection
    public void showTimePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view1, selectedHour, selectedMinute) -> {
                    String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                    textTime.setText(selectedTime);
                },
                hour, minute, true);

        timePickerDialog.show();
    }

    // Handle reservation logic when the button is clicked
    private void handleReservationClick() {
        String selectedDate = textDate.getText().toString();
        String selectedTime = textTime.getText().toString();
        String participants = numParticipants.getText().toString();
        String notes = editNotes.getText().toString();

        // Validate that both date and time have been selected
        if (TextUtils.isEmpty(selectedDate)) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(selectedTime)) {
            Toast.makeText(this, "Please select a time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(participants) || !isValidNumber(participants)) {
            Toast.makeText(this, "Please enter a valid number of participants", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Reservation object
        Reservationn reservation = new Reservationn();
        reservation.setDate(selectedDate);
        reservation.setTime(selectedTime);
        reservation.setNumParticipants(Integer.parseInt(participants));
        reservation.setNotes(notes);

        // Insert the reservation into the database
        databaseExecutor.execute(() -> {
            db.reservationnDao().insert(reservation);
            runOnUiThread(() -> {
                Toast.makeText(ReservationExActivity.this, "Reservation successful", Toast.LENGTH_SHORT).show();
                clearFields();
                navigateToViewReservations();
            });
        });
    }

    // Clear fields after reservation
    private void clearFields() {
        textDate.setText("");
        textTime.setText("");
        numParticipants.setText("");
        editNotes.setText("");
    }

    // Navigate to ViewReservationsActivity
    private void navigateToViewReservations() {
        Intent intent = new Intent(ReservationExActivity.this, ViewReservationsActivity.class);
        startActivity(intent);
    }

    // Validate if the number of participants is a positive number
    private boolean isValidNumber(String input) {
        try {
            int num = Integer.parseInt(input);
            return num > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
