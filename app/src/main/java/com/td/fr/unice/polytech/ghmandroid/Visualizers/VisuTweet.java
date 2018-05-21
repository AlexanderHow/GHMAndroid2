package com.td.fr.unice.polytech.ghmandroid.Visualizers;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.td.fr.unice.polytech.ghmandroid.ImageLoader;
import com.td.fr.unice.polytech.ghmandroid.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;

import java.util.concurrent.CountDownLatch;

import retrofit2.Call;

public class VisuTweet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterConfig config = new TwitterConfig.Builder(getBaseContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getBaseContext().getString(R.string.CONSUMER_KEY), getBaseContext().getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        TwitterSession twitterSession = new TwitterSession(new TwitterAuthToken("940556535897448448-NIqM0XfTfa43Pt3n7uytXEtbAgUUw3B",
                "bFbWhSF7yNhS4TyDdYy1pRX5GonxJCtetrJtuduvgLUPb"), 940556535897448448L, "barnabeliqueux");
        TwitterCore.getInstance().getSessionManager().setActiveSession(twitterSession);

        setContentView(R.layout.activity_visu_tweet);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView textView = (TextView) findViewById(R.id.scrollText);

        setSupportActionBar(toolbar);
        setTitle("Détails");

        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        if (!getIntent().getStringExtra("image").equals("none")) {
            ImageLoader im = new ImageLoader(toolbarLayout, getResources());
            im.execute(getIntent().getStringExtra("image"));
        }


        try {
            Long id = getIntent().getLongExtra("id", 998203499514073088L);

            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService statusesService = twitterApiClient.getStatusesService();
            Call<Tweet> call = statusesService.show(id, null, null, null);
            call.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    textView.setText(result.data.text);
                    Log.d("TWEET", result.data.text);
                }

                public void failure(TwitterException exception) {
                    Toast.makeText(getBaseContext(), "Can't load tweet.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.d("Err", "null intent");
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
