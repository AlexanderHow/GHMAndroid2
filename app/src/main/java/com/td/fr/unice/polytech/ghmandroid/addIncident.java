package com.td.fr.unice.polytech.ghmandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class addIncident extends AppCompatActivity {

    private EditText title;
    private Spinner userRole;
    private Spinner urgence;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_incident);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        spinnerUrg.setAdapter(adapter);

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
                    replyIntent.putExtra("TITLE", titleStr);
                    replyIntent.putExtra("DESCRIPTION", descriStr);
                    replyIntent.putExtra("URGENCE", urgenceStr);
                    replyIntent.putExtra("USERROLE", userRoleStr);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }

}
