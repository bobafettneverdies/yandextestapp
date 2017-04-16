package com.blahblah.yandextestapp.ui.translation;

import android.support.annotation.NonNull;
import android.view.View;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.base.BaseFragment;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class TranslationFragment extends BaseFragment  implements TranslationView {
    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_translation;
    }

    @Override
    protected void onViewInflated(@NonNull View view) {

    }

    @Override
    public void setSrcLanguage(String language) {

    }

    @Override
    public void setDstLanguage(String language) {

    }

    @Override
    public void swapLanguages() {

    }

    @Override
    public void setTranslation(String translation) {

    }
}
