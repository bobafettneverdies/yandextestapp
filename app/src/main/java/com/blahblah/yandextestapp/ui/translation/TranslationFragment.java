package com.blahblah.yandextestapp.ui.translation;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;

import java.util.Locale;

import javax.inject.Inject;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class TranslationFragment extends BaseFragment implements TranslationView, View.OnClickListener {

    private AppCompatTextView srcLanguageView;
    private AppCompatTextView dstLanguageView;
    private AppCompatEditText translationInput;
    private AppCompatTextView translationResultView;

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
        translationResultView = (AppCompatTextView) view.findViewById(R.id.translation_result_view);

        view.findViewById(R.id.translation_swap_languages_btn).setOnClickListener(this);

        String uiLanguage = Locale.getDefault().getLanguage();
        if (!TextUtils.equals(uiLanguage, Locale.ENGLISH.getLanguage())) {
            presenter.getLanguageHub(uiLanguage, Locale.ENGLISH.getLanguage());
        } else {
            presenter.getLanguageHub(uiLanguage, Locale.FRENCH.getLanguage());
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
}
