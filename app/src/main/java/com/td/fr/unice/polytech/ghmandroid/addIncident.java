package com.td.fr.unice.polytech.ghmandroid;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;

import java.util.Objects;


public class addIncident extends AppCompatActivity {

    private EditText title;
    private Spinner userRole;
    private Spinner urgence;
    private EditText description;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incident);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_activity_add_incident));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        this.title = findViewById(R.id.addIncInputTitle);
        this.description = findViewById(R.id.addIncDescriInput);

        Spinner spinner = (Spinner) findViewById(R.id.addIncUserRole);
        this.userRole = spinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_role_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinnerUrg = (Spinner) findViewById(R.id.addIncUrgenceSelector);
        this.urgence = spinnerUrg;
        ArrayAdapter<CharSequence> adapterUrg = ArrayAdapter.createFromResource(this,
                R.array.urgence_array, android.R.layout.simple_spinner_item);
        adapterUrg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUrg.setAdapter(adapterUrg);

        FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.cameraButton);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addIncSend);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(description.getText()) || TextUtils.isEmpty(userRole.getSelectedItem().toString()) || TextUtils.isEmpty(urgence.getSelectedItem().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String titleStr = title.getText().toString();
                    String descriStr = description.getText().toString();
                    String userRoleStr = userRole.getSelectedItem().toString();
                    String urgenceStr = urgence.getSelectedItem().toString();
                    replyIntent.putExtra("TITRE", titleStr);
                    replyIntent.putExtra("DESCRIPTION", descriStr);
                    replyIntent.putExtra("URGENCE", urgenceStr);
                    replyIntent.putExtra("USERROLE", userRoleStr);
                    setResult(RESULT_OK, replyIntent);
                    MainActivity.TwitterLoader twitterLoader = new MainActivity.TwitterLoader(getApplicationContext());
                    if (image != null) {
                        twitterLoader.postTweetWithImage(new Incident(titleStr, descriStr, urgence.getSelectedItemPosition(), 1, userRole.getSelectedItemPosition(), 1), image);
                    } else {
                        twitterLoader.postTweet(new Incident(titleStr, descriStr, urgence.getSelectedItemPosition(), 1, userRole.getSelectedItemPosition(), 1));
                    }
                }
                finish();
            }
        });

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.image = imageBitmap;
            imageView.setImageBitmap(imageBitmap);
        }
    }

}
