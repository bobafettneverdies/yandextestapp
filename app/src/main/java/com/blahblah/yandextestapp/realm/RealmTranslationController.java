package com.blahblah.yandextestapp.realm;

import android.app.Application;

import com.blahblah.yandextestapp.domain.translation.Translation;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

public class RealmTranslationController {
    private final Realm realm;

    public RealmTranslationController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return realm;
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Translation.class);
        realm.commitTransaction();
    }

    public RealmResults<Translation> getAll() {
        return realm.where(Translation.class)
                .findAllSorted("time", Sort.DESCENDING);
    }

    public Translation get(String id) {
        return realm.where(Translation.class).equalTo("id", id).findFirst();
    }

    public boolean translationsExists() {
        return realm.where(Translation.class).count() != 0;
    }

    public RealmResults<Translation> queryFavorites() {
        return realm.where(Translation.class)
                .equalTo("isFavorite", true)
                .findAllSorted("time", Sort.DESCENDING);
    }

    public Translation getLast() {
        long lastTranslationTime = realm.where(Translation.class).max("time").longValue();
        return realm.where(Translation.class)
                .equalTo("time", lastTranslationTime)
                .findFirst();
    }

    public void addOrUpdate(Translation translation) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(translation);
        realm.commitTransaction();
    }
}
