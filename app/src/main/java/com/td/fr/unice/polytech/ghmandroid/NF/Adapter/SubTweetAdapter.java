package com.td.fr.unice.polytech.ghmandroid.NF.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.R;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

public class SubTweetAdapter extends RecyclerView.Adapter<SubTweetAdapter.SubTweetHolder> {

    private final LayoutInflater mInflater;
    private List<Tweet> tweets;

    public SubTweetAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public SubTweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.sub_tweet, parent, false);
        return new SubTweetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SubTweetHolder holder, int position) {
        if (tweets != null) {
            Tweet current = tweets.get(position);
            holder.setTextView(current.text);
        } else {
            // Covers the case of data not being ready yet.
            holder.setTextView("Empty");
        }
    }

    @Override
    public int getItemCount() {
        if (tweets != null)
            return tweets.size();
        return 0;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public class SubTweetHolder extends RecyclerView.ViewHolder{

        private TextView textView;


        public SubTweetHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        public void setTextView(String text) {
            this.textView.setText(text);
        }
    }
}
