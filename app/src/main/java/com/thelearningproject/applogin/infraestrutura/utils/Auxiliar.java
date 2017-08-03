package com.thelearningproject.applogin.infraestrutura.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.thelearningproject.applogin.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Criado por Gabriel on 27/07/2017.
 */

public class Auxiliar {
    public static void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public boolean aplicaPattern(String email){
        Pattern pattern = Pattern.compile("^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = pattern.matcher(email);

        return m.matches();

    }
    public static void criarToast(Activity activity, String msg) {
        Toast.makeText(activity,msg, Toast.LENGTH_LONG).show();
    }
    public static AlertDialog criarDialogConfirmacao(Activity activity, String titulo) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setMessage(titulo);
        alert.setPositiveButton(R.string.sim, (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton(R.string.nao, (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

}
