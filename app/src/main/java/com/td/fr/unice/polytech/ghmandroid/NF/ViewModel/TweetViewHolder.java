package com.td.fr.unice.polytech.ghmandroid.NF.ViewModel;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.R;

/**
 * Created by Quentin on 14/05/2018.
 */

public class TweetViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView description;

    public TweetViewHolder(View itemView) {
        super(itemView);
        this.title = (TextView) itemView.findViewById(R.id.titreIncidentCell);
        this.description = (TextView) itemView.findViewById(R.id.descriptionIncidentCell);
    }

}
