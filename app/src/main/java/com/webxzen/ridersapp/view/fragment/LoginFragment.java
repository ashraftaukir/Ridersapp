package com.webxzen.ridersapp.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.webxzen.ridersapp.R;


public class LoginFragment extends Fragment implements View.OnClickListener {


    Button loginwithfbbtn, loginwitheamilbtn;
    TextView registrationtv;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.loginscreen, container, false);
        initialization();
        listeners();
        return view;
    }

    private void listeners() {
        loginwitheamilbtn.setOnClickListener(this);
        loginwithfbbtn.setOnClickListener(this);
        registrationtv.setOnClickListener(this);
    }

    private void initialization() {

        loginwithfbbtn = (Button) view.findViewById(R.id.loginwithfb);
        loginwitheamilbtn = (Button) view.findViewById(R.id.loginwithemail);
        registrationtv = (TextView) view.findViewById(R.id.register);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.loginwithemail:

                gotologinwithEmail();

                break;
            case R.id.loginwithfb:
                Toast.makeText(getContext(), "comming soon", Toast.LENGTH_SHORT).show();
                break;


            case R.id.register:

                Toast.makeText(getContext(), "comming soon", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void gotologinwithEmail() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginwithemailFragment()).commit();

    }
}