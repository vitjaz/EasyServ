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


        final Hookah hookah = list.get(i);
        placeHolder.textViewDescription.setText(hookah.getDescription());
        placeHolder.textViewTitle.setText(hookah.getTitle());
        //holder.circleImageViewPlace.setImageResource(R.mipmap.ic_launcher);
        Glide.with(context).load(hookah.getImage()).into(placeHolder.circleImageViewPlace);

        placeHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PlaceShowActivity.class);
                i.putExtra("description", hookah.getDescription());
                i.putExtra("description_2", hookah.getDescription_2());
                i.putExtra("image", hookah.getImage());
                i.putExtra("map", hookah.getMap());
                i.putExtra("photo_1", hookah.getPhoto_1());
                i.putExtra("photo_2", hookah.getPhoto_2());
                i.putExtra("photo_3", hookah.getPhoto_3());
                i.putExtra("photo_4", hookah.getPhoto_4());
                i.putExtra("photo_5", hookah.getPhoto_5());
                i.putExtra("tel", hookah.getTel());
                i.putExtra("time", hookah.getTime());
                i.putExtra("title", hookah.getTitle());
                i.putExtra("order", hookah.getOrder());
                i.putExtra("max_person", hookah.getMax_person());
                i.putExtra("nameInDB", hookah.getNameInDB());
                context.startActivity(i);
            }
        });
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
