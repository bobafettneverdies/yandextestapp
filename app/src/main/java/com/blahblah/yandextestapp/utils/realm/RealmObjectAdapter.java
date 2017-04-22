package com.blahblah.yandextestapp.utils.realm;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Dmitrii Komiakov on 22.04.2017.
 */

public abstract class RealmObjectAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

    public RealmObjectAdapter(RealmResults<T> realmResults) {
        super(realmResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
