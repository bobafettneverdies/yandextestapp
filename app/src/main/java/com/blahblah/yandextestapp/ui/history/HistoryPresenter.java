package com.blahblah.yandextestapp.ui.history;

import com.blahblah.yandextestapp.domain.translation.Translation;
import com.blahblah.yandextestapp.realm.RealmTranslationRepository;
import com.blahblah.yandextestapp.ui.translation.TranslationPresenter;

import javax.inject.Inject;

import io.realm.RealmResults;

/**
 * Created by Dmitrii Komiakov on 23.04.2017.
 */

public class HistoryPresenter {

    public interface HistoryView {
        void openTranslationView();
    }

    private final HistoryView view;
    private final RealmTranslationRepository translationRepository;
    private final TranslationPresenter translationPresenter;

    @Inject
    public HistoryPresenter(HistoryView view, RealmTranslationRepository translationRepository, TranslationPresenter translationPresenter) {
        this.view = view;
        this.translationRepository = translationRepository;
        this.translationPresenter = translationPresenter;
    }

    public RealmResults<Translation> getData(boolean showFavoritesOnly) {
        return showFavoritesOnly ? translationRepository.getFavorites() : translationRepository.getAll();
    }

    public RealmResults<Translation> getSearchResultsFor(String text, boolean showFavoritesOnly) {
        return text == null || text.isEmpty() ?
                getData(showFavoritesOnly) :
                (showFavoritesOnly ? translationRepository.searchInFavorites(text) : translationRepository.search(text));
    }

    public void setFavorite(Translation translation) {
        translationRepository.update(translation, data -> data.isFavorite = !data.isFavorite);
    }

    public void showTranslation(Translation translation) {
        translationRepository.update(translation, data -> data.time = System.currentTimeMillis());
        translationPresenter.setTranslation(translation);
        view.openTranslationView();
    }
}
