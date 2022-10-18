package com.neil.geosphere.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neil.geosphere.Objects.FavouriteLocation;
import com.neil.geosphere.R;

import java.util.ArrayList;

public class BookMarkedLocationsAdapter extends RecyclerView.Adapter<BookMarkedLocationsAdapter.ViewHolder> {
    private ArrayList<FavouriteLocation> favList;
    private Context context;

    public BookMarkedLocationsAdapter(ArrayList<FavouriteLocation> favList, Context context) {
        this.favList = favList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookMarkedLocationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bookmarked_locations_cardview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkedLocationsAdapter.ViewHolder holder, int position) {
        FavouriteLocation favouriteLocation = favList.get(position);
        holder.title.setText(favouriteLocation.getTitle());
        holder.title.setText(favouriteLocation.getPosition().toString());
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, coords;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            title = itemView.findViewById(R.id.txv_cv_Title);
//            coords = itemView.findViewById(R.id.txv_cv_Coords);
        }
    }
}
