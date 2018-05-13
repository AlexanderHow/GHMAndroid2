package com.td.fr.unice.polytech.ghmandroid.NF.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.Repository.IncidentRepository;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */

public class IncidentViewModel extends AndroidViewModel {

    private IncidentRepository mRepository;

    public IncidentViewModel (Application application) {
        super(application);
        mRepository = new IncidentRepository(application);
    }

    public LiveData<List<Incident>> getIncidentByAvancementAndAffecte(int avanc, int idUserAffecte){
        return mRepository.getIncidentByAvancementAndUser(avanc,idUserAffecte);
    }

    public LiveData<Incident> getIncidentById(int iid ){
        return mRepository.getIncidentById(iid);
    }

    public void insert(Incident incident) { mRepository.insert(incident); }
}
