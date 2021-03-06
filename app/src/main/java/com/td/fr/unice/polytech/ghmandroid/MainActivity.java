package com.td.fr.unice.polytech.ghmandroid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;
import android.widget.Toast;

import com.td.fr.unice.polytech.ghmandroid.NF.Adapter.IncidentListAdapter;
import com.td.fr.unice.polytech.ghmandroid.NF.ContextAndRoleHolder;
import com.td.fr.unice.polytech.ghmandroid.NF.Fragments.TwitterFragment;
import com.td.fr.unice.polytech.ghmandroid.NF.Helpers.MessageSender;
import com.td.fr.unice.polytech.ghmandroid.NF.HolderMatchingUser;
import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.User;
import com.td.fr.unice.polytech.ghmandroid.NF.ViewModel.IncidentViewModel;
import com.td.fr.unice.polytech.ghmandroid.NF.ViewModel.UserViewModel;
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
import com.twitter.sdk.android.core.models.Media;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.MediaService;
import com.twitter.sdk.android.core.services.StatusesService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final int REQUEST_READ_CONTACTS = 444;
    private static final int REQUEST_SEND_SMS = 445;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private IncidentViewModel incidentViewModel;
    private UserViewModel userViewModel;
    private TwitterLoader twitterLoader;
    private ContextAndRoleHolder holder;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int USER_ROLE_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.menu_principal));

        Log.i("WAIIIIIT" , getFilesDir().getAbsolutePath());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        twitterLoader = new TwitterLoader(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addIncident.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        userViewModel.getUsersByIdRole(USER_ROLE_ID).observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                // Update the cached copy of the incidents in the adapter.
                HolderMatchingUser.getInstance().setUsersOfRole(users);
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String userRolestr = data.getStringExtra("USERROLE");
            String titleStr = data.getStringExtra("TITRE");
            String urgenceStr = data.getStringExtra("URGENCE");
            String descriStr = data.getStringExtra("DESCRIPTION");
            int urole = Integer.valueOf(data.getStringExtra("USERROLE").split("-")[0]);
            int urgence = Integer.valueOf(data.getStringExtra("URGENCE").split("-")[0]);
            Incident incident = new Incident(titleStr,descriStr,urgence,1,urole,1, data.getStringExtra("IMAGE"));
            incidentViewModel.insert(incident);
            //twitterLoader.postTweet(incident);

            StringBuilder txtMessage = new StringBuilder();
            txtMessage.append("L'incident :");
            txtMessage.append(titleStr);
            txtMessage.append("\n et est affecté aux utilisateurs du rôle ");
            txtMessage.append(userRolestr);
            txtMessage.append("\n le degré d'importance est ");
            txtMessage.append(urgenceStr);
            txtMessage.append(" merci de bien vouloir consulter l'application pour plus d'information");

            this.holder = new ContextAndRoleHolder(getApplicationContext(),urole,getApplication(),txtMessage.toString());

            if(this.mayRequestContacts()){
                if(this.maySendSMS()){
                    userViewModel.getUsersByIdRole(urole).observe(this, new Observer<List<User>>() {
                        @Override
                        public void onChanged(@Nullable final List<User> users) {
                            // Update the cached copy of the incidents in the adapter.
                            HolderMatchingUser.getInstance().setUsersOfRole(users);
                            new MessageSender().execute(holder);
                        }
                    });
                }
            }
            System.out.println("I WAS HERE");
            mSectionsPagerAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    private boolean maySendSMS(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(SEND_SMS)) {
            requestPermissions(new String[]{SEND_SMS}, REQUEST_SEND_SMS);
        } else {
            requestPermissions(new String[]{SEND_SMS}, REQUEST_SEND_SMS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    if(this.maySendSMS()){
                        new MessageSender().execute(this.holder);
                    }
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Message non envoyé",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
            case REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new MessageSender().execute(this.holder);

                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Message non envoyé",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private IncidentViewModel incidentViewModel;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);
            RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
            final IncidentListAdapter adapter = new IncidentListAdapter(rootView.getContext(), this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

            incidentViewModel.getIncidentByAvancementAndAffecte(getArguments().getInt(ARG_SECTION_NUMBER),USER_ROLE_ID).observe(this, new Observer<List<Incident>>() {
                @Override
                public void onChanged(@Nullable final List<Incident> incidents) {
                    // Update the cached copy of the incidents in the adapter.
                    adapter.setIncidents(incidents);
                }
            });

            final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            return rootView;
        }

        public void updateIncident(int idInc, int avancement){
            incidentViewModel.update(idInc,avancement);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 3)
                return TwitterFragment.newInstance(position + 1);
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
    }

    public static class TwitterLoader {

        Context context;

        public TwitterLoader(Context context) {
            this.context = context;
            TwitterConfig config = new TwitterConfig.Builder(context)
                    .logger(new DefaultLogger(Log.DEBUG))
                    .twitterAuthConfig(new TwitterAuthConfig(context.getString(R.string.CONSUMER_KEY), context.getString(R.string.CONSUMER_SECRET)))
                    .debug(true)
                    .build();
            Twitter.initialize(config);
            TwitterSession twitterSession = new TwitterSession(new TwitterAuthToken("940556535897448448-NIqM0XfTfa43Pt3n7uytXEtbAgUUw3B",
                    "bFbWhSF7yNhS4TyDdYy1pRX5GonxJCtetrJtuduvgLUPb"), 940556535897448448L, context.getString(R.string.username));
            TwitterCore.getInstance().getSessionManager().setActiveSession(twitterSession);
        }

        public void postTweet(final Incident incident) {
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService statusesService = twitterApiClient.getStatusesService();
            Call<Tweet> touite = statusesService.update(incident.getTitre(), null, null,
                    null, null, null, null, null, null);
            touite.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    Long id = result.data.getId();
                    subTweet(id, "Urgence : " + incident.getUrgence());
                    Toast.makeText(context, "Posté !", Toast.LENGTH_SHORT).show();
                    Log.i("TWITTER", "Successfully tweeted");
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.i("TWITTER", "Failure");
                }
            });
        }

        public void postTweetWithImage(final Incident incident, Bitmap bmp) {
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            MediaService mediaService = twitterApiClient.getMediaService();
            File dir = context.getFilesDir();
            File image = new File(dir, "temp.png");
            OutputStream os;
            try {
                os = new FileOutputStream(image);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            } catch (Exception e) {
                Log.e("error", "error");
            }

            RequestBody request = RequestBody.create(MediaType.parse("image/*"), image);
            Call<Media> upload = mediaService.upload(request, null, null);
            upload.enqueue(new Callback<Media>() {
                @Override
                public void success(Result<Media> result) {
                    TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                    StatusesService statusesService = twitterApiClient.getStatusesService();
                    Call<Tweet> touite = statusesService.update(incident.getTitre(), null, null,
                            null, null, null, null, null, result.data.mediaIdString);
                    touite.enqueue(new Callback<Tweet>() {
                        @Override
                        public void success(Result<Tweet> result) {
                            Long id = result.data.getId();
                            subTweet(id, "Urgence : " + incident.getUrgence());
                            Toast.makeText(context, "Posté !", Toast.LENGTH_SHORT).show();
                            Log.i("TWITTER", "Successfully tweeted");
                        }

                        @Override
                        public void failure(TwitterException exception) {
                            Log.i("TWITTER", "Failure with image");
                        }
                    });
                }

                @Override
                public void failure(TwitterException exception) {

                }
            });
        }

        private void subTweet(Long id, String string) {
            if (!string.equals("")) {
                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                StatusesService statusesService = twitterApiClient.getStatusesService();
                Call<Tweet> touite = statusesService.update(string, id, null,
                        null, null, null, null, null, null);
                touite.enqueue(new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        Toast.makeText(context, "Detail Posté !", Toast.LENGTH_SHORT).show();
                        Log.i("TWITTER", "Successfully tweeted");
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.i("TWITTER", "Failure");
                    }
                });
            }
        }

        public List<Tweet> getTweets() {
            TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
            StatusesService statusesService = twitterApiClient.getStatusesService();
            final List<Tweet> tweets = new ArrayList<>();
            Call<List<Tweet>> call = statusesService.userTimeline(940556535897448448L, null, 50, null, null,
                    null, true, null, false);
            call.enqueue(new Callback<List<Tweet>>() {
                @Override
                public void success(Result<List<Tweet>> result) {
                    tweets.addAll(result.data);
                    Log.i("TWITTER", "Tweets received");
                }

                public void failure(TwitterException exception) {
                    //Do something on failure
                    Log.i("TWITTER", "FAILURE");
                }
            });
            return tweets;
        }
    }
}
