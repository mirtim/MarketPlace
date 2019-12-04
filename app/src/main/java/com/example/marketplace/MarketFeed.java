package com.example.marketplace;


import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class MarketFeed extends Fragment {

    Button btnCreatePost;
    ListView feedListView;
    SharedPreferences sharedPref;

    private DatabaseReference mDatabase;


    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PostListAdapter postListAdapter;

    public MarketFeed() {
        // Required empty public constructor
    }

    public interface MarketFeedListener {
        public void createPost();
    }

    MarketFeedListener MFL;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MFL = (MarketFeedListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.feed_layout, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.feed_recycler_view);

        btnCreatePost = view.findViewById(R.id.btnCreatePost);
        // init the adapter
        postListAdapter = new PostListAdapter(postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(postListAdapter); // set the adapter to the recycler view


        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MFL.createPost();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference(); // get the ref of db

        mDatabase.child("posts").addValueEventListener( new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                Log.i("dataInside",dataSnapshot.getValue().toString());

                postList.clear();
                if(dataSnapshot.hasChildren()){
                    Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();
                    while (iter.hasNext()){
                        DataSnapshot snap = iter.next();
                        String postID = snap.getKey();
                        long askingPrice = (long) snap.child("askingPrice").getValue();
                        String category = (String) snap.child("category").getValue();
                        String itemDescription = (String) snap.child("itemDescription").getValue();
                        String itemName = (String) snap.child("itemName").getValue();

                        long itemPostTime = (long) snap.child("itemPostTime").getValue();

//                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//                        Date itemPostTime = null;
//                        try {
//                            itemPostTime = formatter.parse(snap.child("itemPostTime").getValue().toString());
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }

                        String sellerID = (String) snap.child("sellerID").getValue();
                        long zipcode = (long) snap.child("zipcode").getValue();
                        String itemCondition = (String) snap.child("itemCondition").getValue();


                        Post post = new Post(itemName, askingPrice, zipcode, sellerID, category, itemCondition,
                                itemPostTime, itemDescription);
                        postList.add(post);
                        //received results
                        Log.i("post", post.getItemName() + " on nod " + postID);
                    }
                }
                // notify the adapter
                postListAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError){
            }
        });

        return view;
    }


//    @Override
//    protected void onDestroy() {
//
//        sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        Gson gson = new Gson();
//        String youJson = gson.toJson(you);
//        editor.putString("user", youJson);
//        editor.commit();
//        super.onDestroy();
//    }
}