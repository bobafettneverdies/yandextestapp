package com.blahblah.yandextestapp.ui.history;

import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

public class HistoryListFragment extends BaseFragment {

    private AppCompatEditText searchInput;
    private RecyclerView translationList;

    private boolean showFavoritesOnly;

    public static HistoryListFragment newInstance(boolean showFavoritesOnly) {
        HistoryListFragment fragment = new HistoryListFragment();
        fragment.showFavoritesOnly = showFavoritesOnly;
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_history_list;
    }

    @Override
    protected void onViewInflated(@NonNull View view) {
        searchInput = (AppCompatEditText) view.findViewById(R.id.history_search_input);
        searchInput.setHint(showFavoritesOnly ? R.string.find_in_the_favorites : R.string.find_in_the_history);
        VectorDrawableCompat drawableCompat= VectorDrawableCompat.create(getActivity().getResources(), R.drawable.ic_search_black_24dp, getContext().getTheme());
        drawableCompat.setBounds( 0, 0, drawableCompat.getIntrinsicWidth(), drawableCompat.getIntrinsicHeight());
        searchInput.setCompoundDrawables(drawableCompat, null, null, null);

        translationList = (RecyclerView) view.findViewById(R.id.history_translation_list);
    }
}
