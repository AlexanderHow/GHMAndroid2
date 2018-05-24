package com.td.fr.unice.polytech.ghmandroid.Visualizers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetView;

import retrofit2.Call;

public class OptimizedVisuTweet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_optimized_visu_tweet);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);

        TwitterConfig config = new TwitterConfig.Builder(getBaseContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getBaseContext().getString(R.string.CONSUMER_KEY), getBaseContext().getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);
        TwitterSession twitterSession = new TwitterSession(new TwitterAuthToken("940556535897448448-NIqM0XfTfa43Pt3n7uytXEtbAgUUw3B",
                "bFbWhSF7yNhS4TyDdYy1pRX5GonxJCtetrJtuduvgLUPb"), 940556535897448448L, "barnabeliqueux");
        TwitterCore.getInstance().getSessionManager().setActiveSession(twitterSession);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Détails");

        try {
            final Long id = getIntent().getLongExtra("id", 998203499514073088L);

            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService statusesService = twitterApiClient.getStatusesService();
            Call<Tweet> call = statusesService.show(id, null, null, null);
            call.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    linearLayout.addView(new UnclickableTweetView(getApplicationContext(), result.data));
                    TextView textView = new TextView(getBaseContext());
                    textView.setText("Retweets : " + result.data.retweetCount + "       Favoris : " + result.data.favoriteCount);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setTextColor(Color.BLACK);
                    linearLayout.addView(textView);
                    View v = new View(getBaseContext());
                    v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    v.setMinimumHeight(5);
                    linearLayout.addView(v);
                    Log.d("TWEET", result.data.text);
                }

                public void failure(TwitterException exception) {
                    Toast.makeText(getBaseContext(), "Can't load tweet.", Toast.LENGTH_SHORT).show();
                }
            });

            SearchService searchService = twitterApiClient.getSearchService();
            String query = getString(R.string.username);
            Call<Search> search = searchService.tweets(query, null, null, null, null,
                    null, null, null, null, null);
            search.enqueue(new Callback<Search>() {
                @Override
                public void success(Result<Search> result) {
                    for (Tweet t: result.data.tweets) {
                        if (t.inReplyToStatusId == id) {
                            linearLayout.addView(new UnclickableTweetView(getApplicationContext(), t));
                            Log.i("SEARCH", t.text);
                        }
                    }
                }

                @Override
                public void failure(TwitterException exception) {

                }
            });
        } catch (Exception e) {
            Log.d("Err", "null intent");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
