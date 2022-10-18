package com.neil.geosphere;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.neil.geosphere.Adapters.BookmarkAdapter;
import com.neil.geosphere.Objects.FavouriteLocation;

import java.util.ArrayList;

public class BookmarkedLocationsActivity extends AppCompatActivity {
    private BookmarkAdapter adapter;
    private RecyclerView bookmarkRV;
    private ArrayList<FavouriteLocation> favLocationList;
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_locations);
        //Todo: Fix Adapter calling
        bookmarkRV = findViewById(R.id.rv_Favourite_Bookmarks);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        String currentUserID = fAuth.getCurrentUser().getUid();
        Object[] favouriteList = fStore.collection("FavouriteLocations").get().getResult().toObjects(FavouriteLocation.class).toArray();
        for (FavouriteLocation item :  favLocationList) {

        }

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