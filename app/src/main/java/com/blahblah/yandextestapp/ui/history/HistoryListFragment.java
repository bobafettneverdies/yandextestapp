package com.blahblah.yandextestapp.ui.history;

import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.domain.translation.Translation;
import com.blahblah.yandextestapp.ui.base.BaseFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;
import com.blahblah.yandextestapp.utils.ViewHolder;

import javax.inject.Inject;

/**
 * Created by Dmitrii Komiakov on 19.04.2017.
 */

public class HistoryListFragment extends BaseFragment implements ViewHolder.OnHolderClickListener, TextWatcher {

    @Inject
    HistoryPresenter presenter;

    private AppCompatEditText searchInput;
    private RecyclerView translationList;
    private TranslationAdapter adapter;

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
        ((MainActivity) getActivity()).getComponent().inject(this);

        searchInput = (AppCompatEditText) view.findViewById(R.id.history_search_input);
        searchInput.setHint(showFavoritesOnly ? R.string.find_in_the_favorites : R.string.find_in_the_history);
        VectorDrawableCompat drawableCompat= VectorDrawableCompat.create(getActivity().getResources(), R.drawable.ic_search_black_24dp, getContext().getTheme());
        drawableCompat.setBounds( 0, 0, drawableCompat.getIntrinsicWidth(), drawableCompat.getIntrinsicHeight());
        searchInput.setCompoundDrawables(drawableCompat, null, null, null);
        searchInput.addTextChangedListener(this);

        translationList = (RecyclerView) view.findViewById(R.id.history_translation_list);
        translationList.setHasFixedSize(true);
        adapter = new TranslationAdapter(presenter.getData(showFavoritesOnly), true);
        adapter.setOnHolderClickListener(this);
        translationList.setAdapter(adapter);
    }

    @Override
    public void onHolderClick(View view, ViewHolder holder) {
        switch (view.getId()) {
            case R.id.list_item_translation_favorite_btn:
                presenter.setFavorite((Translation) holder.data);
                break;
            case R.id.list_item_translation_background:
                presenter.showTranslation((Translation) holder.data);
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        adapter.updateData(presenter.getSearchResultsFor(s.toString(), showFavoritesOnly));
    }
}
