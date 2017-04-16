package com.blahblah.yandextestapp.ui.translation;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 16.04.2017.
 */
public interface TranslationView {
    void setSrcLanguage(String language);

    void setDstLanguage(String language);

    void setTranslation(String translation);
}
