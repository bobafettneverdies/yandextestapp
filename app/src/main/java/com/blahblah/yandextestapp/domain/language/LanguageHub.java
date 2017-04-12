package com.blahblah.yandextestapp.domain.language;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 123 on 10.04.2017.
 */

public class LanguageHub {

    @Expose
    @SerializedName("langs")
    public Map<String, String> languages;

    public List<Language> getLanguagesAsList() {
        List<Language> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : languages.entrySet()) {
            result.add(new Language(entry.getKey(), entry.getValue()));
        }
        return result;
    }

}
