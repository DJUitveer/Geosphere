package com.neil.geosphere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.R;

public class HelpActivity extends AppCompatActivity {
    Button sendHelp;

    EditText emailBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        sendHelp = findViewById(R.id.Btn_help_help);
        emailBody = findViewById(R.id.txt_email_body);


        sendHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + CurrentUser.UID + "'s Question"+ "&body=" + emailBody.getText().toString() + "&to=" + "geospherevc@gmail.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                emailBody.setText("");
            }
        });
    }
}