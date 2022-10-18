package com.neil.geosphere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.Objects.Settings;
import com.neil.geosphere.Objects.User;

public class RegistrationActivity extends AppCompatActivity {

    //Declare of UI variables
    private TextView edtemail;
    private EditText edtname, edtsurname, edtusername, spAge;
    private Spinner Landmark;
    private RadioButton rbnMetric ,rbnImperial;
    private Button register;
    private FirebaseAuth fAuth;

    //Declare of picture properties
    private CheckBox chbxPermission;
    private DatabaseReference mDatabaseRef;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edtname = findViewById(R.id.txv_name_input);
        edtsurname = findViewById(R.id.txv_surname_input);
        edtemail = findViewById(R.id.txv_email_input);
        edtusername = findViewById(R.id.txv_username_input);
        chbxPermission = findViewById(R.id.chbxPermission);
        Landmark = findViewById(R.id.spn_profile_Landmark_type);
        register = findViewById(R.id.btn_register_save);
        rbnImperial = findViewById(R.id.rb_profile_imperial);
        rbnMetric = findViewById(R.id.rb_profile_Metric);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        Bundle bundle = getIntent().getExtras();
        String getEmail = bundle.getString("LoginEmail");
        edtemail.setText(getEmail);

         register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 try {
                     if (chbxPermission.isChecked() == true)
                     {
                         Register();
                         Toast.makeText(RegistrationActivity.this, "User information has been captured \n \n User Created", Toast.LENGTH_SHORT).show();
                         Intent switchToMainMenu = new Intent(RegistrationActivity.this, Main_Menu_Activity.class);
                         startActivity(switchToMainMenu);
                     }
                     else
                     {
                         Toast.makeText(RegistrationActivity.this, "Please provide permission in order to save personal information", Toast.LENGTH_LONG).show();
                     }
                 }
                 catch (Exception e)
                 {
                     Toast.makeText(RegistrationActivity.this, "Sign up was unsuccessful, please try again \nError:" + e.getMessage(), Toast.LENGTH_LONG).show();
                 }
             }
         });
    }

    public void Register()
    {
        String name , surname ,username , email ;

        name = edtname.getText().toString();
        surname = edtsurname.getText().toString();
        username = edtusername.getText().toString();
        email = edtemail.getText().toString();

        SaveUser(name,surname,username,email,fAuth.getUid());


        if (rbnMetric.isChecked() && Landmark.getSelectedItem() != null) {
            String uid = fAuth.getUid();
            String unitOfMeasurement = rbnMetric.getText().toString();
            String landmarkType = Landmark.getSelectedItem().toString();
            SaveUserSettings(uid, landmarkType, unitOfMeasurement);
        } else if (rbnImperial.isChecked() && Landmark.getSelectedItem() != null) {
            String uid = fAuth.getUid();
            String unitOfMeasurement = rbnImperial.getText().toString();
            String landmarkType = Landmark.getSelectedItem().toString();
            SaveUserSettings(uid, landmarkType, unitOfMeasurement);
        } else {
            Toast.makeText(RegistrationActivity.this, "Please make sure to provide settings.", Toast.LENGTH_SHORT).show();
        }


    }


    public void SaveUserSettings(String uid, String landmarkType, String unitOfMeasurement) {

        Settings newSettings = new Settings(uid, landmarkType, unitOfMeasurement);
        DocumentReference reference = fStore.collection("UserSettings").document(uid);
        reference.set(newSettings).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegistrationActivity.this, "Defaults Saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void SaveUser(String name, String surname, String username, String email ,String uid) {

        User u = new User(name,surname,username,email,uid);
        DocumentReference reference = fStore.collection("Users").document(uid);
        reference.set(u).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegistrationActivity.this, "User Saved", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegistrationActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }
}