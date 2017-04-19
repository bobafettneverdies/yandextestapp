package com.blahblah.yandextestapp.ui.history;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

public class HistoryPagerAdapter extends FragmentPagerAdapter {

    private final String historyTitle;
    private final String favoritesTitle;

    public HistoryPagerAdapter(FragmentManager fm, String historyTitle, String favoritesTitle) {
        super(fm);
        this.historyTitle = historyTitle;
        this.favoritesTitle = favoritesTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return HistoryListFragment.newInstance(position == 1);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return historyTitle;
            case 1:
                return favoritesTitle;
            default:
                return "";
        }
    }
}
