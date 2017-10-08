package com.webxzen.ridersapp.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.model.LoginModel;
import com.webxzen.ridersapp.util.Appinfo;
import com.webxzen.ridersapp.util.DBHelper;
import com.webxzen.ridersapp.util.DialogUtil;
import com.webxzen.ridersapp.util.PreferenceHelper;


/**
 * Created by behestee on 9/22/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected DialogUtil dialogUtil;
    protected PreferenceHelper preferenceHelper;
    protected ActionBar actionBar;
    private LoginModel loginModel;
    AppCompatActivity context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        context=this;
        preferenceHelper = new PreferenceHelper(this);
        dialogUtil = new DialogUtil(this);
        actionBar = getSupportActionBar();
        loginModel = DBHelper.getSavedLogin();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    protected void setTitle(String title) {
        actionBar.setTitle(title);
    }

    protected void showBackButton(boolean show) {
        actionBar.setDisplayHomeAsUpEnabled(show);
        actionBar.setDisplayShowHomeEnabled(show);
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    protected boolean isEmpty(EditText editText) {

        String value = editText.getText().toString();

        if (value.trim().length() == 0) {
            return true;
        } else {
            return false;
        }

    }

    public void showLoadingDialog(final boolean isLoad, final Context dialContext) {
        final ProgressDialog prog = new ProgressDialog(dialContext);//Assuming that you are using fragments.
        prog.setTitle(getString(R.string.please_wait));
        prog.setMessage(getString(R.string.loading));
        prog.setCancelable(false);
        prog.setIndeterminate(true);
        prog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (isLoad) {
            prog.show();
        } else {
            prog.hide();
        }
    }

    protected void initFragment(Fragment fragment, String id, int resId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(resId, fragment, id)
                .commit();
    }

    protected void initFragmentWithValue(Fragment fragment, String id, int resId, String fragmentName) {

        Bundle bundle = new Bundle();
        bundle.putString(Appinfo.FRAGMENT_NAME, fragmentName);
        fragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(resId, fragment, id)
                .commit();
    }

    protected void replaceFragment(Fragment fragment, String id, String oldId, int resId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(resId, fragment, id)
                .addToBackStack(oldId)
                .commit();
    }

    public LoginModel getLoginModel() {
        return loginModel == null ? DBHelper.getSavedLogin() : loginModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
