package easyserv.aapp.customserv.com.myapplication.Model;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.PagerAdapter.ViewPagerAdapter;
import easyserv.aapp.customserv.com.myapplication.R;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import qiu.niorgai.StatusBarCompat;

public class PlaceShowActivity extends AppCompatActivity {

    /*FirebaseFirestore fs = FirebaseFirestore.getInstance();
    CollectionReference placeRef = fs.collection("Places");

    String s;
    String s1;
    String s2;
    Intent i;

    FancyButton callButton;                 //кнопка для звонка
    FancyButton mapButton;                  //кнопка для карты
    TextView descView;                      //описание заведения
    TextView nameView;                      //название заведения
    TextView timeView;                      //время работы заведения
    //CircleImageView imageViewToolbar;       //логотип заведения в тулбаре
    ViewPager viewPager;                    //скролинг фоток заведения
    CircleIndicator circleIndicator;        //индикатор для скролера фоток
    Toolbar toolbar;                        //наш тулбар


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_show);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StatusBarCompat.translucentStatusBar(this);
        nameView = findViewById(R.id.title_place_show);
        //imageViewToolbar = findViewById(R.id.image_place_show);
        descView = findViewById(R.id.place_show_description);
        viewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.indicator);
        timeView = findViewById(R.id.place_show_time);
        callButton = findViewById(R.id.call_button);
        mapButton = findViewById(R.id.map_button);

        i = getIntent();
        s = i.getStringExtra("idsn");


                        String name =
                        String description =
                        String time =
                        final String tel = ds.getString("tel");
                        final String map = ds.getString("map");

                        String[] imageUrls = new String[]{

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


    }*/


}
