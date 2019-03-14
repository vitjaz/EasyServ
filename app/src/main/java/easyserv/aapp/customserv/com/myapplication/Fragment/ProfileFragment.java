package easyserv.aapp.customserv.com.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;
import easyserv.aapp.customserv.com.myapplication.Model.User;
import easyserv.aapp.customserv.com.myapplication.R;
import easyserv.aapp.customserv.com.myapplication.StartActivity;
import mehdi.sakout.fancybuttons.FancyButton;


public class ProfileFragment extends Fragment {

    private FancyButton logoutButton;
    private TextView username;
    private CircleImageView profileImage;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logoutButton = view.findViewById(R.id.logout_button);
        username = view.findViewById(R.id.text_view_profile_username);
        profileImage = view.findViewById(R.id.profile_image);

        auth = FirebaseAuth.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseUser != null)
        {
            Toast.makeText(getContext(), "Мы авторизованы", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getContext(), "Мы не авторизованы", Toast.LENGTH_SHORT).show();
        }

        userId = firebaseUser.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                username.setText(user.getUsername());
                Glide.with(getContext()).load(user.getImageURL()).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(getActivity(), StartActivity.class);
                startActivity(i);
            }
        });

        return view;
    }
}



