package easyserv.aapp.customserv.com.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import easyserv.aapp.customserv.com.myapplication.Model.Hookah;
import easyserv.aapp.customserv.com.myapplication.Model.ReviewObj;
import easyserv.aapp.customserv.com.myapplication.Model.ReviewsAdapter;
import easyserv.aapp.customserv.com.myapplication.Model.User;
import mehdi.sakout.fancybuttons.FancyButton;
import qiu.niorgai.StatusBarCompat;

public class ReviewsActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userName;
    private String imageURL;
    private String time;
    private String name;
    private TextView title;

    private RecyclerView recyclerView;
    private EditText text;
    private FancyButton button;

    private ArrayList<ReviewObj> list;
    private Intent i;

    ReviewsAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("d, MMM, yyyy 'в' HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        text = findViewById(R.id.reviewsText);
        button = findViewById(R.id.revBut);
        title = findViewById(R.id.title_place_show_review);

        i = getIntent();
        name = i.getStringExtra("title");

        //работа с тулбаром
        Toolbar toolbar = findViewById(R.id.toolbar_review);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        //StatusBarCompat.translucentStatusBar(this);

        String full_name = "Отзывы о " + name;
        title.setText(full_name);


        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                userName = user.getUsername();
                imageURL = user.getImageURL();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().toString().equals(""))
                {
                    Toast.makeText(ReviewsActivity.this, "Пустой отзыв оставить нельзя!", Toast.LENGTH_SHORT).show();
                }
                else {
                    addReviews();
                    //showReviews();
                    readReviews();
                    Toast.makeText(ReviewsActivity.this, "Отзыв добавлен", Toast.LENGTH_SHORT).show();
                    text.setText("");
                }
            }
        });
        readReviews();
        //showReviews();
    }

    private void readReviews() {
        list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Hookah").child("Hookah1").child("Reviews");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    ReviewObj review = ds.getValue(ReviewObj.class);
                    list.add(review);
                }
                adapter = new ReviewsAdapter(ReviewsActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showReviews() {
        list = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Hookah").child("Hookah1").child("Reviews");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                list.clear();
                ReviewObj reviewObj = dataSnapshot.getValue(ReviewObj.class);
                Toast.makeText(ReviewsActivity.this, "Sender: " + reviewObj.getSender()
                        +"\n Text: " + reviewObj.getText(), Toast.LENGTH_SHORT).show();
                list.add(reviewObj);
                adapter = new ReviewsAdapter(ReviewsActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addReviews() {
        String currentTimeAndDate = sdf.format(new Date());
        ref = FirebaseDatabase.getInstance().getReference("Hookah").child("Hookah1").child("Reviews");
        HashMap<String, String> map = new HashMap<>();
        map.put("sender", userName);
        map.put("text", text.getText().toString());
        map.put("image", imageURL);
        map.put("dateTime", currentTimeAndDate);
        ref.push().setValue(map);
    }
}
