package com.example.marketplace;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Profile extends Fragment {

    private ImageView imgUser;
    private TextView txtDisplayName;
    private TextView txtEmail;
    private Button btnEdit;
    private Button btnPosts;
    private Button btnChats;
    private TextView txtUni;
    private FirebaseUser mFirebaseUser;
    // test

    public Profile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        imgUser = view.findViewById(R.id.imgUser);
        txtDisplayName = view.findViewById(R.id.txtDisplayName);
        txtEmail = view.findViewById(R.id.txtEmail);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnPosts = view.findViewById(R.id.btnPosts);
        btnChats = view.findViewById(R.id.btnChats);
        btnChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ChatList.class));
            }
        });

        txtUni = view.findViewById(R.id.txtUni);

        mFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        if (mFirebaseUser != null) {

            txtDisplayName.setText(mFirebaseUser.getDisplayName());
            txtEmail.setText(mFirebaseUser.getEmail());

            Uri img = mFirebaseUser.getPhotoUrl();
            GlideApp.with(this /* context */)
                    .load(img)
                    .into(imgUser);
        }

        return view;
    }
}
