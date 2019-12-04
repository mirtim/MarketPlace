package com.example.marketplace;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "mFirebaseIIDService";
    private static final String SUBSCRIBE_TO = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public void onNewToken(String newToken){
        super.onNewToken(newToken);
        FirebaseMessaging.getInstance().subscribeToTopic(SUBSCRIBE_TO);

    }
}