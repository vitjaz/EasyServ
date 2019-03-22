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


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.R;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> implements Filterable {

    Context context;
    ArrayList<Hookah> list;

    public PlaceAdapter(Context context, ArrayList<Hookah> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_place, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder placeHolder, int i) {


        Hookah hookah = list.get(i);
        placeHolder.textViewDescription.setText(hookah.getDescription());
        placeHolder.textViewTitle.setText(hookah.getTitle());
        //holder.circleImageViewPlace.setImageResource(R.mipmap.ic_launcher);
        Glide.with(context).load(hookah.getPhoto_1()).into(placeHolder.circleImageViewPlace);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public Filter getFilter() {
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTitle;
        TextView textViewDescription;
        CircleImageView circleImageViewPlace;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            textViewTitle = itemView.findViewById(R.id.text_view_title_place);
            textViewDescription = itemView.findViewById(R.id.text_view_description_place);
            circleImageViewPlace = itemView.findViewById(R.id.image_place);
        }
    }
}
