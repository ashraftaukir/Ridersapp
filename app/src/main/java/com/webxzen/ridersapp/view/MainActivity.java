package com.webxzen.ridersapp.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.view.fragment.HomeScreenFragment;
import com.webxzen.ridersapp.view.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //our project is starting


//        Fragment homescreen = new HomeScreenFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.add(R.id.fragment_container, homescreen).commit();

        Fragment loginfragment = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, loginfragment).commit();

    }
}
