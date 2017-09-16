package com.webxzen.ridersapp.view.login;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.webxzen.ridersapp.R;

public class MainActivity extends AppCompatActivity {



    FrameLayout fragmentcontainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentcontainer=(FrameLayout)findViewById(R.id.fragment_container);

        fragmenttransition();

    }

    private void fragmenttransition() {

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).addToBackStack(null).commit();

    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = (LoginFragment)getFragmentManager().findFragmentById(fragmentcontainer.getId());
        fragment.onActivityResult(requestCode, resultCode, data);


    }
}
