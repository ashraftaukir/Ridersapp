package com.webxzen.ridersapp.view.activitypkg;

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
        fragmenttransition();

    }

    private void fragmenttransition() {
        Fragment loginfragment = new LoginFragment();
        FragmentTransaction fragmenttransition = getFragmentManager().beginTransaction();
        fragmenttransition.add(R.id.fragment_container, loginfragment).commit();

    }
}
