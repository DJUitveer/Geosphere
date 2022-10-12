package com.neil.geosphere;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    //Declaring components
    private Button save, help, aboutUs, feedback, mode;
    private RadioButton metric, imperial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Initializing Components
        save = findViewById(R.id.btnSave);
        help = findViewById(R.id.btnHelp);
        aboutUs = findViewById(R.id.btnAboutUs);
        feedback = findViewById(R.id.btnFeedback);
        mode = findViewById(R.id.btnDarkMode);

    }
    public void UnitsOfMeasurementSave(){
        //Todo: save the unit of measurement user chose (neil & creolin)
    }
    public void Help(){
        //Todo: Help method (neil & creolin)
    }
    public void AboutUs(){
        //Todo: Display About the devs information (neil & creolin)
    }
    public void Feedback(){
        //Todo: Allow user to provide feedback on app (neil & creolin)
        //code from solid puck app (reference)
    }
    public void DarkModeLightMode(){
        //Todo: Allow user to switch between light and dark mode (neil & creolin)
    }
}