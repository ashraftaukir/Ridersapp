package com.webxzen.ridersapp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.webxzen.ridersapp.R;


public class RegistrationFragment extends Fragment implements View.OnClickListener {

    View view;
    Button signupbtn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.registrationscreen, container, false);
        initialization();
        initializeListener();
        return view;
    }

    private void initializeListener() {
    signupbtn.setOnClickListener(this);

    }

    private void initialization() {
    signupbtn=(Button)view.findViewById(R.id.signup);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signup:
                gotologinPage();
                break;

            default:
                break;
        }

    }

    private void gotologinPage() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginwithemailFragment()).commit();


    }
}
