package com.example.user_module;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.user_module.Dao.AccommodationDao;
import com.example.user_module.Dao.CommentaireDao;
import com.example.user_module.Dao.ExcursionDao;
import com.example.user_module.Dao.ReservationDao;
import com.example.user_module.Dao.ReservationnDao;
import com.example.user_module.Dao.RestaurantDao;
import com.example.user_module.Dao.UserDao;
import com.example.user_module.Dao.SiteDao;
import com.example.user_module.entity.Accommodation;
import com.example.user_module.entity.Commentaire;
import com.example.user_module.entity.Converters;
import com.example.user_module.entity.Excursion;
import com.example.user_module.entity.Reservation;
import com.example.user_module.entity.Reservationn;
import com.example.user_module.entity.Restaurant;
import com.example.user_module.entity.Site;
import com.example.user_module.entity.User;

@Database(entities = {User.class,Site.class, Restaurant.class, Reservation.class, Accommodation.class, Commentaire.class, Excursion.class, Reservationn.class }, version = 1) // Increment version if needed
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance; // Declare as a static variable
    public abstract UserDao userDao();
    public abstract SiteDao siteDao();
    public abstract RestaurantDao restaurantDao();
    public abstract AccommodationDao accommodationDao();
    public abstract ReservationDao reservationDao();
    public abstract CommentaireDao commentaireDao();
    public abstract ExcursionDao excursionDao();
    public abstract ReservationnDao  reservationnDao();


    // Synchronized method to get a single instance
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }






}
