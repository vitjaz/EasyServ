package easyserv.aapp.customserv.com.myapplication.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import easyserv.aapp.customserv.com.myapplication.Model.Place;
import easyserv.aapp.customserv.com.myapplication.Model.PlaceAdapter;
import easyserv.aapp.customserv.com.myapplication.R;


public class SearchFragment extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference placesRef = db.collection("Places");

    private PlaceAdapter adapter;
    private RecyclerView recyclerView;

    private EditText searchtext;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_search);
        searchtext = view.findViewById(R.id.search_edit_text);

        //setUpRecyclerView();

        searchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                setUpRecyclerView();
//                searchPlaces(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return view;
    }

//    private void searchPlaces(String s) {
//        Query query = placesRef.orderBy("title").startAt(s).endAt(s + "\uf8ff");
//
//        FirestoreRecyclerOptions<Place> options = new FirestoreRecyclerOptions.Builder<Place>()
//                .setQuery(query, Place.class)
//                .build();
//
//        adapter = new PlaceAdapter(options);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//
//    }
//
//    private void setUpRecyclerView() {
//
//        Query query = placesRef.orderBy("num", Query.Direction.DESCENDING);
//
//
//        FirestoreRecyclerOptions<Place> options = new FirestoreRecyclerOptions.Builder<Place>()
//                .setQuery(query, Place.class)
//                .build();
//
//        adapter = new PlaceAdapter(options);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(adapter);
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
