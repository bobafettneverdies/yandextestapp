package com.blahblah.yandextestapp.ui.translation;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.domain.language.Language;
import com.blahblah.yandextestapp.ui.base.BaseFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class TranslationFragment extends BaseFragment implements TranslationView, View.OnClickListener, TextWatcher {

    private static final long TRANSLATE_DELAY_IN_MS = 700;

    private AppCompatTextView srcLanguageView;
    private AppCompatTextView dstLanguageView;
    private AppCompatEditText translationInput;
    private AppCompatTextView translationResultView;
    private Timer translateTimer;
    private MaterialDialog progressDialog;

    @Inject
    TranslationPresenter presenter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_translation;
    }

    @Override
    protected void onViewInflated(@NonNull View view) {
        ((MainActivity) getActivity()).getComponent().inject(this);

        srcLanguageView = (AppCompatTextView) view.findViewById(R.id.translation_src_language);

        dstLanguageView = (AppCompatTextView) view.findViewById(R.id.translation_dst_language);

        translationInput = (AppCompatEditText) view.findViewById(R.id.translation_src_input);
        translationInput.addTextChangedListener(this);

        translationResultView = (AppCompatTextView) view.findViewById(R.id.translation_result_view);

        view.findViewById(R.id.translation_swap_languages_btn).setOnClickListener(this);
        view.findViewById(R.id.translation_src_language).setOnClickListener(this);
        view.findViewById(R.id.translation_dst_language).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        String uiLanguage = Locale.getDefault().getLanguage();
        if (!TextUtils.equals(uiLanguage, Locale.ENGLISH.getLanguage())) {
            presenter.syncViewWithPresenterState(uiLanguage, Locale.ENGLISH.getLanguage());
        } else {
            presenter.syncViewWithPresenterState(uiLanguage, Locale.FRENCH.getLanguage());
        }
    }

    @Override
    public void setSrcLanguage(String language) {
        srcLanguageView.setText(language);
    }

    @Override
    public void setDstLanguage(String language) {
        dstLanguageView.setText(language);
    }

    @Override
    public void setSourceText(String text) {
        translationInput.setText(text);
    }

    @Override
    public void setTranslation(String translation) {
        translationResultView.setText(translation);
    }

    @Override
    public void showLanguageHubQueryProgressBar() {
        MaterialDialog.Builder b = new MaterialDialog.Builder(getContext());
        b.progress(true, 0);
        b.content(R.string.updating_data);
        b.cancelable(false);
        progressDialog = b.show();
    }

    @Override
    public void hideLanguageHubQueryProgressBar() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
        progressDialog = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translation_swap_languages_btn:
                presenter.swapLanguages();
                break;
            case R.id.translation_src_language:
                selectSrcLanguage();
                break;
            case R.id.translation_dst_language:
                selectDstLanguage();
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
        if (translateTimer == null) {
            translateTimer = new Timer();
        }
        translateTimer.cancel();
        translateTimer = new Timer();
        translateTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        presenter.translate(s.toString());
                    }
                },
                TRANSLATE_DELAY_IN_MS
        );
    }

    public void selectSrcLanguage() {
        List<Language> languages = presenter.getLanguageList();

        if (languages != null) {
            MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter((dialog, index, item) -> {
                dialog.hide();
                presenter.setTranslationSrcLanguage(languages.get(index).getName());
            });

            for (Language language : languages) {
                adapter.add(new MaterialSimpleListItem.Builder(getActivity())
                        .content(language.toString())
                        .backgroundColor(Color.WHITE)
                        .build());
            }

            new MaterialDialog.Builder(getActivity())
                    .adapter(adapter, null)
                    .show();
        }
    }

    public void selectDstLanguage() {
        List<Language> languages = presenter.getLanguageList();

        if (languages != null) {
            MaterialSimpleListAdapter adapter = new MaterialSimpleListAdapter((dialog, index, item) -> {
                dialog.hide();
                presenter.setTranslationDstLanguage(languages.get(index).getName());
            });

            for (Language language : languages) {
                adapter.add(new MaterialSimpleListItem.Builder(getActivity())
                        .content(language.toString())
                        .backgroundColor(Color.WHITE)
                        .build());
            }

            new MaterialDialog.Builder(getActivity())
                    .adapter(adapter, null)
                    .show();
        }
    }
}
