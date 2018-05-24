package com.td.fr.unice.polytech.ghmandroid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.td.fr.unice.polytech.ghmandroid.NF.Incident;
import com.td.fr.unice.polytech.ghmandroid.NF.ViewModel.IncidentViewModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class visuIncidentBasic extends AppCompatActivity {


    private IncidentViewModel incidentViewModel;
    private TextView titre;
    private TextView userRole;
    private TextView description;
    private ImageView imgVisu;
    private ImageView urgence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visu_incident_basic);

        titre = this.findViewById(R.id.visuIncTitre);
        userRole = this.findViewById(R.id.visuIncUserRole);
        description = this.findViewById(R.id.visuIncDescription);

        imgVisu = this.findViewById(R.id.visuIncImg);
        urgence = this.findViewById(R.id.visuIncUrgence);


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

    }

    private void setFields(Incident incident){
        titre.setText(incident.getTitre());
        description.setText(incident.getDescription());
        String txtUR = "Affecté aux utilisateurs du rôle :"+incident.getUserRoleAffect();
        userRole.setText(txtUR);
        try {
            if(incident.getPhotoPath()!=null){
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(incident.getPhotoPath())));
                this.imgVisu.setImageBitmap(bitmap);
            }else{
                this.imgVisu.setImageDrawable(this.getDrawable(R.drawable.noimg));
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.imgVisu.setImageDrawable(this.getDrawable(R.drawable.noimg));
            Log.d("VISUINCIDENT", "setFields: loadImage failed");
        }

        switch (incident.getUrgence()){
            case 1 :
                this.urgence.setImageDrawable(this.getDrawable(R.drawable.levels_1));
                break;
            case 2 :
                this.urgence.setImageDrawable(this.getDrawable(R.drawable.levels_2));
                break;
            case 3 :
                this.urgence.setImageDrawable(this.getDrawable(R.drawable.levels_3));
                break;
            default:
                this.urgence.setImageDrawable(this.getDrawable(R.drawable.noimg));
                break;
        }

    }
}
