package com.td.fr.unice.polytech.ghmandroid.NF.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.td.fr.unice.polytech.ghmandroid.Visualizers.VisuTweet;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

public class TwitterAdapter extends TweetTimelineRecyclerViewAdapter {

    public TwitterAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    TwitterAdapter(Context context, Timeline<Tweet> timeline, int styleResId, Callback<Tweet> cb) {
        super(context, timeline, styleResId, cb);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Tweet tweet = new TweetBuilder().build();
        final CompactTweetView compactTweetView = new CompactTweetView(context, tweet, styleResId);
        compactTweetView.setOnActionCallback(actionCallback);
        compactTweetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("TWEET", compactTweetView.getTweetId() + "");
                    Intent intent = new Intent(context, VisuTweet.class);
                    intent.putExtra("id", compactTweetView.getTweetId());
                    intent.putExtra("title", compactTweetView.getTweet().text);
                    try {
                        intent.putExtra("image", compactTweetView.getTweet().extendedEntities.media.get(0).mediaUrl);
                        Log.i("Intent", compactTweetView.getTweet().extendedEntities.media.get(0).mediaUrl);
                    } catch (Exception e) {intent.putExtra("image", "none");}
                    context.startActivity(intent);
                    Log.i("Tweet", "You touched a tweet");
                    return true;
                }
                return false;
            }
        });
        return new TweetViewHolder(compactTweetView);
    }

}
