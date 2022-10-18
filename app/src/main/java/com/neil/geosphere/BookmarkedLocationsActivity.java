package com.neil.geosphere;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.neil.geosphere.Adapters.BookmarkAdapter;
import com.neil.geosphere.Objects.FavouriteLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookmarkedLocationsActivity extends AppCompatActivity {
    private BookmarkAdapter adapter;
    private RecyclerView bookmarkRV;
    private ArrayList<FavouriteLocation> favLocationList;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private Button menu;
    private ArrayList<FavouriteLocation> adapterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_locations);
        //Todo: Fix Adapter calling
        bookmarkRV = findViewById(R.id.rv_Favourite_Bookmarks);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String currentUserID = fAuth.getCurrentUser().getUid();

        fStore.collection("FavouriteLocations").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot favLoc: task.getResult()) {
                     if (favLoc.getString("userID").equals(currentUserID)){
                         adapterList.add( new FavouriteLocation(favLoc.getString("title"), favLoc.getString("latitude"),favLoc.getString("longitude") , favLoc.getString("userID") ));
                     }
                }
                BookmarkAdapter courseAdapter = new BookmarkAdapter(BookmarkedLocationsActivity.this, (ArrayList<FavouriteLocation>) adapterList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookmarkedLocationsActivity.this, LinearLayoutManager.VERTICAL, false);
                bookmarkRV.setLayoutManager(linearLayoutManager);
                bookmarkRV.setAdapter(courseAdapter);
            }
        });

        //Method to open and interact with menu
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(BookmarkedLocationsActivity.this, menu);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    //Switch statement to decide what users chooses
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bookmarks:
                                Toast.makeText(BookmarkedLocationsActivity.this, "Page Already active ", Toast.LENGTH_LONG).show();
//
//                                Intent ToBookmark = new Intent(BookmarkedLocationsActivity.this, BookmarkedLocationsActivity.class);
//                                startActivity(ToBookmark);
                                break;
                            case R.id.settings:
                                Intent toSettings = new Intent(BookmarkedLocationsActivity.this, SettingsActivity.class);
                                startActivity(toSettings);
                                break;
                            case R.id.my_profile:
                                Intent ToMyProfile=new Intent(BookmarkedLocationsActivity.this, ProfileActivity.class);
                                startActivity(ToMyProfile);
                                break;
                            case R.id.about_us:
                                Intent ToAboutUs = new Intent(BookmarkedLocationsActivity.this, AboutUsActivity.class);
                                startActivity(ToAboutUs);
                                break;
                            case R.id.help:
                                Intent ToHelp = new Intent(BookmarkedLocationsActivity.this, HelpActivity.class);
                                startActivity(ToHelp);
                                break;
                            case R.id.home:
                                Intent ToHome = new Intent(BookmarkedLocationsActivity.this, Main_Menu_Activity.class);
                                startActivity(ToHome);
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}