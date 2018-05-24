package com.td.fr.unice.polytech.ghmandroid.NF.RoomDB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final IncidentDao mIDao;
        private final UserDao mUDao;
        private final UserRoleDao mURDao;

        PopulateDbAsync(IncidentRoomDatabase db) {
            mIDao = db.incidentDao();
            mUDao = db.userDao();
            mURDao = db.userRoleDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mIDao.deleteAll();
            mUDao.deleteAll();
            mURDao.deleteAll();

            UserRole ur1 = new UserRole("Service Technique");
            mURDao.insert(ur1);

            User u1 = new User("Paul", "Martini");
            mUDao.insert(u1);

            Incident i1 = new Incident("Ampoule cassée","une ampoule est cassée en e130",2,1,1,1, null);
            mIDao.insert(i1);
            Incident i2 = new Incident("Chaise cassée","une chaise est cassée en e133",1,2,1,1, null);
            mIDao.insert(i2);
            return null;
        }
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
        new RoomDatabase.Callback(){
            @Override
            public void onOpen (@NonNull SupportSQLiteDatabase db){
                super.onOpen(db);
                new PopulateDbAsync(INSTANCE).execute();
            }
        };

    public static IncidentRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (IncidentRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IncidentRoomDatabase.class, "ghm_database")
                            .addCallback(sRoomDatabaseCallback)
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
