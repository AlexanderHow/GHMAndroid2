package com.td.fr.unice.polytech.ghmandroid.NF.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.td.fr.unice.polytech.ghmandroid.NF.Repository.UserRepository;
import com.td.fr.unice.polytech.ghmandroid.NF.User;

import java.util.List;

public class UserViewModel  extends AndroidViewModel {
    private UserRepository mRepository;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
    }

    public LiveData<List<User>> getUsersByIdRole(int idUserRole){
        return mRepository.getUsersByRole(idUserRole);
    }
}
