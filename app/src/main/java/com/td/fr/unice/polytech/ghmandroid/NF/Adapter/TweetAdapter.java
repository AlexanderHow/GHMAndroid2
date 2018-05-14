package com.td.fr.unice.polytech.ghmandroid.NF.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.ViewModel.TweetViewHolder;
import com.td.fr.unice.polytech.ghmandroid.R;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 14/05/2018.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetViewHolder> {

    List<Tweet> list = new ArrayList<>();

    public TweetAdapter (List<Tweet> list) {
        this.list = list;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.incident_cell, parent, false);
        TweetViewHolder newsViewHolder = new TweetViewHolder(v);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        holder.title.setText(list.get(position).text);
        holder.description.setText(list.get(position).text);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
