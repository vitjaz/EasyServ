package easyserv.aapp.customserv.com.myapplication.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jaeger.library.StatusBarUtil;
import com.kelin.translucentbar.library.TranslucentBarManager;

import easyserv.aapp.customserv.com.myapplication.MainActivity;
import easyserv.aapp.customserv.com.myapplication.Model.Place;
import easyserv.aapp.customserv.com.myapplication.Model.PlaceAdapter;
import easyserv.aapp.customserv.com.myapplication.R;
import qiu.niorgai.StatusBarCompat;


public class HomeFragment extends Fragment {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference placesRef = db.collection("Places");

    private PlaceAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //StatusBarCompat.translucentStatusBar(getActivity());

//        TranslucentBarManager translucentBarManager = new TranslucentBarManager(this);
//        translucentBarManager.translucent(this, view, android.R.color.holo_orange_dark);

        //StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);

        recyclerView = view.findViewById(R.id.recycler_view_home);
        setUpRecyclerView();

        return view;

    }

    private void setUpRecyclerView() {

        Query query = placesRef.orderBy("num", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<Place> options = new FirestoreRecyclerOptions.Builder<Place>()
                .setQuery(query, Place.class)
                .build();

        adapter = new PlaceAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
