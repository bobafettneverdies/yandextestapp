package com.blahblah.yandextestapp.realm;

import com.blahblah.yandextestapp.domain.translation.Translation;

import io.realm.RealmResults;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

public interface RealmTranslationRepository {

    interface TranslationUpdater {
        void updateData(Translation data);
    }

    RealmResults<Translation> getAll();

    Translation get(long id);

    RealmResults<Translation> getFavorites();

    RealmResults<Translation> search(String text);

    RealmResults<Translation> searchInFavorites(String text);

    Translation getLatest();

    void add(Translation translation);

    void update(Translation translation);

    void update(Translation data, TranslationUpdater translationUpdater);

    boolean translationsExists();

    void clearAll();

    void clearFavorites();
}
