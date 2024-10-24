package com.Singlee.forex

import android.app.Application
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Base : Application()
{
//    override fun onCreate() {
//        super.onCreate()
////        FacebookSdk.sdkInitialize(applicationContext);
////        AppEventsLogger.activateApp(this);
//    }
}