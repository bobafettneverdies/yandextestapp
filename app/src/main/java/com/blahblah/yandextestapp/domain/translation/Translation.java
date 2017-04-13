package com.blahblah.yandextestapp.domain.translation;

import io.realm.RealmModel;

/**
 * Created by 123 on 13.04.2017.
 */

public class Translation implements RealmModel {

    public String source;

    public String result;

    public String lang;

    public long time;

    public boolean isFavorite;

}
