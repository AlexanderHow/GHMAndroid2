package com.td.fr.unice.polytech.ghmandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.td.fr.unice.polytech.ghmandroid.NF.Adapter.IncidentListAdapter;
import com.td.fr.unice.polytech.ghmandroid.NF.Adapter.TweetAdapter;
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

import java.util.List;

import retrofit2.Call;

public class TwitterExperiments extends AppCompatActivity {

    private EditText content;
    private Button send;
    private RecyclerView listView;
    private Button refresh;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_experiments);

        content = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.send);
        listView = (RecyclerView) findViewById(R.id.tweetRecycle);
        refresh = (Button) findViewById(R.id.refresh);

        listView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(mLayoutManager);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.CONSUMER_KEY), getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        TwitterSession twitterSession = new TwitterSession(new TwitterAuthToken("940556535897448448-NIqM0XfTfa43Pt3n7uytXEtbAgUUw3B",
                "bFbWhSF7yNhS4TyDdYy1pRX5GonxJCtetrJtuduvgLUPb"), 940556535897448448L, "barnabeliqueux");
        TwitterCore.getInstance().getSessionManager().setActiveSession(twitterSession);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touite();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTouite();
            }
        });

    }

    public void getTouite() {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<List<Tweet>> call = statusesService.userTimeline(940556535897448448L, null, 50, null, null,
                null, true, null, false);
        call.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                for (Tweet t: result.data) {
                    Log.i("INFO : ", t.text);
                }
                TweetAdapter tweetAdapter = new TweetAdapter(result.data);
                listView.setAdapter(tweetAdapter);
                Log.i("TWITTER", "SUCCESS");
            }

            public void failure(TwitterException exception) {
                //Do something on failure
                Log.i("TWITTER", "FAILURE");
            }
        });
    }

    public void touite() {
        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = twitterApiClient.getStatusesService();
        Call<Tweet> touite = statusesService.update(content.getText().toString(), null, null,
                null, null, null, null, null, null);
        touite.enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                content.setText("");
                Log.i("TWITTER", "Successfully tweeted");
            }

            @Override
            public void failure(TwitterException exception) {
                Log.i("TWITTER", "Failure");
            }
        });
    }
}
