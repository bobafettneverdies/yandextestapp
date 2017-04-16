package com.blahblah.yandextestapp.domain.translation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dmitrii Komiakov on 16.04.2017.
 */

public class TranslationDto {

    @Expose
    @SerializedName("lang")
    public String lang;

    @Expose
    @SerializedName("text")
    public List<String> text;

    @Override
    public String toString() {
        String result = "";
        if (text != null && text.size() > 0) {
            result = text.get(0);
        }
        return result;
    }
}
