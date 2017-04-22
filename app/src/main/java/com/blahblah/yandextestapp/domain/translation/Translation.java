package com.blahblah.yandextestapp.domain.translation;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.R;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by 123 on 13.04.2017.
 */

public class Translation extends RealmObject implements Comparable<Translation> {

    @PrimaryKey
    public long id;

    public String source;

    public String result;

    public String srcLanguage;

    public String dstLanguage;

    public long time;

    public boolean isFavorite;

    public String getLang() {
        return String.format("%s - %s", srcLanguage, dstLanguage);
    }

    @DrawableRes
    public int getIconRes() {
        return isFavorite ? R.drawable.ic_bookmark_yellow_24dp : R.drawable.ic_bookmark_black_24dp;
    }

    public boolean containsThatTranslation(Translation that) {
        return srcLanguage.equals(that.srcLanguage) && dstLanguage.equals(that.dstLanguage) &&
                source.contains(that.source);
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
