package com.thelearningproject.applogin.infraestrutura.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void esconderTecladoFragment(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean aplicaPattern(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = pattern.matcher(email);

        return m.matches();

    }

    public static boolean telefonePattern(String telefone) {
        Pattern pattern = Pattern.compile("^((\\+[1-9]{1,3}? ?)?[1-9]{2}? ?)?[9][6-9]\\d{3}-?\\d{4}$");
        Matcher m = pattern.matcher(telefone);

        return m.matches();
    }

    public static void criarToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static String geraCodigo(){
        String codigo = "123";
        return codigo;
    }

    public static AlertDialog criarDialogConfirmacao(Activity activity, String titulo, String mensagem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setPositiveButton(R.string.sim, (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton(R.string.nao, (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    public static AlertDialog criarDialogInsercao(Activity activity, String titulo, String mensagem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setNeutralButton("Adicionar", (DialogInterface.OnClickListener) activity);

        final EditText input = new EditText(activity);
        alert.setView(input);

        abrirTeclado(activity);
        return alert.create();
    }

    public static void abrirTeclado(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
