package com.blahblah.yandextestapp;

import io.realm.Realm;

/**
 * Created by 123 on 13.04.2017.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
    }

}
