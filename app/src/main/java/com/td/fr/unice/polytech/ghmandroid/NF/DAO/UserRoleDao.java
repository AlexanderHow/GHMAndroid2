package com.td.fr.unice.polytech.ghmandroid.NF.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.td.fr.unice.polytech.ghmandroid.NF.UserRole;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */

@Dao
public interface UserRoleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserRole userR);

    @Query("SELECT * FROM userrole_table")
    List<UserRole> getAllUser();

    @Query("SELECT * FROM userrole_table WHERE id = :urid")
    UserRole getUserById(int urid);
}
