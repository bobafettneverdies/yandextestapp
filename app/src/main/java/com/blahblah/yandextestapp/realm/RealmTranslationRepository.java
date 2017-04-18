package com.blahblah.yandextestapp.realm;

import com.blahblah.yandextestapp.domain.translation.Translation;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

public interface RealmTranslationRepository {
    RealmResults<Translation> getAll();

    Translation get(String id);

    RealmResults<Translation> getFavorites();

    RealmResults<Translation> search(String text);

    Translation getLatest();

    void add(Translation translation);

    void update(Translation translation);

    boolean translationsExists();

    void clearAll();

    void clearFavorites();
}
