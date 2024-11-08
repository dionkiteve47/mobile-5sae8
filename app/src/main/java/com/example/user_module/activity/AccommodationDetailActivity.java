package com.example.user_module.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.user_module.R;

import java.util.Calendar;

public class AccommodationDetailActivity extends AppCompatActivity {

    private ImageView accommodationImage;
    private TextView accommodationName, accommodationLocation, accommodationType,
            accommodationCapacity, accommodationPrice, accommodationAvailability, accommodationTitle;
    private TextView roomOccupancyTextView;
    private int selectedRooms = 1;
    private int selectedAdults = 2;
    private EditText arrivalDateEditText;
    private EditText departureDateEditText;
    private Button checkAvailabilityButton;
    private TableLayout availabilityTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_detail);

        initializeViews();
        setupBackIcon();
        setupDatePickers();
        setupCheckAvailabilityButton();
        displayAccommodationDetails();

    // Setup the confirm button to navigate to ReservationConfirmationActivity
    Button confirmButton1 = findViewById(R.id.confirm_button);
    confirmButton1.setOnClickListener(v -> {
        Intent intent = new Intent(AccommodationDetailActivity.this, ReservationConfirmationActivity.class);
        startActivity(intent);
    }); }
    private void initializeViews() {
        roomOccupancyTextView = findViewById(R.id.room_occupancy);
        roomOccupancyTextView.setText(selectedRooms + " chambre(s), " + selectedAdults + " adulte(s)");

        roomOccupancyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRoomOccupancyDialog();
            }
        });

        accommodationImage = findViewById(R.id.accommodation_image);
        accommodationName = findViewById(R.id.accommodation_name);
        accommodationLocation = findViewById(R.id.accommodation_location);
        accommodationType = findViewById(R.id.accommodation_type);
        accommodationCapacity = findViewById(R.id.accommodation_capacity);
        accommodationPrice = findViewById(R.id.accommodation_price);
        accommodationAvailability = findViewById(R.id.accommodation_availability);
        accommodationTitle = findViewById(R.id.accommodation_title);

        arrivalDateEditText = findViewById(R.id.arrival_date);
        departureDateEditText = findViewById(R.id.departure_date);
        checkAvailabilityButton = findViewById(R.id.check_availability_button);
        availabilityTable = findViewById(R.id.availability_table);
    }

    private void setupBackIcon() {
        ImageView backIcon = findViewById(R.id.back_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close this activity and return to the previous one
            }
        });
    }

    private void setupDatePickers() {
        arrivalDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(arrivalDateEditText); // Fix typo here
            }
        });

        departureDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(departureDateEditText);
            }
        });
    }

    private void setupCheckAvailabilityButton() {
        checkAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAvailabilityTable();
            }
        });
    }

    private void displayAccommodationDetails() {
        // Get the data from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accommodationName.setText(extras.getString("name", "Unknown Name"));
            accommodationLocation.setText(extras.getString("location", "Unknown Location"));
            accommodationType.setText(extras.getString("type", "Unknown Type"));
            accommodationCapacity.setText("Capacity: " + extras.getInt("capacity", 1));
            accommodationPrice.setText("Price per night: " + extras.getDouble("pricePerNight", 0.0) + " DT");
            accommodationAvailability.setText(extras.getBoolean("isAvailable", false) ? "Available" : "Not Available");
            accommodationTitle.setText(extras.getString("title", "Unknown Title"));
            accommodationImage.setImageResource(extras.getInt("imageResId")); // Use a default image if not set
        }
    }

    private void toggleAvailabilityTable() {
        // Toggle visibility of the availability table
        if (availabilityTable.getVisibility() == View.GONE) {
            availabilityTable.setVisibility(View.VISIBLE);
            populateAvailabilityTable(); // Populate data when shown
        } else {
            availabilityTable.setVisibility(View.GONE);
        }
    }

    private void populateAvailabilityTable() {
        // Clear existing rows except header
       // availabilityTable.removeViews(1, availabilityTable.getChildCount() - 1);

        String[][] availabilityData = {
                {"1 x Chambre Double \n Disponible \n Annulation gratuite avant le 2024-10-29", "216.00 TND"},
                {"1 x Chambre double avec Salon \n Disponible \n Annulation gratuite avant le 2024-10-29", "216.00 TND"},
                {"1 x Suite Junior GV Double \n Disponible \n Annulation gratuite avant le 2024-10-29", "216.00 TND"},

        };

        // Loop to create rows
        for (String[] dataRow : availabilityData) {
            TableRow row = new TableRow(this);
            row.setPadding(8, 8, 8, 8);

            TextView roomType = new TextView(this);
            roomType.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
            roomType.setText(dataRow[0]);
            roomType.setPadding(8, 8, 8, 8);

            TextView totalPrice = new TextView(this);
            totalPrice.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            totalPrice.setText(dataRow[1]);
            totalPrice.setPadding(8, 8, 8, 8);

            row.addView(roomType);
            row.addView(totalPrice);
            availabilityTable.addView(row, availabilityTable.getChildCount() - 1); // Add before the last row
            //availabilityTable.addView(row);
        }
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        // Format and set the selected date
                        String selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                        editText.setText(selectedDate);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void showRoomOccupancyDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_room_occupancy);

        // Initialize SeekBars and TextViews
        SeekBar roomSeekBar = dialog.findViewById(R.id.room_count_seekbar);
        SeekBar adultSeekBar = dialog.findViewById(R.id.adult_count_seekbar);
        TextView roomCountLabel = dialog.findViewById(R.id.room_count_label);
        TextView adultCountLabel = dialog.findViewById(R.id.adult_count_label);
        Button confirmButton = dialog.findViewById(R.id.dialog_confirm_button); // Ensure this matches your XML

        if (roomSeekBar == null || adultSeekBar == null || roomCountLabel == null || adultCountLabel == null || confirmButton == null) {
            Log.e("AccommodationDetailActivity", "One or more views in the dialog are null");
            return; // Exit the method to avoid crashing
        }

        // SeekBar change listener for room count
        roomSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedRooms = progress;
                roomCountLabel.setText(String.valueOf(selectedRooms));
                Log.d("RoomOccupancy", "Rooms selected: " + selectedRooms);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // SeekBar change listener for adult count (similar setup)
        adultSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedAdults = progress;
                adultCountLabel.setText(String.valueOf(selectedAdults));
                Log.d("RoomOccupancy", "Adults selected: " + selectedAdults);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomOccupancyTextView.setText(selectedRooms + " chambre(s), " + selectedAdults + " adulte(s)");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}