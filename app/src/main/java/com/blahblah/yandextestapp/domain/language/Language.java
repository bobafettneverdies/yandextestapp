package com.blahblah.yandextestapp.domain.language;

/**
 * Created by 123 on 10.04.2017.
 */

public class Language {

    private final String name;
    private final String description;

    public Language(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return description;
    }
}
