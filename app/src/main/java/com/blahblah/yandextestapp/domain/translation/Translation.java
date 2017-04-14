package com.blahblah.yandextestapp.domain.translation;

import android.support.annotation.NonNull;

import io.realm.RealmModel;

/**
 * Created by 123 on 13.04.2017.
 */

public class Translation implements RealmModel, Comparable<Translation> {

    public String source;

    public String result;

    public String lang;

    public long time;

    public boolean isFavorite;


    @Override
    public int compareTo(@NonNull Translation o) {
        if (time > o.time) {
            return 1;
        } else if (time < o.time) {
            return -1;
        } else {
            return 0;
        }
    }
}
