package com.td.fr.unice.polytech.ghmandroid.NF.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.R;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */
public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.IncidentViewHolder> {

    class IncidentViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;

        private IncidentViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.titreIncidentCell);
            description = (TextView)itemView.findViewById(R.id.descriptionIncidentCell);
        }

        public void setFields(String title, String desthcription){
            this.title.setText(title);
            this.description.setText(desthcription);
        }
    }

    private final LayoutInflater mInflater;
    private List<Incident> mIncident;

    public IncidentListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.incident_cell, parent, false);
        return new IncidentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IncidentViewHolder holder, int position) {
        if (mIncident != null) {
            Incident current = mIncident.get(position);
            holder.setFields(current.getTitre(),current.getDescription());
        } else {
            // Covers the case of data not being ready yet.
            holder.setFields("nothing", "nothing");
        }
    }

    public void setIncidents(List<Incident> incidents){
        mIncident = incidents;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mIncident != null)
            return mIncident.size();
        else return 0;
    }
}