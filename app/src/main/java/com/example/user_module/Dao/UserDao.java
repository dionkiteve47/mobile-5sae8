package com.example.user_module.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.user_module.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("SELECT COUNT(*) FROM user_table WHERE email = :email")
    int checkUserExists(String email);

    // Add the login method
    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM user_table WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("UPDATE user_table SET confirmed = 1 WHERE email = :email")
    void confirmUser(String email);

    @Query("UPDATE user_table SET password = :newPassword WHERE email = :email")
    void updatePassword(String email, String newPassword);

    @Query("SELECT * FROM user_table WHERE resetToken = :resetToken LIMIT 1")
    User getUserByResetToken(String resetToken); // Retrieve user by reset token

    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();



    @Delete
    void delete(User user);

}
