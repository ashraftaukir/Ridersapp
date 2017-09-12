package com.webxzen.ridersapp.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.webxzen.ridersapp.R;
import com.webxzen.ridersapp.view.fragment.HomeScreen;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //our project is starting


      //  getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new HomeScreen()).commit();
        Fragment homescreen = new HomeScreen();
       FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, homescreen).commit();

    }
}
