package com.td.fr.unice.polytech.ghmandroid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.ViewModel.IncidentViewModel;

import java.util.List;

public class visuIncidentBasic extends AppCompatActivity {


    private IncidentViewModel incidentViewModel;
    private TextView titre;
    private TextView userRole;
    private TextView description;
    private ImageView imgVisu;
    private ImageView urgence;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_incident_basic);

        titre = this.findViewById(R.id.visuIncTitre);
        userRole = this.findViewById(R.id.visuIncUserRole);
        description = this.findViewById(R.id.visuIncDescription);

        imgVisu = this.findViewById(R.id.visuIncImg);
        urgence = this.findViewById(R.id.visuIncUrgence);

        back = this.findViewById(R.id.visuIncReturn);

        incidentViewModel = ViewModelProviders.of(this).get(IncidentViewModel.class);
        Bundle b = getIntent().getExtras();
        if(b != null){
            int iid = b.getInt("IDINC");
            incidentViewModel.getIncidentById(iid).observe(this, new Observer<Incident>() {
                @Override
                public void onChanged(@Nullable final Incident incident) {
                    // Update the cached copy of the incidents in the adapter.
                    setFields(incident);
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setFields(Incident incident){
        titre.setText(incident.getTitre());
        description.setText(incident.getDescription());
        String txtUR = "Affecté aux utilisateurs du rôle :"+incident.getUserRoleAffect();
        userRole.setText(txtUR);
        //TODO imgVisu en fct de l'uri
        //TODO switch pour img d'urgence

    }
}
