package com.blahblah.yandextestapp.ui.history;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class HistoryFragment extends BaseFragment implements HistoryPresenter.HistoryView {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history;
    }

    @Override
    protected void onViewInflated(@NonNull View view) {
        viewPager = (ViewPager) view.findViewById(R.id.history_pager);
        HistoryPagerAdapter pagerAdapter = new HistoryPagerAdapter(getChildFragmentManager(),
                getString(R.string.title_history), getString(R.string.title_favorites));
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout) view.findViewById(R.id.history_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void openTranslationView() {
        ((MainActivity) getActivity()).selectTranslationNavItem();
    }
}
