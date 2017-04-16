package com.blahblah.yandextestapp.ui.main;

import com.blahblah.yandextestapp.ui.history.HistoryFragment;
import com.blahblah.yandextestapp.ui.translation.TranslationFragment;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 15.04.2017.
 */
public interface MainRouter {
    void openTranslationFragment();

    void openHistoryFragment();

    TranslationFragment getTranslationFragment();

    HistoryFragment getHistoryFragment();
}
