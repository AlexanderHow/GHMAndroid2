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

    @ColumnInfo(name = "photo")
    private String mPhotoPath;

    public Incident(String mTitre, String mDescription, int mUrgence, int mAvancement, int mUserRoleAffect, int mUserDeposant, String mPhotoPath) {
        this.mTitre = mTitre;
        this.mDescription = mDescription;
        this.mUrgence = mUrgence;
        this.mAvancement = mAvancement;
        this.mUserRoleAffect = mUserRoleAffect;
        this.mUserDeposant = mUserDeposant;
        this.mPhotoPath = mPhotoPath;
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

    public boolean upgradeAvancment(){
        if(this.mAvancement<3){
            this.mAvancement++;
            return true;
        }
        return false;
    }

    public boolean downgradeAvancment(){
        if(this.mAvancement>1){
            this.mAvancement--;
            return true;
        }
        return false;
    }

    public String getmPhotoPath() {
        return mPhotoPath;
    }
}
