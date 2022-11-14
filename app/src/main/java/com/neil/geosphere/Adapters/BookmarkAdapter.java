package com.neil.geosphere.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.geojson.Point;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.Objects.FavouriteLocation;
import com.neil.geosphere.R;

import java.util.ArrayList;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<FavouriteLocation> favouriteLocationArrayList;

    public BookmarkAdapter(Context context, ArrayList<FavouriteLocation> favouriteLocationArrayList) {
        this.context = context;
        this.favouriteLocationArrayList = favouriteLocationArrayList;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_locations_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        FavouriteLocation model = favouriteLocationArrayList.get(position);
        holder.title.setText(model.getTitle());
        holder.coords.setText("Lat: " + model.getLatitude() + "\nLong: " + model.getLatitude());
        //holder.destination = Point.fromLngLat(Double.parseDouble(model.getLatitude()),Double.parseDouble(model.getLongitude()));
        //holder.start = CurrentUser.deviceLocationForRoute;

    }

    @Override
    public int getItemCount() {
        return favouriteLocationArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, coords;
        private Button startTracking;
        private Point destination, start;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txv_cv_Title);
            coords = itemView.findViewById(R.id.txv_cv_coords);
            //startTracking = itemView.findViewById(R.id.btn_start_trackin);

//            startTracking.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Routing_Nav n = new Routing_Nav();
//                    n.getRoute(start,destination,view.getContext());
//                }
//            });

        }
    }
}
//
//    Context context;
//
//    public BookmarkAdapter(@NonNull FirestoreRecyclerOptions<FavouriteLocation> options) {
//        super(options);
//    }
//
//    @NonNull
//    @Override
//    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarked_locations_cardview, parent, false);
//        return new BookmarkAdapter.ViewHolder(v);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull FavouriteLocation model) {
//        holder.title.setText(model.getTitle());
//        holder.coords.setText("Lat: " + model.getLatitude()+",Long: " + model.getLongitude());
//        holder.cardPosition = new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongitude()));
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView title, coords;
//        private Button startNavigation;
//        private LatLng cardPosition;
//        private Routing_Nav nav = new Routing_Nav();
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            title = itemView.findViewById(R.id.txv_cv_Title);
//            coords = itemView.findViewById(R.id.txv_cv_coords);
//            startNavigation = itemView.findViewById(R.id.btn_start_tracking);
//            startNavigation.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Point destinationPoint = Point.fromLngLat(cardPosition.latitude, cardPosition.longitude);
//                    nav.getRoute(CurrentUser.deviceLocationForRoute, destinationPoint, itemView.getContext());
//                }
//            });
//        }
//    }
