package com.td.fr.unice.polytech.ghmandroid.NF;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Thres on 13/05/2018.
 */

@Entity(tableName = "userrole_table")
public class UserRole {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "libelle")
    private String mLibelle;

    public UserRole(String mLibelle) {
        this.mLibelle = mLibelle;
    }

    public String getLibelle() {
        return mLibelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
