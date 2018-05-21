package com.td.fr.unice.polytech.ghmandroid.NF;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Thres on 13/05/2018.
 */

@Entity(tableName = "user_table", foreignKeys = @ForeignKey(entity = UserRole.class, parentColumns = "id", childColumns = "idRole", onDelete = CASCADE))
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nom")
    private String mNom;

    @ColumnInfo(name = "prenom")
    private String mPrenom;

    @ColumnInfo(name = "idRole")
    private int idRole;

    public User(String mNom, String mPrenom, int idRole) {
        this.mNom = mNom;
        this.mPrenom = mPrenom;
        this.idRole=idRole;
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

    public int getIdRole() {
        return idRole;
    }
}
