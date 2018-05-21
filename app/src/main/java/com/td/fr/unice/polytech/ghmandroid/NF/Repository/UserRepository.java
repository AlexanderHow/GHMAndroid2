package com.td.fr.unice.polytech.ghmandroid.NF.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.td.fr.unice.polytech.ghmandroid.NF.DAO.UserDao;
import com.td.fr.unice.polytech.ghmandroid.NF.RoomDB.IncidentRoomDatabase;
import com.td.fr.unice.polytech.ghmandroid.NF.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {

    private UserDao mUserDao;

    public UserRepository(Application app){
        IncidentRoomDatabase db = IncidentRoomDatabase.getDatabase(app);
        this.mUserDao=db.userDao();
    }

    public LiveData<List<User>> getUsersByRole(int idRole){
        try {
            return new selectByRoleAsyncTask(this.mUserDao).execute(idRole).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.out.println("Error getting users by their role");
            return null;
        }
    }

    //ASYNC TASKS
    private static class selectByRoleAsyncTask extends AsyncTask<Integer, Void, LiveData<List<User>>> {

        private UserDao mAsyncTaskDao;

        selectByRoleAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<User>> doInBackground(final Integer... params) {
            return mAsyncTaskDao.getUsersByIdRole(params[0]);
        }
    }
}
