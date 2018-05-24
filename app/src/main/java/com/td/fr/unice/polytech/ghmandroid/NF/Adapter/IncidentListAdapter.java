package com.td.fr.unice.polytech.ghmandroid.NF.Adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.MainActivity;
import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.ViewModel.IncidentViewModel;
import com.td.fr.unice.polytech.ghmandroid.R;
import com.td.fr.unice.polytech.ghmandroid.visuIncidentBasic;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */
public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.IncidentViewHolder> {

    class IncidentViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView description;
        private final RelativeLayout cellLayout;
        private final ImageButton upgrade;
        private final ImageButton downgrade;
        private final MainActivity.PlaceholderFragment placeholderFragment;



        private IncidentViewHolder(View itemView, MainActivity.PlaceholderFragment placeholderFragment) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.titreIncidentCell);
            description = (TextView)itemView.findViewById(R.id.descriptionIncidentCell);
            cellLayout = (RelativeLayout)itemView.findViewById(R.id.incidentCellLayout);
            upgrade = (ImageButton)itemView.findViewById(R.id.upgradeIncidentCell);
            downgrade = (ImageButton)itemView.findViewById(R.id.downgradeIncidentCell);
            this.placeholderFragment=placeholderFragment;
        }

        public void setFields(final Incident incident){
            String desthcription = incident.getDescription();
            String title = incident.getTitre();
            final int id = incident.getId();
            this.title.setText(title);
            this.description.setText(desthcription);
            if(id != -1 ){
                cellLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent visuIntent = new Intent(view.getContext(), visuIncidentBasic.class);
                        Bundle b = new Bundle();
                        b.putInt("IDINC", id);
                        visuIntent.putExtras(b);
                        view.getContext().startActivity(visuIntent);
                    }
                });

                upgrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(incident.upgradeAvancment()){
                            placeholderFragment.updateIncident(incident.getId(),incident.getAvancement());
                        }
                    }
                });

                downgrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(incident.downgradeAvancment()){
                            placeholderFragment.updateIncident(incident.getId(),incident.getAvancement());
                        }
                    }
                });

            }
        }
    }

    private final LayoutInflater mInflater;
    private List<Incident> mIncident;
    private MainActivity.PlaceholderFragment placeholderFragment;

    public IncidentListAdapter(Context context, MainActivity.PlaceholderFragment placeholderFragment) {
        mInflater = LayoutInflater.from(context);
        this.placeholderFragment=placeholderFragment;
    }

    @Override
    public IncidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.incident_cell, parent, false);
        return new IncidentViewHolder(itemView, this.placeholderFragment);
    }

    @Override
    public void onBindViewHolder(IncidentViewHolder holder, int position) {
        if (mIncident != null) {
            Incident current = mIncident.get(position);
            holder.setFields(current);
        } else {
            // Covers the case of data not being ready yet.
            Log.d("INCIDENTLISTADAPTER", "onBindViewHolder: no incident found");
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