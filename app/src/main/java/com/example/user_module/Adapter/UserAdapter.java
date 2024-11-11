package com.example.user_module.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.user_module.AppDatabase;
import com.example.user_module.Dao.UserDao;
import com.example.user_module.R;
import com.example.user_module.entity.User;

import java.util.List;
import java.util.concurrent.Executors;

public class UserAdapter extends ArrayAdapter<User> {

    private Context context;
    private List<User> users;

    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        }

        User user = users.get(position);

        TextView emailTextView = convertView.findViewById(R.id.user_email);
        TextView roleTextView = convertView.findViewById(R.id.user_role);
        ImageView optionsMenu = convertView.findViewById(R.id.user_options_menu);

        emailTextView.setText(user.email);
        roleTextView.setText(user.role);

        // Set up the options menu
        optionsMenu.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, optionsMenu);
            popupMenu.getMenuInflater().inflate(R.menu.user_options_menu, popupMenu.getMenu());

            // Handle menu item clicks with if-else
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.menu_show) {
                    showUserDetails(user);
                    return true;
                } else if (item.getItemId() == R.id.menu_edit) {
                    editUser(user);
                    return true;
                } else if (item.getItemId() == R.id.menu_delete) {
                    deleteUser(user);
                    return true;
                } else {
                    return false;
                }
            });
            popupMenu.show();
        });

        return convertView;
    }

    // Show user details
    private void showUserDetails(User user) {
        // Create a dialog to display user details
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("User Details");
        builder.setMessage("Email: " + user.email + "\nRole: " + user.role + "\nConfirmed: " + (user.confirmed ? "Yes" : "No"));
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    // Edit user details
    private void editUser(User user) {
        // Create a dialog with input fields for editing user details
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit User");

        // Set up the input fields
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText emailInput = new EditText(context);
        emailInput.setHint("Email");
        emailInput.setText(user.email);
        layout.addView(emailInput);

        final EditText roleInput = new EditText(context);
        roleInput.setHint("Role (USER/ADMIN)");
        roleInput.setText(user.role);
        layout.addView(roleInput);

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Save", (dialog, which) -> {
            // Update user with new details
            user.email = emailInput.getText().toString().trim();
            user.role = roleInput.getText().toString().trim();
            Executors.newSingleThreadExecutor().execute(() -> {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "user_database").build();
                UserDao userDao = db.userDao();
                userDao.update(user);

                // Update UI on the main thread
                ((AppCompatActivity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "User updated", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged(); // Refresh the list
                });
            });
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }


    // Delete user
    private void deleteUser(User user) {
        // Show a confirmation dialog before deletion
        new AlertDialog.Builder(context)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "user_database").build();
                        UserDao userDao = db.userDao();
                        userDao.delete(user);

                        // Update UI on the main thread
                        ((AppCompatActivity) context).runOnUiThread(() -> {
                            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
                            users.remove(user); // Remove from the list
                            notifyDataSetChanged(); // Refresh the list
                        });
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }

}
