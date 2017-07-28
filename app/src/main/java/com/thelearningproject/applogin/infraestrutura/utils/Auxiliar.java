package com.thelearningproject.applogin.infraestrutura.utils;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gabri on 27/07/2017.
 */

public class Auxiliar {
    public static void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public boolean aplicaPattern(String email){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = pattern.matcher(email);

        return m.matches();

    }
}
