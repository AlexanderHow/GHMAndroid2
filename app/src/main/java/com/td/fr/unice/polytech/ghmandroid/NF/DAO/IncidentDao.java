package com.td.fr.unice.polytech.ghmandroid.NF.DAO;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */

@Dao
public interface IncidentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Incident incident);

    @Query("SELECT * FROM incident_table WHERE id = :idIncident")
    LiveData<Incident> getIncidentById(int idIncident);

    @Query("SELECT * FROM incident_table WHERE avancement = :avancement AND idUserRoleAffect = :idAffecte")
    LiveData<List<Incident>> getIncidentByAvancementAndUser(int avancement, int idAffecte);

    @Query("UPDATE incident_table SET avancement = :newAvancement WHERE id = :iid")
    int updateAvancement(int iid, int newAvancement);
}
