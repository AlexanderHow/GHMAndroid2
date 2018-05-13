package com.td.fr.unice.polytech.ghmandroid.NF.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.td.fr.unice.polytech.ghmandroid.NF.DAO.IncidentDao;
import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.RoomDB.IncidentRoomDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Thres on 13/05/2018.
 */

public class IncidentRepository {
    private IncidentDao mIncidentDao;

    public IncidentRepository(Application app){
        IncidentRoomDatabase db = IncidentRoomDatabase.getDatabase(app);
        this.mIncidentDao=db.incidentDao();
    }

    public LiveData<List<Incident>> getIncidentByAvancementAndUser(int avancement, int idAffecte){
        try {
            return new selectByAvancAndUserAsyncTask(this.mIncidentDao).execute(avancement, idAffecte).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.out.println("Error getting incident by avancement and user affecte");
            return null;
        }
    }

    public LiveData<Incident> getIncidentById(int id){
        try {
            return new selectByIdAsyncTask(this.mIncidentDao).execute(id).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.out.println("Error getting incident by id");
            return null;
        }
    }

    public void insert(Incident incident){
        new insertAsyncTask(this.mIncidentDao).execute(incident);
    }




    //ASYNC TASKS
    private static class selectByAvancAndUserAsyncTask extends AsyncTask<Integer, Void, LiveData<List<Incident>>> {

        private IncidentDao mAsyncTaskDao;

        selectByAvancAndUserAsyncTask(IncidentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<List<Incident>> doInBackground(final Integer... params) {
            return mAsyncTaskDao.getIncidentByAvancementAndUser(params[0],params[1]);
        }
    }

    private static class selectByIdAsyncTask extends AsyncTask<Integer, Void, LiveData<Incident>> {

        private IncidentDao mAsyncTaskDao;

        selectByIdAsyncTask(IncidentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected LiveData<Incident> doInBackground(final Integer... params) {
            return mAsyncTaskDao.getIncidentById(params[0]);
        }
    }

    private static class insertAsyncTask extends AsyncTask<Incident, Void, Void> {

        private IncidentDao mAsyncTaskDao;

        insertAsyncTask(IncidentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Incident... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
