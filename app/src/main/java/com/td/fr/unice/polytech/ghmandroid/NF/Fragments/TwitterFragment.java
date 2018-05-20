package com.td.fr.unice.polytech.ghmandroid.NF.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.td.fr.unice.polytech.ghmandroid.R;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public TwitterFragment() {}

    public static TwitterFragment newInstance(int sectionNumber) {
        TwitterFragment fragment = new TwitterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TwitterConfig config = new TwitterConfig.Builder(getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getContext().getString(R.string.CONSUMER_KEY), getContext().getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        TwitterSession twitterSession = new TwitterSession(new TwitterAuthToken("940556535897448448-NIqM0XfTfa43Pt3n7uytXEtbAgUUw3B",
                "bFbWhSF7yNhS4TyDdYy1pRX5GonxJCtetrJtuduvgLUPb"), 940556535897448448L, "barnabeliqueux");
        TwitterCore.getInstance().getSessionManager().setActiveSession(twitterSession);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("barnabeliqueux")
                .includeReplies(false)
                .includeRetweets(false)
                .maxItemsPerRequest(50)
                .build();

        final TweetTimelineRecyclerViewAdapter adapter =
                new TweetTimelineRecyclerViewAdapter.Builder(this.getContext())
                        .setTimeline(userTimeline)
                        .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                        .build();

        recyclerView.setAdapter(adapter);


        return rootView;
    }

}
