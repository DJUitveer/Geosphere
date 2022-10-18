package com.neil.geosphere;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.neil.geosphere.Objects.FavouriteLocation;

import java.util.ArrayList;

public class BookmarkedLocationsActivity extends AppCompatActivity {
    private RecyclerView bookmarkRV;
    private ArrayList<FavouriteLocation> favLocationList;
    private FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_locations);
        //Todo: Fix Adapter calling
        bookmarkRV = findViewById(R.id.rv_Favourite_Bookmarks);
        fStore = FirebaseFirestore.getInstance();
        DocumentReference reference = fStore.collection("FavouriteLocations").document();

        Query query = FirebaseFirestore.getInstance()
                .collection("FavouriteLocations")
                .orderBy("userID")
                .limit(50);
        bookmarkRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<FavouriteLocation> options
                = new FirebaseRecyclerOptions.Builder<FavouriteLocation>()
                .setQuery(reference, FavouriteLocation.class)
                .build();
//        fStore.collection("FavouriteLocations").get();

    }
}