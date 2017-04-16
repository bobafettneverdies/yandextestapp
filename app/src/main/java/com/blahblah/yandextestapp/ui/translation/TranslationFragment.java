package com.blahblah.yandextestapp.ui.translation;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class TranslationFragment extends BaseFragment implements TranslationView {

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
        srcLanguageView = (AppCompatTextView) view.findViewById(R.id.translation_src_language);
        dstLanguageView = (AppCompatTextView) view.findViewById(R.id.translation_dst_language);
        translationInput = (AppCompatEditText) view.findViewById(R.id.translation_src_input);
        translationResultView = (AppCompatTextView) view.findViewById(R.id.translation_result_view);
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
    public void swapLanguages() {
        String dstLanguage = dstLanguageView.getText().toString();
        dstLanguageView.setText(srcLanguageView.getText());
        srcLanguageView.setText(dstLanguage);
    }

    @Override
    public void setTranslation(String translation) {
        translationResultView.setText(translation);
    }
}
