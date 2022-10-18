package com.neil.geosphere;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.rpc.Help;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.R;

public class HelpActivity extends AppCompatActivity {
    Button sendHelp ,menu;
FirebaseAuth mAuth;
    EditText emailBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mAuth = FirebaseAuth.getInstance();
        sendHelp = findViewById(R.id.Btn_help_help);
        emailBody = findViewById(R.id.txt_email_body);
        menu = findViewById(R.id.btn_menu_help);

        //Method to open and interact with menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(HelpActivity.this, menu);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    //Switch statement to decide what users chooses
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bookmarks:
                                Intent ToBookmark = new Intent(HelpActivity.this, BookmarkedLocationsActivity.class);
                                startActivity(ToBookmark);
                                break;
                            case R.id.settings:
                                Intent toSettings = new Intent(HelpActivity.this, SettingsActivity.class);
                                startActivity(toSettings);
                                break;
                            case R.id.my_profile:
                                Intent ToMyProfile=new Intent(HelpActivity.this, ProfileActivity.class);
                                startActivity(ToMyProfile);
                                break;
                            case R.id.about_us:
                                Intent ToAboutUs = new Intent(HelpActivity.this, AboutUsActivity.class);
                                startActivity(ToAboutUs);
                                break;
                            case R.id.help:
                                Toast.makeText(HelpActivity.this, "Page Already active ", Toast.LENGTH_LONG).show();
//                                Intent ToHelp = new Intent(HelpActivity.this, HelpActivity.class);
//                                startActivity(ToHelp);
                                break;
                            case R.id.home:
                                Intent ToHome = new Intent(HelpActivity.this, Main_Menu_Activity.class);
                                startActivity(ToHome);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        sendHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + mAuth.getCurrentUser() + "'s Question"+ "&body=" + emailBody.getText().toString() + "&to=" + "geospherevc@gmail.com");
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
                emailBody.setText("");
            }
        });
    }
}