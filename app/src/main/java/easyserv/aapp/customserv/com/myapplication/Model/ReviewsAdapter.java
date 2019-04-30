package easyserv.aapp.customserv.com.myapplication.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.R;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ReviewObj> list;


    public ReviewsAdapter(Context context, ArrayList<ReviewObj> rebObj) {
        this.context = context;
        this.list = rebObj;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.reviews_item, viewGroup, false);
        ReviewObj rev = list.get(i);
        //Toast.makeText(context, "Sender: " + rev.getSender(), Toast.LENGTH_SHORT).show();
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       final ReviewObj reviewObj = list.get(i);

       viewHolder.nick.setText(reviewObj.getSender());
       viewHolder.text.setText(reviewObj.getText());
       Glide.with(context).load(reviewObj.getImage()).into(viewHolder.imageReviews);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }   //было return 0

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView nick;
        TextView text;
        CircleImageView imageReviews;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nick = itemView.findViewById(R.id.NickNameText);
            text = itemView.findViewById(R.id.myReviews);
            imageReviews = itemView.findViewById(R.id.imageReviews);
        }
    }
}
