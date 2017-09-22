package com.webxzen.ridersapp.view.shared;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    protected void addFragment(Fragment fragment, String id, int resId){
        getFragmentManager()
                .beginTransaction()
                .add(resId, fragment, id)
                .commit();
    }

//    protected void replaceFragment(Fragment fragment, String id, String oldId, int resId){
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(resId, fragment, id)
//                .addToBackStack(oldId)
//                .commit();
//    }
}
