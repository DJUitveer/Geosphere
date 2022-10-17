package com.neil.geosphere;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.neil.geosphere.Adapters.BookMarkedLocationsAdapter;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.Objects.FavouriteLocation;

import java.util.ArrayList;
import java.util.List;

public class BookmarkedLocationsActivity extends AppCompatActivity {
    private RecyclerView bookmarkRV;
    private ArrayList<FavouriteLocation> favLocationList;
    private BookMarkedLocationsAdapter bookMarkedLocationsAdapter;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_locations);
        //Todo: Fix Adapter calling
        bookmarkRV = findViewById(R.id.rv_Favourite_Bookmarks);
        fStore = FirebaseFirestore.getInstance();

        favLocationList = new ArrayList<>();

        bookmarkRV.setHasFixedSize(true);
        bookmarkRV.setLayoutManager(new LinearLayoutManager(this));

        bookMarkedLocationsAdapter = new BookMarkedLocationsAdapter(favLocationList, this);

        bookmarkRV.setAdapter(bookMarkedLocationsAdapter);

        fStore.collection("FavouriteLocations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        FavouriteLocation fl = d.toObject(FavouriteLocation.class);
                        if (fl.getUserID().equals(CurrentUser.UID)) {
                            favLocationList.add(fl);
                        }
                    }
                    bookMarkedLocationsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(BookmarkedLocationsActivity.this, "No favourites found!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookmarkedLocationsActivity.this, "Failed to get the favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}