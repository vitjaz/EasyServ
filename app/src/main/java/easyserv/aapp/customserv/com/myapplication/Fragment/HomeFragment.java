package easyserv.aapp.customserv.com.myapplication.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jaeger.library.StatusBarUtil;
import com.kelin.translucentbar.library.TranslucentBarManager;

<<<<<<< HEAD
import java.util.ArrayList;

import easyserv.aapp.customserv.com.myapplication.Model.Hookah;
=======
import easyserv.aapp.customserv.com.myapplication.MainActivity;
>>>>>>> 13dfe5a5817c0eaaa9e65d9dfa0cec384887f490
import easyserv.aapp.customserv.com.myapplication.Model.Place;
import easyserv.aapp.customserv.com.myapplication.Model.PlaceAdapter;
import easyserv.aapp.customserv.com.myapplication.R;
import qiu.niorgai.StatusBarCompat;


public class HomeFragment extends Fragment {


    private ArrayList<Hookah> listHookah;
    private PlaceAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
<<<<<<< HEAD
        StatusBarCompat.translucentStatusBar(getActivity());
=======


        //StatusBarCompat.translucentStatusBar(getActivity());

//        TranslucentBarManager translucentBarManager = new TranslucentBarManager(this);
//        translucentBarManager.translucent(this, view, android.R.color.holo_orange_dark);

        //StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);

        recyclerView = view.findViewById(R.id.recycler_view_home);
        setUpRecyclerView();
>>>>>>> 13dfe5a5817c0eaaa9e65d9dfa0cec384887f490

        listHookah = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycler_view_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        readHookah();


        return view;

    }

    private void readHookah() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Hookah");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listHookah.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Hookah hookah = ds.getValue(Hookah.class);
                    listHookah.add(hookah);
                }
                adapter = new PlaceAdapter(getContext(), listHookah);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
