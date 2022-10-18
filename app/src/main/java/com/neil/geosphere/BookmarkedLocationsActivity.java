package com.neil.geosphere;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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