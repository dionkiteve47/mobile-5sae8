package com.example.user_module;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.user_module.Dao.AccommodationDao;
import com.example.user_module.Dao.ReservationDao;
import com.example.user_module.Dao.RestaurantDao;
import com.example.user_module.Dao.UserDao;
import com.example.user_module.Dao.SiteDao;
import com.example.user_module.entity.Accommodation;
import com.example.user_module.entity.Reservation;
import com.example.user_module.entity.Restaurant;
import com.example.user_module.entity.Site;
import com.example.user_module.entity.User;

@Database(entities = {User.class,Site.class, Restaurant.class, Reservation.class, Accommodation.class }, version = 3) // Increment version if needed
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance; // Declare as a static variable
    public abstract UserDao userDao();
    public abstract SiteDao siteDao();
    public abstract RestaurantDao restaurantDao();

    // Synchronized method to get a single instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "MobileApp")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }



    public abstract AccommodationDao accommodationDao();
    public abstract ReservationDao reservationDao();


}
