package com.neil.geosphere.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.neil.geosphere.Objects.FavouriteLocation;

public class BookmarkAdapter extends FirebaseRecyclerAdapter<FavouriteLocation, BookmarkAdapter.ViewHolder> {

    public BookmarkAdapter(@NonNull FirebaseRecyclerOptions<FavouriteLocation> options) {
        super(options);
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FavouriteLocation model) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, coords;
        private MaterialButton startNavigation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
