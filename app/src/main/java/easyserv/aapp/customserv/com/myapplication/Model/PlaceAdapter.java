package easyserv.aapp.customserv.com.myapplication.Model;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.R;

public class PlaceAdapter extends FirestoreRecyclerAdapter<Place, PlaceAdapter.PlaceHolder> implements Filterable {

    Context context;



    public PlaceAdapter(@NonNull FirestoreRecyclerOptions options) {
        super(options);
    }


    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_place, viewGroup, false);
        return new PlaceHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final PlaceHolder holder, int position, @NonNull Place model) {

        holder.textViewDescription.setText(model.getDescription());
        holder.textViewTitle.setText(model.getTitle());
        //holder.circleImageViewPlace.setImageResource(R.mipmap.ic_launcher);
        Glide.with(context).load(model.getImage()).into(holder.circleImageViewPlace);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PlaceShowActivity.class);
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
                i.putExtra("idsn", snapshot.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    class PlaceHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewDescription;
        CircleImageView circleImageViewPlace;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            textViewTitle = itemView.findViewById(R.id.text_view_title_place);
            textViewDescription = itemView.findViewById(R.id.text_view_description_place);
            circleImageViewPlace = itemView.findViewById(R.id.image_place);
        }
    }
}
