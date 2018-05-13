package com.td.fr.unice.polytech.ghmandroid.NF.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.R;

import java.util.List;

/**
 * Created by Thres on 13/05/2018.
 */
public class IncidentListAdapter extends RecyclerView.Adapter<IncidentListAdapter.IncidentViewHolder> {

    class IncidentViewHolder extends RecyclerView.ViewHolder {
        //TODO private final TextView wordItemView;

        private IncidentViewHolder(View itemView) {
            super(itemView);
            //TODO wordItemView = itemView.findViewById(R.id.textView);
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
            //TODO holder.wordItemView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            //TODO holder.wordItemView.setText("No Word");
        }
    }

    void setWords(List<Incident> incidents){
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