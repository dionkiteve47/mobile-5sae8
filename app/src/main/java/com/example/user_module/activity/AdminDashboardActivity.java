package com.example.user_module.activity;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.user_module.Adapter.UserAdapter;
import com.example.user_module.AppDatabase;
import com.example.user_module.Dao.UserDao;
import com.example.user_module.R;
import com.example.user_module.entity.User;

import java.util.List;
import java.util.concurrent.Executors;

public class AdminDashboardActivity extends AppCompatActivity {

    private ListView userListView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        userListView = findViewById(R.id.user_list_view);

        // Initialize Room database and UserDao
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "user_database").build();
        UserDao userDao = db.userDao();

        // Fetch users in a background thread
        Executors.newSingleThreadExecutor().execute(() -> {
            List<User> users = userDao.getAllUsers();  // Make sure UserDao has a method to retrieve all users

            runOnUiThread(() -> {
                // Set up the adapter and attach it to the ListView
                userAdapter = new UserAdapter(AdminDashboardActivity.this, users);
                userListView.setAdapter(userAdapter);
            });
        });
    }
}
