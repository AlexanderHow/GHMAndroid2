package com.td.fr.unice.polytech.ghmandroid.NF.RoomDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.td.fr.unice.polytech.ghmandroid.NF.DAO.IncidentDao;
import com.td.fr.unice.polytech.ghmandroid.NF.DAO.UserDao;
import com.td.fr.unice.polytech.ghmandroid.NF.DAO.UserRoleDao;
import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.User;
import com.td.fr.unice.polytech.ghmandroid.NF.UserRole;

/**
 * Created by Thres on 13/05/2018.
 */

@Database(entities = {Incident.class, User.class, UserRole.class}, version = 1)
public abstract class IncidentRoomDatabase extends RoomDatabase {

    private static IncidentRoomDatabase INSTANCE;

    public static IncidentRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IncidentRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IncidentRoomDatabase.class, "ghm_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract IncidentDao incidentDao();
    public abstract UserDao userDao();
    public abstract UserRoleDao userRoleDao();
}
