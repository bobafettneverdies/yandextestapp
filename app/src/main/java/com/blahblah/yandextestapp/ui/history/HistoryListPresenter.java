package com.blahblah.yandextestapp.ui.history;

import com.blahblah.yandextestapp.domain.translation.Translation;
import com.blahblah.yandextestapp.realm.RealmTranslationRepository;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * Created by Dmitrii Komiakov on 23.04.2017.
 */

public class HistoryListPresenter {

    public interface HistoryListView {}

    private final RealmTranslationRepository translationRepository;

    @Inject
    public HistoryListPresenter(RealmTranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    public RealmResults<Translation> getData(boolean showFavoritesOnly) {
        return showFavoritesOnly ? translationRepository.getFavorites() : translationRepository.getAll();
    }

    public void setFavorite(Translation translation) {
        translation.isFavorite = !translation.isFavorite;
        translationRepository.update(translation);
    }
}
