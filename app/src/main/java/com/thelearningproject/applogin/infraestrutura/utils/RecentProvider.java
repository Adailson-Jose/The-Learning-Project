package com.thelearningproject.applogin.infraestrutura.utils;

import android.content.SearchRecentSuggestionsProvider;

public class RecentProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.thelearningproject.applogin.infraestrutura.utils.RecentProvider";
    public static final int MODE = DATABASE_MODE_QUERIES;

    public RecentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
