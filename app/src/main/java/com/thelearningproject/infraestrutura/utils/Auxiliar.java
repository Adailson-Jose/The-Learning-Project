package com.thelearningproject.infraestrutura.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.thelearningproject.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Auxiliar.
 */
public class Auxiliar {
    private static final String CODIGO_TESTE = "123";

    /**
     * Esconder teclado.
     *
     * @param activity the activity
     */
    public static void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Aplica pattern boolean.
     *
     * @param email the email
     * @return the boolean
     */
/*    public static void esconderTecladoFragment(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }*/
    public boolean aplicaPattern(String email) {
        Pattern pattern = Pattern.compile("^[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$");
        Matcher m = pattern.matcher(email);

        return m.matches();

    }

    /**
     * Telefone pattern boolean.
     *
     * @param telefone the telefone
     * @return the boolean
     */
    public static boolean telefonePattern(String telefone) {
        Pattern pattern = Pattern.compile("^(\\+[1-9]{1,3}(-?| ))?((([1-9]{2}(-?| ))?[9][1-9]\\d{3}(-?| )\\d{4})|([1-9]{3}(-?| )\\d{3}(-?| )\\d{4}))$");
        Matcher m = pattern.matcher(telefone);

        return m.matches();
    }

    /**
     * Criar toast.
     *
     * @param context the context
     * @param msg     the msg
     */
    public static void criarToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gera codigo string.
     *
     * @return the string
     */
    public static String geraCodigo() {
        return CODIGO_TESTE;
    }

    /**
     * Criar dialog confirmacao alert dialog.
     *
     * @param activity the activity
     * @param titulo   the titulo
     * @param mensagem the mensagem
     * @return the alert dialog
     */
    public static AlertDialog criarDialogConfirmacao(Activity activity, String titulo, String mensagem) {
        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setPositiveButton(R.string.sim, (DialogInterface.OnClickListener) activity);
        alert.setNegativeButton(R.string.nao, (DialogInterface.OnClickListener) activity);

        return alert.create();
    }

    /**
     * Abrir teclado.
     *
     * @param activity the activity
     */
    public static void abrirTeclado(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
