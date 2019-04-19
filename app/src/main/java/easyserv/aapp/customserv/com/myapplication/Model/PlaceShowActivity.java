package easyserv.aapp.customserv.com.myapplication.Model;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.PagerAdapter.ViewPagerAdapter;
import easyserv.aapp.customserv.com.myapplication.R;
import easyserv.aapp.customserv.com.myapplication.ReviewsActivity;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import qiu.niorgai.StatusBarCompat;

public class PlaceShowActivity extends AppCompatActivity {

    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    CollectionReference placeRef = fs.collection("Places");

    String s;
    String s1;
    String s2;
    Intent i;

    String userName;
    String textR;


    FancyButton callButton;                 //кнопка для звонка
    FancyButton mapButton;                  //кнопка для карты
    FancyButton reviewsButton;               //кнопка для отзывов
    TextView descView;                      //описание заведения
    TextView nameView;                      //название заведения
    TextView timeView;                      //время работы заведения
    //CircleImageView imageViewToolbar;     //логотип заведения в тулбаре
    ViewPager viewPager;                    //скролинг фоток заведения
    CircleIndicator circleIndicator;        //индикатор для скролера фоток
    Toolbar toolbar;                        //наш тулбар
    FloatingActionButton fab;               //кнопка вызова щит меню

    FirebaseUser fUser;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_show);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Hookah");
        StatusBarCompat.translucentStatusBar(this);
        nameView = findViewById(R.id.title_place_show);
        //imageViewToolbar = findViewById(R.id.image_place_show);
        descView = findViewById(R.id.place_show_description);
        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.indicator);
        timeView = findViewById(R.id.place_show_time);
        callButton = findViewById(R.id.call_button);
        mapButton = findViewById(R.id.map_button);
        reviewsButton = findViewById(R.id.reviews_button);

        fab = findViewById(R.id.fab);

        i = getIntent();
        s = i.getStringExtra("idsn");

        String name = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String time = i.getStringExtra("time");
        final String tel = i.getStringExtra("tel");
        final String map = i.getStringExtra("map");

        String[] imageUrls = new String[]{
                i.getStringExtra("photo_1"),
                i.getStringExtra("photo_2"),
                i.getStringExtra("photo_3"),
                i.getStringExtra("photo_4"),
                i.getStringExtra("photo_5")
        };

        //Glide.with(PlaceShowActivity.this).load(ds.getString("image")).into(imageViewToolbar);

        ViewPagerAdapter adapter = new ViewPagerAdapter(PlaceShowActivity.this, imageUrls);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        nameView.setText(name);
        descView.setText(description);
        timeView.setText(time);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(tel));
                startActivity(intent);
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(map));
                startActivity(intent);
            }
        });

        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlaceShowActivity.this, ReviewsActivity.class);
                startActivity(i);
            }
        });

//        placeRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                for(DocumentSnapshot ds: queryDocumentSnapshots){
//                    if(ds.getId().equals(s)){
//
//                        String name = ds.getString("title");
//                        String description = ds.getString("description_2");
//                        String time = ds.getString("time");
//                        final String tel = ds.getString("tel");
//                        final String map = ds.getString("map");
//
//                        String[] imageUrls = new String[]{
//                                ds.getString("photo_1"),
//                                ds.getString("photo_2"),
//                                ds.getString("photo_3"),
//                                ds.getString("photo_4"),
//                                ds.getString("photo_5")
//                        };
//                        //Glide.with(PlaceShowActivity.this).load(ds.getString("image")).into(imageViewToolbar);
//                        ViewPagerAdapter adapter = new ViewPagerAdapter(PlaceShowActivity.this, imageUrls);
//                        viewPager.setAdapter(adapter);
//                        circleIndicator.setViewPager(viewPager);
//                        nameView.setText(name);
//                        descView.setText(description);
//                        timeView.setText(time);
//
//                        callButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent(Intent.ACTION_DIAL);
//                                intent.setData(Uri.parse(tel));
//                                startActivity(intent);
//                            }
//                        });
//
//                        mapButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent = new Intent();
//                                intent.setAction(Intent.ACTION_VIEW);
//                                intent.setData(Uri.parse(map));
//                                startActivity(intent);
//                            }
//                        });
//                        reviewsButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent i = new Intent(PlaceShowActivity.this, ReviewsActivity.class);
//                                startActivity(i);
//                            }
//                        });
//                    }
//                }
//            }
//        });
    }

}
