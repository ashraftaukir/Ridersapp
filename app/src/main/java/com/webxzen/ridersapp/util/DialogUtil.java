package com.webxzen.ridersapp.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.webxzen.ridersapp.R;


/**
 * Created by behestee on 9/22/17.
 */

public class DialogUtil {

    private Context context;
    private AlertDialog.Builder dialog;
    private ProgressDialog progressDialog;
    private EditText editText;

    /**
     * Constructor for dialog util
     * @param context
     */
    public DialogUtil(Context context) {
        this.context = context;
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * To get recent edit text (for dialog with edittext only)
     * @return
     */
    public EditText getEditText() {
        return editText;
    }

    /**
     * Show loading dialog with custom message
     * @param msg
     */
    public void showProgressDialog(String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Dismiss current shows dialog
     */
    public void dismissProgress(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    /**
     * Dialog to show message with ok and custom button listener
     * @param msg
     * @param okListener
     */
    public void showDialogOk(String msg, DialogInterface.OnClickListener okListener) {
        dialog = new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.ok, okListener);
        dialog.show();
    }

    /**
     * Dialog to show message with ok and custom button listener
     * @param msg
     * @param okListener
     */
    public void showDialogOk(String title, String msg, DialogInterface.OnClickListener okListener) {
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.ok, okListener);
        dialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Dialog to show ok button with custom message
     * @param msg
     */
    public void showDialogOk(String msg) {
        if(context == null)
            return;

        dialog = new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Dialog to show ok button with custom message
     * @param msg
     */
    public void showErrDialogOk(String msg) {
        if(context == null)
            return;

        dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Error!");
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Dialog to show yes no button with custom message with yes callback
     * @param msg
     * @param yesListener
     */
    public void showDialogYesNo(String msg, DialogInterface.OnClickListener yesListener) {
        dialog = new AlertDialog.Builder(context);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, yesListener);
        dialog.setNegativeButton(R.string.no,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Dialog with custom message and options with its callback
     * @param msg
     * @param option
     * @param optionListener
     */
    public void showDialogOption(String msg, CharSequence[] option,
                                 DialogInterface.OnClickListener optionListener){
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle(msg);
        dialog.setItems(option, optionListener);
        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Dialog with edittext and custom message with customisation and callback
     * @param context
     * @param title
     * @param editTextHint
     * @param value
     * @param allCaps
     * @param yesButton
     * @param yesClickListener
     * @param noButton
     * @param noClickListener
     */
    public void showDialogWithEditText(Context context, String title, String editTextHint,
                                       String value, boolean allCaps, String yesButton,
                                       DialogInterface.OnClickListener yesClickListener,
                                       String noButton,
                                       @Nullable DialogInterface.OnClickListener noClickListener){
        dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        editText = new EditText(context);

        if(allCaps){
            editText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        }

        if(editTextHint != null){
            editText.setHint(editTextHint);
        }
        if(value != null){
            editText.setText(value);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        editText.setLayoutParams(lp);
        dialog.setView(editText);
        dialog.setPositiveButton(yesButton, yesClickListener);
        if(noClickListener == null){
            dialog.setNegativeButton(noButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }else {
            dialog.setNegativeButton(noButton, noClickListener);
        }
        dialog.show();
    }

}
