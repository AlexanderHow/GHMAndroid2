package com.td.fr.unice.polytech.ghmandroid.NF;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Thres on 13/05/2018.
 */

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nom")
    private String mNom;

    @ColumnInfo(name = "prenom")
    private String mPrenom;

    public User(String mNom, String mPrenom) {
        this.mNom = mNom;
        this.mPrenom = mPrenom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return mNom;
    }

    public String getPrenom() {
        return mPrenom;
    }

    public void setId(int id) {
        this.id = id;
    }
}
