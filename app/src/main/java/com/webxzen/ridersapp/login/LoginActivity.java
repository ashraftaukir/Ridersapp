package com.webxzen.ridersapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.base.BaseActivity;
import com.webxzen.ridersapp.util.Appinfo;

public class LoginActivity extends BaseActivity {



    FrameLayout fragmentcontainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentcontainer=(FrameLayout)findViewById(R.id.fragment_container);
        initFragment(new LoginFragment(), Appinfo.LOGIN_FRAGMENT,fragmentcontainer.getId());


    }




//    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            super.onBackPressed();
//            //  finish();
//        } else {
//            getFragmentManager().popBackStack();
//        }
//    }
//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(fragmentcontainer.getId());
        fragment.onActivityResult(requestCode, resultCode, data);


    }
}
