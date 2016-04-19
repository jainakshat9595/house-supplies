package jainakshat.in.housesupplies;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Akshat Jain on 11/23/2015.
 */
public class DB extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

}
