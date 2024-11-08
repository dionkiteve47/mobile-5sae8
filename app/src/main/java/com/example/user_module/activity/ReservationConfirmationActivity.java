package com.example.user_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.user_module.R;
import com.example.user_module.AppDatabase;
import com.example.user_module.entity.Reservation;

public class ReservationConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirmation);
        setupBackIcon();



        Button confirmButton = findViewById(R.id.confirm_button_id);
        confirmButton.setOnClickListener(view -> {
            // Collect data from the form fields
            String firstName = ((EditText) findViewById(R.id.first_name)).getText().toString();
            String lastName = ((EditText) findViewById(R.id.last_name)).getText().toString();
            String email = ((EditText) findViewById(R.id.email)).getText().toString();
            String phone = ((EditText) findViewById(R.id.phone)).getText().toString();
            String message = ((EditText) findViewById(R.id.message_field)).getText().toString(); // Ensure this field exists
// Other fields like room details, payment method, etc.

            // Get occupant's information
            String occupantFirstName = ((EditText) findViewById(R.id.occupant_first_name)).getText().toString();
            String occupantLastName = ((EditText) findViewById(R.id.family_name)).getText().toString();

            // Get payment option
            boolean paymentOnline = ((RadioButton) findViewById(R.id.payment_online)).isChecked();
            boolean payment_agency = ((RadioButton) findViewById(R.id.payment_agency)).isChecked();
            // Get special requests
            boolean lateArrival = ((CheckBox) findViewById(R.id.late_arrival_checkbox)).isChecked();
            boolean sideBySide = ((CheckBox) findViewById(R.id.side_by_side_rooms_checkbox)).isChecked();
            boolean kingBed = ((CheckBox) findViewById(R.id.king_bed_checkbox)).isChecked();


            boolean mr = ((RadioButton) findViewById(R.id.radio_mr)).isChecked();
            boolean mm = ((RadioButton) findViewById(R.id.radio_mme)).isChecked();
            boolean mlle = ((RadioButton) findViewById(R.id.radio_mlle)).isChecked();

            // Get terms acceptance
            boolean termsAccepted = ((CheckBox) findViewById(R.id.terms_conditions_checkbox)).isChecked();

            // Create Reservation object
            Reservation reservation = new Reservation(
                    firstName,
                    lastName,
                    email,
                    phone,
                    occupantFirstName,
                    occupantLastName,
                    message,
                    lateArrival,
                    sideBySide,
                    kingBed,
                    paymentOnline,
                    termsAccepted,
                    payment_agency, // New field
                    mr, // New field
                    mm, // New field
                    mlle // New field
            );

            // Save to database
            AppDatabase db = AppDatabase.getInstance(this);
            new Thread(() -> {
                db.reservationDao().insertReservation(reservation);

                // After saving, retrieve the ID of the saved reservation
                int reservationId = db.reservationDao().getLastInsertedId();

                // Pass ID to detail activity
                Intent intent = new Intent(ReservationConfirmationActivity.this, ReservationConfirmationDetailActivity.class);
                intent.putExtra("reservationId", reservationId);
                startActivity(intent);
            }).start();
        });


        // Set the content to extend into the system windows
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);


        // Apply padding for system bars
        View mainView = findViewById(R.id.main);  // Ensure your root layout has this ID
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return WindowInsetsCompat.CONSUMED;
            });
        }


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

}
