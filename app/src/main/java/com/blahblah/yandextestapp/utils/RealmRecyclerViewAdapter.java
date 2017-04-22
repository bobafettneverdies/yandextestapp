package com.blahblah.yandextestapp.utils;

import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Dmitrii Komiakov on 22.04.2017.
 */

public abstract class RealmRecyclerViewAdapter<T extends RealmObject> extends Adapter {

    private RealmBaseAdapter<T> realmBaseAdapter;

    public T getItem(int position) {

        return realmBaseAdapter.getItem(position);
    }

    public RealmBaseAdapter<T> getRealmAdapter() {

        return realmBaseAdapter;
    }

    public void setRealmAdapter(RealmBaseAdapter<T> realmAdapter) {

        realmBaseAdapter = realmAdapter;
    }
}
