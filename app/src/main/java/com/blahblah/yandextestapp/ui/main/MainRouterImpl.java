package com.blahblah.yandextestapp.ui.main;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;
import com.blahblah.yandextestapp.ui.history.HistoryFragment;
import com.blahblah.yandextestapp.ui.translation.TranslationFragment;

import javax.inject.Inject;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 15.04.2017.
 */
public class MainRouterImpl implements MainRouter {

    private BaseFragment lastFragment;

    private final FragmentManager fragmentManager;

    private final TranslationFragment translationFragment;
    private final HistoryFragment historyFragment;

    @Inject
    public MainRouterImpl(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.translationFragment = new TranslationFragment();
        this.historyFragment = new HistoryFragment();
    }

    @Override
    public void openTranslationFragment() {
        changeFragment(translationFragment);
    }

    @Override
    public void openHistoryFragment() {
        changeFragment(historyFragment);
    }

    @Override
    public TranslationFragment getTranslationFragment() {
        return translationFragment;
    }

    @Override
    public HistoryFragment getHistoryFragment() {
        return historyFragment;
    }

    private void changeFragment(@NonNull final BaseFragment fragment) {
        if (fragment.equals(lastFragment)) {
            return;
        }

        Log.d("MainRouterImpl", "fragment: " + fragment.getClass().getName());

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (lastFragment != null) {
            if (lastFragment instanceof TranslationFragment) {
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                        R.anim.slide_in_right, R.anim.slide_out_right);
            } else {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
                        R.anim.slide_in_left, R.anim.slide_out_left);
            }
        }

        transaction.replace(R.id.main_content, fragment, fragment.getClass().getName());
        transaction.commit();
        lastFragment = fragment;
    }
}
