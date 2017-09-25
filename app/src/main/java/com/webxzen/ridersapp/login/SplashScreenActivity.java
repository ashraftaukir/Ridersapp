package com.webxzen.ridersapp.login;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.home.HomeScreenActivity;
import com.webxzen.ridersapp.util.DBHelper;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        DBHelper.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                starOurApp();
            }
        }, 3000);


    }

    private void starOurApp() {
        if (DBHelper.getSavedLogin() != null) {
            startActivity(new Intent(this, HomeScreenActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }



    }
}
