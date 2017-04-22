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

public class RealmTranslationRepositoryImpl implements RealmTranslationRepository {

    private final Realm realm;

    @Inject
    public RealmTranslationRepositoryImpl() {
        realm = Realm.getDefaultInstance();
    }

    @Override
    public RealmResults<Translation> getAll() {
        return baseQuery()
                .findAllSorted("time", Sort.DESCENDING);
    }

    @Override
    public Translation get(String id) {
        return baseQuery().equalTo("id", id).findFirst();
    }

    @Override
    public RealmResults<Translation> getFavorites() {
        return baseQuery()
                .equalTo("isFavorite", true)
                .findAllSorted("time", Sort.DESCENDING);
    }

    @Override
    public RealmResults<Translation> search(String text) {
        return baseQuery()
                .contains("source", text)
                .or()
                .contains("result", text)
                .findAllSorted("time", Sort.DESCENDING);
    }

    @Override
    public RealmResults<Translation> searchInFavorites(String text) {
        return baseQuery()
                .equalTo("isFavorite", true)
                .contains("source", text)
                .or()
                .contains("result", text)
                .findAllSorted("time", Sort.DESCENDING);
    }

    @Override
    public Translation getLatest() {
        long lastTranslationTime = realm.where(Translation.class).max("time").longValue();
        return baseQuery()
                .equalTo("time", lastTranslationTime)
                .findFirst();
    }

    @Override
    public void add(Translation translation) {
        long currentTimeMillis = System.currentTimeMillis();
        translation.time = currentTimeMillis;
        translation.id = currentTimeMillis;
        addOrUpdate(translation);
    }

    @Override
    public void update(Translation translation) {
        translation.time = System.currentTimeMillis();;
        addOrUpdate(translation);
    }

    @Override
    public boolean translationsExists() {
        return baseQuery().count() != 0;
    }

    @Override
    public void clearAll() {
        realm.beginTransaction();
        realm.delete(Translation.class);
        realm.commitTransaction();
    }

    @Override
    public void clearFavorites() {
        realm.beginTransaction();
        getFavorites().deleteAllFromRealm();
        realm.commitTransaction();
    }

    private void addOrUpdate(Translation translation) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(translation);
        realm.commitTransaction();
    }

    private RealmQuery<Translation> baseQuery(){
        return realm.where(Translation.class);
    }
}
