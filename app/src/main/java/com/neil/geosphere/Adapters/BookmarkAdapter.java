package com.neil.geosphere.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.mapbox.geojson.Point;
import com.neil.geosphere.Objects.CurrentUser;
import com.neil.geosphere.Objects.FavouriteLocation;
import com.neil.geosphere.R;
import com.neil.geosphere.Util.Navigation;

public class BookmarkAdapter  {


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
//        private Navigation nav = new Navigation();
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
