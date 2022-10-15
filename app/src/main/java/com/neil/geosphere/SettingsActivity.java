package com.neil.geosphere;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neil.geosphere.Objects.Settings;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //Declaring components
    private Button save, help, aboutUs;
    private Spinner typeOfLandMark;
    private RadioButton metric, imperial;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Initializing Components
        save = findViewById(R.id.btn_settings_save);
        help = findViewById(R.id.btn_settings_help);
        aboutUs = findViewById(R.id.btn_settings_about_us);
        typeOfLandMark = findViewById(R.id.spn_settings_Landmark_type);
        metric = findViewById(R.id.rb_settings_Metric);
        imperial = findViewById(R.id.rb_settings_imperial);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        GetUserSettings();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (metric.isChecked() && typeOfLandMark.getSelectedItem() != null) {
                    String uid = fAuth.getCurrentUser().getUid();
                    String unitOfMeasurement = metric.getText().toString();
                    String landmarkType = typeOfLandMark.getSelectedItem().toString();
                    SaveUserSettings(uid, landmarkType, unitOfMeasurement);
                } else if (imperial.isChecked() && typeOfLandMark.getSelectedItem() != null) {
                    String uid = fAuth.getUid().toString();
                    String unitOfMeasurement = imperial.getText().toString();
                    String landmarkType = typeOfLandMark.getSelectedItem().toString();
                    SaveUserSettings(uid, landmarkType, unitOfMeasurement);
                } else {
                    Toast.makeText(SettingsActivity.this, "Please make sure to provide settings.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void SaveUserSettings(String uid, String landmarkType, String unitOfMeasurement) {

        Settings newSettings = new Settings(uid, landmarkType, unitOfMeasurement);
        DocumentReference reference = fStore.collection("UserSettings").document(uid);
        reference.set(newSettings).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SettingsActivity.this, "Settings Saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SettingsActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void GetUserSettings() {
        String uid = fAuth.getCurrentUser().getUid();
        fStore.collection("UserSettings").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Settings getSettings = document.toObject(Settings.class);
                    String landmarkType = getSettings.getLandmarkType();
                    String unitType = getSettings.getUnitOfMeasurement();
                    metric = findViewById(R.id.rb_settings_Metric);
                    imperial = findViewById(R.id.rb_settings_imperial);
                    typeOfLandMark = findViewById(R.id.spn_settings_Landmark_type);

                    if (unitType.equals("Metric")) {
                        metric.setChecked(true);
                    } else {
                        imperial.setChecked(true);
                    }

                    for (int i = 0; i < typeOfLandMark.getCount(); i++) {
                        if (typeOfLandMark.getItemAtPosition(i).toString().equals(landmarkType)) {
                            typeOfLandMark.setSelection(i);
                        }
                    }
                }
            }
        });
    }

    public void Help() {
        //Todo: Help method (neil & creolin)
    }

    public void AboutUs() {
        //Todo: Display About the devs information (neil & creolin)
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner landmarkFilter = (Spinner) findViewById(R.id.spn_settings_Landmark_type);
        landmarkFilter.setOnItemSelectedListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}