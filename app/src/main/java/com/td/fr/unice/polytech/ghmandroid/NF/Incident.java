package com.td.fr.unice.polytech.ghmandroid.NF;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Thres on 13/05/2018.
 */

@Entity(tableName = "incident_table")
public class Incident {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "titre")
    private String mTitre;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "urgence")
    private int mUrgence;

    @ColumnInfo(name = "avancement")
    private int mAvancement;

    /*@ColumnInfo(name = "date")
    private Date mDate;*/

    @ForeignKey(entity = UserRole.class, parentColumns = "id", childColumns = "idUserRoleAffect")
    @ColumnInfo(name = "idUserRoleAffect")
    private int mUserRoleAffect;

    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "idUserRoleAffect")
    @ColumnInfo(name = "idUserDeposant")
    private int mUserDeposant;

    public Incident(String mTitre, String mDescription, int mUrgence, int mAvancement, int mUserRoleAffect, int mUserDeposant) {
        this.mTitre = mTitre;
        this.mDescription = mDescription;
        this.mUrgence = mUrgence;
        this.mAvancement = mAvancement;
        this.mUserRoleAffect = mUserRoleAffect;
        this.mUserDeposant = mUserDeposant;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return mTitre;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getUrgence() {
        return mUrgence;
    }

    public int getUserRoleAffect() {
        return mUserRoleAffect;
    }

    public int getUserDeposant() {
        return mUserDeposant;
    }

    public int getAvancement() {
        return mAvancement;
    }

    public void setId(int id) {
        this.id = id;
    }
}