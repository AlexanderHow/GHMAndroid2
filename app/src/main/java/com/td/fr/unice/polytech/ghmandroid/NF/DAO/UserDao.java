package com.td.fr.unice.polytech.ghmandroid.NF.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.td.fr.unice.polytech.ghmandroid.NF.User;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM user_table")
    List<User> getAllUser();

    @Query("SELECT * FROM user_table WHERE id = :uid")
    User getUserById(int uid);
}
