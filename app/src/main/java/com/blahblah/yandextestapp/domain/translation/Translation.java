package com.blahblah.yandextestapp.domain.translation;

import android.support.annotation.NonNull;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 123 on 13.04.2017.
 */

public class Translation implements RealmModel, Comparable<Translation> {

    @PrimaryKey
    public int id;

    public String source;

    public String result;

    public String srcLanguage;

    public String dstLanguage;

    public long time;

    public boolean isFavorite;

    public String getLang() {
        return String.format("%s - %s", srcLanguage, dstLanguage);
    }

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
