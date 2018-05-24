package com.td.fr.unice.polytech.ghmandroid.Visualizers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetLinkClickListener;
import com.twitter.sdk.android.tweetui.TweetMediaClickListener;
import com.twitter.sdk.android.tweetui.TweetView;

public class UnclickableTweetView extends TweetView {
    public UnclickableTweetView(Context context, Tweet tweet) {
        super(context, tweet);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        setTweetLinkClickListener(new TweetLinkClickListener() {
            @Override
            public void onLinkClick(Tweet tweet, String url) {

            }
        });

        setTweetMediaClickListener(new TweetMediaClickListener() {
            @Override
            public void onMediaEntityClick(Tweet tweet, MediaEntity entity) {

            }
        });
    }

    public UnclickableTweetView(Context context, Tweet tweet, int styleResId) {
        super(context, tweet, styleResId);
    }

    public UnclickableTweetView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnclickableTweetView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
