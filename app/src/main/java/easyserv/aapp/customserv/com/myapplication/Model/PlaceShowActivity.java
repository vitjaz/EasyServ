package easyserv.aapp.customserv.com.myapplication.Model;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import easyserv.aapp.customserv.com.myapplication.BookingActivity;
import easyserv.aapp.customserv.com.myapplication.PagerAdapter.ViewPagerAdapter;
import easyserv.aapp.customserv.com.myapplication.R;
import easyserv.aapp.customserv.com.myapplication.ReviewsActivity;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import qiu.niorgai.StatusBarCompat;

public class PlaceShowActivity extends AppCompatActivity {

    private FirebaseFirestore fs = FirebaseFirestore.getInstance();
    private CollectionReference placeRef = fs.collection("Places");

    private String s;
    private String s1;
    private String s2;
    private Intent i;

    String userName;
    String textR;


    BottomSheetBehavior bottomSheetBehavior;    //поведение нижнего
    FancyButton callButton;                 //кнопка для звонка
    FancyButton mapButton;                  //кнопка для карты
    private FancyButton reviewsButton;              //кнопка для отзывов
    private FancyButton reservButton;               //кнопка для брони
    private TextView descView;                      //описание заведения
    private TextView nameView;                      //название заведения
    private TextView timeView;                      //время работы заведения
    //CircleImageView imageViewToolbar;     //логотип заведения в тулбаре
    private ViewPager viewPager;                    //скролинг фоток заведения
    private CircleIndicator circleIndicator;        //индикатор для скролера фоток
    private Toolbar toolbar;                        //наш тулбар
    String nameInDB;
    private FirebaseUser fUser;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_show);


        View includedView = findViewById(R.id.includedLayout);
        View bookingButton = includedView.findViewById(R.id.booking_buttons);


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
        reservButton = findViewById(R.id.reserv_button);
        //bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_reviews));



        i = getIntent();
        s = i.getStringExtra("idsn");

        final String name = i.getStringExtra("title");
        String description = i.getStringExtra("description");
        String time = i.getStringExtra("time");
        nameInDB = i.getStringExtra("nameInDB");
        final String tel = i.getStringExtra("tel");
        final String map = i.getStringExtra("map");

        final String order = i.getStringExtra("order");
        final String max_person = i.getStringExtra("max_person");

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
                i.putExtra("title", name);
                i.putExtra("nameInDB", nameInDB);
                startActivity(i);
            }
        });

        //показать наше нижнее меню бронирования
        reservButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //данный код в разработке
                //                new CustomBottomSheetDialogFragment().show(getSupportFragmentManager(), "Dialog");

                Intent i = new Intent(PlaceShowActivity.this, BookingActivity.class);
                i.putExtra("name", name);
                i.putExtra("order", order);
                i.putExtra("max_person", max_person);
                startActivity(i);
            }
        });

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlaceShowActivity.this, BookingActivity.class);
                startActivity(i);
            }
        });


        //слушатель на bottom sheet
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(View bottomSheet, int newState) {
//
//                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
//                    //      bottomSheetHeading.setText(getString(R.string.text_collapse_me));
//                } else {
//                    //     bottomSheetHeading.setText(getString(R.string.text_expand_me));
//                }
//
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_COLLAPSED:
//                        Log.e("Bottom Sheet Behaviour", "STATE_COLLAPSED");
//                        break;
//                    case BottomSheetBehavior.STATE_DRAGGING:
//                        Log.e("Bottom Sheet Behaviour", "STATE_DRAGGING");
//                        break;
//                    case BottomSheetBehavior.STATE_EXPANDED:
//                        Log.e("Bottom Sheet Behaviour", "STATE_EXPANDED");
//                        break;
//                    case BottomSheetBehavior.STATE_HIDDEN:
//                        Log.e("Bottom Sheet Behaviour", "STATE_HIDDEN");
//                        break;
//                    case BottomSheetBehavior.STATE_SETTLING:
//                        Log.e("Bottom Sheet Behaviour", "STATE_SETTLING");
//                        break;
//                }
//            }
//
//
//            @Override
//            public void onSlide(View bottomSheet, float slideOffset) {
//
//            }
//        });
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

