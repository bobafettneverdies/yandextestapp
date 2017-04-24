package com.blahblah.yandextestapp.ui.translation;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;

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

    private AppCompatTextView srcLanguageView;
    private AppCompatTextView dstLanguageView;
    private AppCompatEditText translationInput;
    private AppCompatTextView translationResultView;
    private Timer translateTimer;
    private static final long TRANSLATE_DELAY_IN_MS = 1000;

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.translation_swap_languages_btn:
                presenter.swapLanguages();
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
}
