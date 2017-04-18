package com.blahblah.yandextestapp.realm;

import com.blahblah.yandextestapp.domain.translation.Translation;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

@SuppressWarnings("unused")
public class RealmTranslationController {

    private final Realm realm;

    @Inject
    public RealmTranslationController() {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealm() {
        return realm;
    }

    public RealmResults<Translation> getAll() {
        return baseQuery()
                .findAllSorted("time", Sort.DESCENDING);
    }

    public Translation get(String id) {
        return baseQuery().equalTo("id", id).findFirst();
    }

    public RealmResults<Translation> getFavorites() {
        return baseQuery()
                .equalTo("isFavorite", true)
                .findAllSorted("time", Sort.DESCENDING);
    }

    public RealmResults<Translation> search(String text) {
        return baseQuery()
                .contains("source", text)
                .or()
                .contains("result", text)
                .findAllSorted("time", Sort.DESCENDING);
    }

    public Translation getLast() {
        long lastTranslationTime = realm.where(Translation.class).max("time").longValue();
        return baseQuery()
                .equalTo("time", lastTranslationTime)
                .findFirst();
    }

    public void addOrUpdate(Translation translation) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(translation);
        realm.commitTransaction();
    }

    public boolean translationsExists() {
        return baseQuery().count() != 0;
    }

    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Translation.class);
        realm.commitTransaction();
    }

    public void clearFavorites() {
        realm.beginTransaction();
        getFavorites().deleteAllFromRealm();
        realm.commitTransaction();
    }

    private RealmQuery<Translation> baseQuery(){
        return realm.where(Translation.class);
    }
}
